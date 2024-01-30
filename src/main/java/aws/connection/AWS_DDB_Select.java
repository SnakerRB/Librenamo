package aws.connection;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;
import software.amazon.awssdk.services.dynamodb.model.ScanRequest;
import software.amazon.awssdk.services.dynamodb.model.ScanResponse;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;

public class AWS_DDB_Select {
	private static final Logger LOGGER = LoggerFactory.getLogger(AWS_DDB_Select.class);
	/*
	 * public static void SelectLibro(DynamoDbClient ddb, String tableName, String
	 * isbn, String titulo) { HashMap<String, AttributeValue> keyToGet = new
	 * HashMap<>(); keyToGet.put("ISBN", AttributeValue.builder().s(isbn).build());
	 * 
	 * GetItemRequest request =
	 * GetItemRequest.builder().key(keyToGet).tableName(tableName).build();
	 * 
	 * try { Map<String, AttributeValue> returnedItem = ddb.getItem(request).item();
	 * if (returnedItem.isEmpty()) {
	 * System.out.format("No item found with partition key %s and sort key %s!\n",
	 * isbn, titulo); } else { Set<String> keys = returnedItem.keySet();
	 * System.out.println("Amazon DynamoDB table attributes: \n"); for (String key :
	 * keys) { System.out.format("%s: %s\n", key, returnedItem.get(key).toString());
	 * } }
	 * 
	 * } catch (DynamoDbException e) { System.err.println("Error getting item: " +
	 * e.getMessage()); System.exit(1); } }
	 */

	public static void SelectAll(DynamoDbClient dynamoDbClient, String tableName) {
		// Realiza una consulta simple para listar todos los elementos
		ScanRequest scanRequest = ScanRequest.builder().tableName(tableName).build();

		try {
			ScanResponse response;
			do {
				response = dynamoDbClient.scan(scanRequest);
				for (Map<String, AttributeValue> item : response.items()) {
					imprimirCampos(item);
				}

				scanRequest = scanRequest.toBuilder().exclusiveStartKey(response.lastEvaluatedKey()).build();
			} while (response.hasLastEvaluatedKey());

		} catch (DynamoDbException e) {
			System.err.println("Error al listar los elementos de la tabla " + tableName + ": " + e.getMessage());
		} finally {
			dynamoDbClient.close();
		}
	}

	// Método para imprimir los campos de un elemento
	private static void imprimirCampos(Map<String, AttributeValue> item) {
		StringBuilder builder = new StringBuilder();
		for (Map.Entry<String, AttributeValue> entry : item.entrySet()) {
			String campo = entry.getKey();
			AttributeValue valor = entry.getValue();
			if (valor != null) {
				if (valor.s() != null) {
					builder.append(campo).append(": ").append(valor.s()).append(", ");
				} else if (valor.n() != null) {
					builder.append(campo).append(": ").append(valor.n()).append(", ");
				} else if (valor.m() != null) {
					builder.append(campo).append(": ").append(imprimirMapa(valor.m())).append(", ");
				} else {
					builder.append(campo).append(": NULL, "); // Manejar el caso en que el campo es nulo
				}
			}
		}
		LOGGER.info(builder.toString());
	}

	// Método para imprimir un mapa
	private static String imprimirMapa(Map<String, AttributeValue> mapa) {
		StringBuilder builder = new StringBuilder();
		builder.append("{ ");
		for (Map.Entry<String, AttributeValue> entry : mapa.entrySet()) {
			String clave = entry.getKey();
			AttributeValue valor = entry.getValue();
			builder.append(clave).append(": ");
			if (valor.s() != null) {
				builder.append(valor.s());
			} else if (valor.n() != null) {
				builder.append(valor.n());
			} else {
				builder.append("NULL"); // Manejar el caso en que el valor del mapa es nulo
			}
			builder.append(", ");
		}
		builder.append("}");
		return builder.toString();
	}

}

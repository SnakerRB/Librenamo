package aws.connection;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;
import software.amazon.awssdk.services.dynamodb.model.GetItemResponse;
import software.amazon.awssdk.services.dynamodb.model.ScanRequest;
import software.amazon.awssdk.services.dynamodb.model.ScanResponse;

public class AWS_DDB_Select {

	private static final Logger LOGGER = LoggerFactory.getLogger(AWS_DDB_Select.class);

	// -------------------- SELECT * FROM table --------------------
	public static void SelectAll(DynamoDbClient dynamoDbClient, String tableName) {

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
			LOGGER.error("Error al listar los elementos de la tabla " + tableName + ": " + e.getMessage());
		} finally {
			dynamoDbClient.close();
		}
	}

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
					builder.append(campo).append(": NULL, ");
				}
			}
		}
		LOGGER.info(builder.toString());
	}

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
				builder.append("NULL");
			}
			builder.append(", ");
		}
		builder.append("}");
		return builder.toString();
	}

	// -------------------- SELECT * FROM table WHERE pk --------------------
	public static void buscarItem(DynamoDbClient dynamoDbClient, String tableName, String partitionKey, String sortKey) {
		String partitionKeyName;
		String SortKeyName = null;
		Map<String, AttributeValue> keyMap = new HashMap<>();

		switch (tableName) {
		case "Libros":
			partitionKeyName = "ISBN";
			break;
		case "Empleados":
			partitionKeyName = "EmpleadoID";
			break;
		case "Ventas":
			partitionKeyName = "VentaID";
			SortKeyName = "FechaVenta";
			break;
		default:
			partitionKeyName = "";
			SortKeyName = null;
		}

		keyMap.put(partitionKeyName, AttributeValue.builder().s(partitionKey).build());

		// Si se proporciona una sortKey, agrégala al mapa de claves
		if (sortKey != null && !sortKey.isEmpty()) {
			keyMap.put(SortKeyName, AttributeValue.builder().s(sortKey).build());
		}

		try {
			GetItemRequest getItemRequest = GetItemRequest.builder().tableName(tableName).key(keyMap).build();

			GetItemResponse getItemResponse = dynamoDbClient.getItem(getItemRequest);

			Map<String, AttributeValue> item = getItemResponse.item();

			if (item != null) {
				for (Map.Entry<String, AttributeValue> entry : item.entrySet()) {
					String key = entry.getKey();
					AttributeValue value = entry.getValue();

					if (value.s() != null) {
						System.out.println(key + ": " + value.s());
					} else if (value.n() != null) {
						System.out.println(key + ": " + value.n());
					} else {
						// Manejar otros tipos de datos si es necesario
						System.out.println(key + ": " + "Tipo de dato no compatible");
					}
				}
			} else {
				LOGGER.warn("No se encontró ningún item con la Partition Key proporcionada.");
			}
		} catch (DynamoDbException e) {
			LOGGER.error("Error al buscar el item: " + e.getMessage());
		}
	}

}

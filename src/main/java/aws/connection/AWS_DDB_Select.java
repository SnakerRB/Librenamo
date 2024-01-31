package aws.connection;

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
	public static void buscarItem(DynamoDbClient dynamoDbClient, String tableName, String partitionKey) {

		String PartitionKeyName = "";
		switch (tableName) {
			case "Libros": PartitionKeyName = "ISBN";
			break;
			case "Empleados": PartitionKeyName = "EmpleadoID";
			break;
			case "Ventas": PartitionKeyName = "VentaID";
			break;
		}

		try {
			GetItemRequest getItemRequest = GetItemRequest.builder().tableName(tableName)
					.key(Map.of(PartitionKeyName, AttributeValue.builder().s(partitionKey).build())).build();

			GetItemResponse getItemResponse = dynamoDbClient.getItem(getItemRequest);

			Map<String, AttributeValue> item = getItemResponse.item();

			if (item != null) {
				for (Map.Entry<String, AttributeValue> entry : item.entrySet()) {
					System.out.println(entry.getKey() + ": " + entry.getValue().s());
				}
			} else {
				LOGGER.warn("No se encontró ningún item con la Partition Key proporcionada.");
			}
		} catch (DynamoDbException e) {
			LOGGER.error("Error al buscar el item: " + e.getMessage());
		}
	}

}

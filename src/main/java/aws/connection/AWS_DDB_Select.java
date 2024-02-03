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

/**
 * Esta clase proporciona métodos para realizar consultas SELECT en tablas
 * DynamoDB.
 */
public class AWS_DDB_Select {

	private static final Logger LOGGER = LoggerFactory.getLogger(AWS_DDB_Select.class);

	/**
	 * Realiza una consulta SELECT * en la tabla especificada y muestra todos los
	 * registros.
	 *
	 * @param dynamoDbClient El cliente DynamoDB utilizado para realizar la
	 *                       operación.
	 * @param tableName      El nombre de la tabla en la que se realizará la
	 *                       consulta.
	 */
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

	/**
	 * Esta clase contiene métodos auxiliares para imprimir campos y mapas de
	 * atributos.
	 */
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

	/**
	 * Este método convierte un mapa de atributos en una representación de cadena
	 * para su impresión.
	 *
	 * @param mapa El mapa de atributos que se va a imprimir.
	 * @return Una cadena que representa el mapa de atributos.
	 */
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

	/**
	 * Realiza una consulta SELECT en la tabla especificada utilizando la clave de
	 * partición proporcionada y, opcionalmente, la clave de rango.
	 *
	 * @param dynamoDbClient El cliente DynamoDB utilizado para realizar la
	 *                       operación.
	 * @param tableName      El nombre de la tabla en la que se realizará la
	 *                       consulta.
	 * @param partitionKey   La clave de partición utilizada en la consulta.
	 * @param sortKey        La clave de rango utilizada en la consulta (opcional).
	 */
	public static void buscarItem(DynamoDbClient dynamoDbClient, String tableName, String partitionKey,
			String sortKey) {
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

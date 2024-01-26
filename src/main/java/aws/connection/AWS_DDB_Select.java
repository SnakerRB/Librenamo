package aws.connection;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;
import software.amazon.awssdk.services.dynamodb.model.ScanRequest;
import software.amazon.awssdk.services.dynamodb.model.ScanResponse;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;

public class AWS_DDB_Select {

	public static void SelectLibro(DynamoDbClient ddb, String tableName, String isbn, String titulo) {
        HashMap<String, AttributeValue> keyToGet = new HashMap<>();
        keyToGet.put("ISBN", AttributeValue.builder().s(isbn).build());
        keyToGet.put("Titulo", AttributeValue.builder().s(titulo).build());

        GetItemRequest request = GetItemRequest.builder()
                .key(keyToGet)
                .tableName(tableName)
                .build();

        try {
            Map<String, AttributeValue> returnedItem = ddb.getItem(request).item();
            if (returnedItem.isEmpty()) {
                System.out.format("No item found with partition key %s and sort key %s!\n", isbn, titulo);
            } else {
                Set<String> keys = returnedItem.keySet();
                System.out.println("Amazon DynamoDB table attributes: \n");
                for (String key : keys) {
                    System.out.format("%s: %s\n", key, returnedItem.get(key).toString());
                }
            }

        } catch (DynamoDbException e) {
            System.err.println("Error getting item: " + e.getMessage());
            System.exit(1);
        }
    }

	public static void SelectAll(DynamoDbClient dynamoDbClient, String tableName, String[] campos) {
		// Realiza una consulta simple para listar todos los elementos
		ScanRequest scanRequest = ScanRequest.builder().tableName(tableName)
				.projectionExpression(projectionExpression(campos))
				.expressionAttributeNames(expressionAttributeNames(campos)).build();

		try {
			ScanResponse response;
			do {
				response = dynamoDbClient.scan(scanRequest);
				for (Map<String, AttributeValue> item : response.items()) {
					imprimirCampos(item, campos);
				}

				scanRequest = scanRequest.toBuilder().exclusiveStartKey(response.lastEvaluatedKey()).build();
			} while (response.hasLastEvaluatedKey());

		} catch (DynamoDbException e) {
			System.err.println("Error al listar los libros: " + e.getMessage());
		} finally {
			dynamoDbClient.close();
		}
	}

	// Método para construir la expresión de proyección
	private static String projectionExpression(String[] campos) {
		StringBuilder expression = new StringBuilder();
		for (int i = 0; i < campos.length; i++) {
			expression.append("#").append(campos[i]);
			if (i < campos.length - 1) {
				expression.append(", ");
			}
		}
		return expression.toString();
	}

	// Método para construir el mapa de nombres de atributos de expresión
	private static Map<String, String> expressionAttributeNames(String[] campos) {
		Map<String, String> attributeNames = new HashMap<>();
		for (String campo : campos) {
			attributeNames.put("#" + campo, campo);
		}
		return attributeNames;
	}

	// Método para imprimir los campos de un elemento
	private static void imprimirCampos(Map<String, AttributeValue> item, String[] campos) {
		StringBuilder builder = new StringBuilder();
		for (String campo : campos) {
			if (item.containsKey(campo) && item.get(campo) != null && item.get(campo).s() != null) {
				builder.append(campo).append(": ").append(item.get(campo).s()).append(", ");
			} else {
				builder.append(campo).append(": NULL, "); // Manejar el caso en que el campo es nulo
			}
		}
		System.out.println(builder.toString());
	}

}

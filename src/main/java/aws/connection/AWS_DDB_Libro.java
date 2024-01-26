package aws.connection;

import java.util.HashMap;
import java.util.Map;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.DeleteItemRequest;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.dynamodb.model.ReturnValue;
import software.amazon.awssdk.services.dynamodb.model.UpdateItemRequest;
import software.amazon.awssdk.services.dynamodb.model.UpdateItemResponse;

public class AWS_DDB_Libro {

	public static void Create(DynamoDbClient dynamoDbClient, String tableName, Libro libro) {
		try {
			// Crea un mapa de atributos para el libro
			// El nombre de la clave debe coincidir con los nombres de las columnas en la
			// tabla DynamoDB
			// y el valor es el objeto AttributeValue correspondiente
			PutItemRequest putItemRequest = PutItemRequest.builder()
					.tableName(tableName)
					.item(Map.of(
							"ISBN", libro.getISBN(), 
							"Titulo", libro.getTitulo(), 
							"Autor", libro.getAutor(),
							"AnoPublicacion", libro.getAnoPublicacion(), 
							"Editorial", libro.getEditorial(), 
							"Genero",libro.getGenero()))
					.build();

			// Inserta el libro en la tabla DynamoDB
			dynamoDbClient.putItem(putItemRequest);
			System.out.println("Libro insertado: " + libro.getISBN());

		} catch (DynamoDbException e) {
			System.err.println("Error al insertar el libro: " + e.getMessage());
		} finally {
			// Cierra el cliente de DynamoDB
			dynamoDbClient.close();
		}

	}

	public static void Delete(DynamoDbClient dynamoDbClient, String tableName, String isbn, String titulo) {
		HashMap<String, AttributeValue> keyToDelete = new HashMap<>();
		keyToDelete.put("ISBN", AttributeValue.builder().s(isbn).build());
		keyToDelete.put("Titulo", AttributeValue.builder().s(titulo).build());

		DeleteItemRequest deleteItemRequest = DeleteItemRequest.builder()
				.tableName(tableName)
				.key(keyToDelete)
				.build();

		try {
			dynamoDbClient.deleteItem(deleteItemRequest);
			System.out.format("Item con clave de partición %s y clave de rango %s eliminado exitosamente.\n", isbn,
					titulo);
		} catch (DynamoDbException e) {
			System.err.println("Error al eliminar el libro: " + e.getMessage());
		}
	}

	public static void Edit(DynamoDbClient dynamoDbClient, String tableName, String isbn, String titulo, String campo,
			String valor) {
		// Primero, obtenemos el objeto que deseamos editar
		HashMap<String, AttributeValue> keyToGet = new HashMap<>();
		keyToGet.put("ISBN", AttributeValue.builder().s(isbn).build());
		keyToGet.put("Titulo", AttributeValue.builder().s(titulo).build());

		GetItemRequest getItemRequest = GetItemRequest.builder()
				.tableName(tableName)
				.key(keyToGet)
				.build();

		Map<String, AttributeValue> item = dynamoDbClient.getItem(getItemRequest).item();

		if (item.isEmpty()) {
			System.out.format("No se encontró ningún objeto con la clave de partición %s.\n", isbn);
			return;
		} else {
			System.out.println(item);

			// Luego, actualiza el atributo del objeto
			UpdateItemRequest updateItemRequest = UpdateItemRequest.builder()
					.tableName(tableName)
					.key(keyToGet)
					.updateExpression("SET " + campo + " = :newAttributeValue")
					.expressionAttributeValues(Map.of(":newAttributeValue", AttributeValue.builder().s(valor).build()))
					.returnValues(ReturnValue.UPDATED_NEW)
					.build();

			try {
				UpdateItemResponse updateItemResponse = dynamoDbClient.updateItem(updateItemRequest);
				Map<String, AttributeValue> updatedItem = updateItemResponse.attributes();
				System.out.println("Objeto actualizado exitosamente.");

				GetItemRequest getItemRequest2 = GetItemRequest.builder()
						.tableName(tableName)
						.key(keyToGet)
						.build();
				
				Map<String, AttributeValue> item2 = dynamoDbClient.getItem(getItemRequest).item();
				System.out.println(item2);

			} catch (DynamoDbException e) {
				System.err.println("Error al actualizar el objeto: " + e.getMessage());
			}

		}

	}

}

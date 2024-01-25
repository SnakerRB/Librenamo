package aws.connection;

import java.util.HashMap;
import java.util.Map;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.DeleteItemRequest;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;

public class AWS_DDB_Libro {

	public static void Create(DynamoDbClient dynamoDbClient, String tableName, Libro libro) {
		try {
			// Crea un mapa de atributos para el libro
			// El nombre de la clave debe coincidir con los nombres de las columnas en la
			// tabla DynamoDB
			// y el valor es el objeto AttributeValue correspondiente
			PutItemRequest putItemRequest = PutItemRequest.builder().tableName(tableName)
					.item(Map.of("ISBN", libro.getISBN(), "Titulo", libro.getTitulo(), "Autor", libro.getAutor(),
							"AnoPublicacion", libro.getAnoPublicacion(), "Editorial", libro.getEditorial(), "Genero",
							libro.getGenero()))
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

		DeleteItemRequest deleteItemRequest = DeleteItemRequest.builder().tableName(tableName).key(keyToDelete).build();

		try {
			dynamoDbClient.deleteItem(deleteItemRequest);
			System.out.format("Item con clave de partición %s y clave de rango %s eliminado exitosamente.\n",
					isbn, titulo);
		} catch (DynamoDbException e) {
			System.err.println("Error al eliminar el libro: " + e.getMessage());
		}
	}

	public static void Edit() {

	}

}

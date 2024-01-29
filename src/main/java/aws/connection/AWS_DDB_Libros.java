package aws.connection;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.DeleteItemRequest;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.dynamodb.model.ReturnValue;
import software.amazon.awssdk.services.dynamodb.model.UpdateItemRequest;
import software.amazon.awssdk.services.dynamodb.model.UpdateItemResponse;

public class AWS_DDB_Libros {

	public static void Create(DynamoDbClient dynamoDbClient, String tableName, Libro libro) {
	    try {
	        // Crea un mapa de atributos para el libro
	        // El nombre de la clave debe coincidir con los nombres de las columnas en la
	        // tabla DynamoDB
	        // y el valor es el objeto AttributeValue correspondiente
	        PutItemRequest putItemRequest = PutItemRequest.builder().tableName(tableName)
	                .item(Map.of(
	                    "ISBN", AttributeValue.builder().s(libro.getISBN()).build(),
	                    "Anio", AttributeValue.builder().n(String.valueOf(libro.getAnio())).build(),
	                    "Autor", AttributeValue.builder().s(libro.getAutor()).build(),
	                    "Editorial", AttributeValue.builder().s(libro.getEditorial()).build(),
	                    "Existencias", AttributeValue.builder().n(String.valueOf(libro.getExistencias())).build(),
	                    "Genero", AttributeValue.builder().s(libro.getGenero()).build(),
	                    "Precio", AttributeValue.builder().n(String.valueOf(libro.getPrecio())).build(),
	                    "Titulo", AttributeValue.builder().s(libro.getTitulo()).build()
	                ))
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

		GetItemRequest getItemRequest = GetItemRequest.builder().tableName(tableName).key(keyToGet).build();

		Map<String, AttributeValue> item = dynamoDbClient.getItem(getItemRequest).item();

		if (item.isEmpty()) {
			System.out.format("No se encontró ningún objeto con la clave de partición %s.\n", isbn);
			return;
		} else {
			System.out.println(item);

			// Luego, actualiza el atributo del objeto
			UpdateItemRequest updateItemRequest = UpdateItemRequest.builder().tableName(tableName).key(keyToGet)
					.updateExpression("SET " + campo + " = :newAttributeValue")
					.expressionAttributeValues(Map.of(":newAttributeValue", AttributeValue.builder().s(valor).build()))
					.returnValues(ReturnValue.UPDATED_NEW).build();

			try {
				UpdateItemResponse updateItemResponse = dynamoDbClient.updateItem(updateItemRequest);
				Map<String, AttributeValue> updatedItem = updateItemResponse.attributes();
				System.out.println("Objeto actualizado exitosamente: " + updatedItem);

				System.out.println(item);

			} catch (DynamoDbException e) {
				System.err.println("Error al actualizar el objeto: " + e.getMessage());
			}

		}

	}

	public static void ImportFromJson(DynamoDbClient dynamoDbClient, String tableName, String jsonFilePath) {

		try {
			String jsonContent = new String(Files.readAllBytes(Paths.get(jsonFilePath)));

			// Convierte el JSON a una lista de objetos Libro
			ObjectMapper objectMapper = new ObjectMapper();
			List<Map<String, Map<String, String>>> libros = objectMapper.readValue(jsonContent,
					new TypeReference<List<Map<String, Map<String, String>>>>() {
					});

			// Inserta cada libro en la tabla
			for (Map<String, Map<String, String>> libro : libros) {
				// Extrae los valores de los campos del libro
				Map<String, String> isbnMap = libro.get("ISBN");
				Map<String, String> tituloMap = libro.get("Titulo");
				Map<String, String> autorMap = libro.get("Autor");
				Map<String, String> anoPublicacionMap = libro.get("AnoPublicacion");
				Map<String, String> editorialMap = libro.get("Editorial");
				Map<String, String> generoMap = libro.get("Genero");

				// Verifica que todos los campos tengan valores
				if (isbnMap != null && tituloMap != null && autorMap != null && anoPublicacionMap != null
						&& editorialMap != null && generoMap != null) {
					// Crea un objeto AttributeValue para cada campo del libro
					AttributeValue isbnValue = AttributeValue.builder().s(isbnMap.get("S")).build();
					AttributeValue tituloValue = AttributeValue.builder().s(tituloMap.get("S")).build();
					AttributeValue autorValue = AttributeValue.builder().s(autorMap.get("S")).build();
					AttributeValue anoPublicacionValue = AttributeValue.builder().s(anoPublicacionMap.get("S")).build();
					AttributeValue editorialValue = AttributeValue.builder().s(editorialMap.get("S")).build();
					AttributeValue generoValue = AttributeValue.builder().s(generoMap.get("S")).build();

					// Crea un mapa de atributos para el libro
					// El nombre de la clave debe coincidir con los nombres de las columnas en la
					// tabla DynamoDB
					// y el valor es el objeto AttributeValue correspondiente
					Map<String, AttributeValue> item = Map.of("ISBN", isbnValue, "Titulo", tituloValue, "Autor",
							autorValue, "AnoPublicacion", anoPublicacionValue, "Editorial", editorialValue, "Genero",
							generoValue);

					// Crea la solicitud de inserción y la ejecuta
					PutItemRequest putItemRequest = PutItemRequest.builder().tableName(tableName).item(item).build();

					dynamoDbClient.putItem(putItemRequest);
					System.out.println("Libro insertado: " + isbnValue.s());
				} else {
					System.err.println("Error: Uno o más campos del libro son nulos.");
				}
			}

		} catch (IOException e) {
			System.err.println("Error al leer el archivo JSON: " + e.getMessage());
		} catch (DynamoDbException e) {
			System.err.println("Error al insertar los libros: " + e.getMessage());
		} finally {
			dynamoDbClient.close();
		}
	}
}

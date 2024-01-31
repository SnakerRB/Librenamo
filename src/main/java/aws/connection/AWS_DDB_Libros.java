package aws.connection;

import java.io.IOException;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
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

	private static final Logger LOGGER = LoggerFactory.getLogger(AWS_DDB_Libros.class);

	public static void Create(DynamoDbClient dynamoDbClient, Libro libro) {
		try {
			// Crea un mapa de atributos para el libro
			Map<String, AttributeValue> item = Map.of("ISBN", libro.getISBN(), "Anio", libro.getAnio(), "Autor",
					libro.getAutor(), "Editorial", libro.getEditorial(), "Existencias", libro.getExistencias(),
					"Genero", libro.getGenero(), "Precio", libro.getPrecio(), "Titulo", libro.getTitulo());

			PutItemRequest putItemRequest = PutItemRequest.builder().tableName("Libros").item(item).build();

			// Inserta el libro en la tabla DynamoDB
			dynamoDbClient.putItem(putItemRequest);
			LOGGER.info("Libro insertado: " + libro.getISBN().s());

		} catch (DynamoDbException e) {
			LOGGER.error("Error al insertar el libro: " + e.getMessage());
		} finally {
			dynamoDbClient.close();
		}
	}

	public static void Delete(DynamoDbClient dynamoDbClient, String isbn) {
		HashMap<String, AttributeValue> keyToDelete = new HashMap<>();
		keyToDelete.put("ISBN", AttributeValue.builder().s(isbn).build());

		DeleteItemRequest deleteItemRequest = DeleteItemRequest.builder().tableName("Libros").key(keyToDelete).build();

		try {
			dynamoDbClient.deleteItem(deleteItemRequest);
			LOGGER.info("Item con clave de partición %s y clave de rango %s eliminado exitosamente.\n", isbn);
		} catch (DynamoDbException e) {
			LOGGER.error("Error al eliminar el libro: " + e.getMessage());
		} finally {
			dynamoDbClient.close();
		}
	}

	public static void Edit(DynamoDbClient dynamoDbClient, String isbn, String campo, String valor) {
		// Primero, obtenemos el objeto que deseamos editar
		HashMap<String, AttributeValue> keyToGet = new HashMap<>();
		keyToGet.put("ISBN", AttributeValue.builder().s(isbn).build());

		GetItemRequest getItemRequest = GetItemRequest.builder().tableName("Libros").key(keyToGet).build();

		Map<String, AttributeValue> item = dynamoDbClient.getItem(getItemRequest).item();

		if (item.isEmpty()) {
			LOGGER.error("No se encontró ningún objeto con la clave de partición: ", isbn);
			return;

		} else {
			LOGGER.info(item.toString());

			// Actualiza el atributo del objeto
			UpdateItemRequest updateItemRequest = UpdateItemRequest.builder().tableName("Libros").key(keyToGet)
					.updateExpression("SET " + campo + " = :newAttributeValue")
					.expressionAttributeValues(Map.of(":newAttributeValue", AttributeValue.builder().s(valor).build()))
					.returnValues(ReturnValue.UPDATED_NEW).build();

			try {
				UpdateItemResponse updateItemResponse = dynamoDbClient.updateItem(updateItemRequest);
				Map<String, AttributeValue> updatedItem = updateItemResponse.attributes();
				LOGGER.info("Objeto actualizado exitosamente: " + updatedItem);

			} catch (DynamoDbException e) {
				LOGGER.error("Error al actualizar el objeto: " + e.getMessage());
			} finally {
				dynamoDbClient.close();
			}
		}
	}
	
	public static ArrayList<Libro> ImportFromJson(DynamoDbClient dynamoDbClient, String jsonFilePath) {
		ObjectMapper objectMapper = new ObjectMapper();
		ArrayList<Libro> libros = new ArrayList<>();

        try {
            // Lee el JSON desde el archivo
            JsonNode jsonArray = objectMapper.readTree(new File(jsonFilePath));

            // Verifica si el JSON es un arreglo
            if (jsonArray.isArray()) {
                for (JsonNode jsonNode : jsonArray) {
                    // Verifica si el JSON contiene todas las propiedades necesarias
                    if (jsonNode.has("ISBN") && jsonNode.has("Titulo") && jsonNode.has("Anio")
                            && jsonNode.has("Autor") && jsonNode.has("Editorial")
                            && jsonNode.has("Existencias") && jsonNode.has("Genero")
                            && jsonNode.has("Precio")) {

                        // Crea un nuevo objeto Libro y asigna los valores
                        Libro libro = new Libro();
                        libro.setISBN(jsonNode.get("ISBN").asText());
                        libro.setTitulo(jsonNode.get("Titulo").asText());
                        libro.setAnio(jsonNode.get("Anio").asText());
                        libro.setAutor(jsonNode.get("Autor").asText());
                        libro.setEditorial(jsonNode.get("Editorial").asText());
                        libro.setExistencias(jsonNode.get("Existencias").asText());
                        libro.setGenero(jsonNode.get("Genero").asText());
                        libro.setPrecio(jsonNode.get("Precio").asText());

                        libros.add(libro);
                    } else {
                        System.err.println("Error: Uno o más objetos en el JSON no contienen todas las propiedades necesarias.");
                    }
                }
            } else {
                System.err.println("Error: El JSON no es un arreglo.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return libros;
    }
}

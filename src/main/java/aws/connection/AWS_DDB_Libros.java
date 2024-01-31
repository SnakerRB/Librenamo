package aws.connection;

import java.io.IOException;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

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
import software.amazon.awssdk.services.dynamodb.model.ScanRequest;
import software.amazon.awssdk.services.dynamodb.model.ScanResponse;
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
			LOGGER.trace("Map y ItemRequest realizado correctamente");
			// Inserta el libro en la tabla DynamoDB
			dynamoDbClient.putItem(putItemRequest);
			LOGGER.info("Libro insertado: " + libro.getISBN().s());

		} catch (DynamoDbException e) {
			LOGGER.error("Error al insertar el libro: " + e.getMessage());
		} finally {
			dynamoDbClient.close();
			LOGGER.trace("Cliente DB Cerrado correctamente");
		}
	}

	public static void Delete(DynamoDbClient dynamoDbClient, String isbn) {
		HashMap<String, AttributeValue> keyToDelete = new HashMap<>();
		keyToDelete.put("ISBN", AttributeValue.builder().s(isbn).build());
		LOGGER.info("HashMap y Clave generados correctemente");
		DeleteItemRequest deleteItemRequest = DeleteItemRequest.builder().tableName("Libros").key(keyToDelete).build();

		try {
			dynamoDbClient.deleteItem(deleteItemRequest);
			LOGGER.info("Item con clave de partición %s y clave de rango %s eliminado exitosamente.\n", isbn);
		} catch (DynamoDbException e) {
			LOGGER.error("Error al eliminar el libro: " + e.getMessage());
		} finally {
			dynamoDbClient.close();
			LOGGER.info("Cliente DB Cerrado correctamente");
		}
	}

	public static void Edit(DynamoDbClient dynamoDbClient, String isbn, String campo, String valor) {
		// Primero, obtenemos el objeto que deseamos editar
		HashMap<String, AttributeValue> keyToGet = new HashMap<>();
		keyToGet.put("ISBN", AttributeValue.builder().s(isbn).build());
		LOGGER.info("HashMap y Clave generados correctemente");
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
				LOGGER.info("Cliente DB Cerrado correctamente");
			}
		}
	}
	 public static ArrayList<Libro> getAllLibros(DynamoDbClient dynamoDbClient) {
	        ArrayList<Libro> libros = new ArrayList<>();

	        ScanResponse response;
	        Map<String, AttributeValue> lastEvaluatedKey = null;
	        int count=0;
	        do {
	            ScanRequest scanRequest = ScanRequest.builder()
	                    .tableName("Libros")
	                    .exclusiveStartKey(lastEvaluatedKey)
	                    .build();

	            response = dynamoDbClient.scan(scanRequest);

	            List<Map<String, AttributeValue>> items = response.items();
	            for (Map<String, AttributeValue> item : items) {
	                Libro libro = new Libro();
	                count++;
	                if (item.containsKey("ISBN")) {
	                    libro.setISBN(item.get("ISBN").s());
	                }

	                if (item.containsKey("Anio")) {
	                    libro.setAnio(item.get("Anio").n());
	                }

	                if (item.containsKey("Autor")) {
	                    libro.setAutor(item.get("Autor").s());
	                }

	                if (item.containsKey("Editorial")) {
	                    libro.setEditorial(item.get("Editorial").s());
	                }

	                if (item.containsKey("Existencias")) {
	                    libro.setExistencias(item.get("Existencias").n());
	                }

	                if (item.containsKey("Genero")) {
	                    libro.setGenero(item.get("Genero").s());
	                }

	                if (item.containsKey("Precio")) {
	                    libro.setPrecio(item.get("Precio").n());
	                }

	                if (item.containsKey("Titulo")) {
	                    libro.setTitulo(item.get("Titulo").s());
	                }

	                libros.add(libro);
	            }

	            lastEvaluatedKey = response.lastEvaluatedKey();

	        } while (lastEvaluatedKey != null && !lastEvaluatedKey.isEmpty());
	        LOGGER.info("Se han recuperado "+count+" registros de Libros en la BBDD");
	        return libros;
	    }
	
	
	
	
}

package aws.connection;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

public class AWS_DDB_Import {

	public static void MegaImport(DynamoDbClient dynamoDbClient, String tableName, String jsonFilePath) {

		try {
            String jsonContent = new String(Files.readAllBytes(Paths.get(jsonFilePath)));

            // Convierte el JSON a una lista de objetos Libro
            ObjectMapper objectMapper = new ObjectMapper();
            List<Map<String, Map<String, String>>> libros = objectMapper.readValue(jsonContent, new TypeReference<List<Map<String, Map<String, String>>>>() {});

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
                if (isbnMap != null && tituloMap != null && autorMap != null && anoPublicacionMap != null && editorialMap != null && generoMap != null) {
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
                    Map<String, AttributeValue> item = Map.of("ISBN", isbnValue, "Titulo", tituloValue, "Autor", autorValue,
                            "AnoPublicacion", anoPublicacionValue, "Editorial", editorialValue, "Genero", generoValue);

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

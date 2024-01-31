package aws.connection;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonHandler {
	
	public static void ImportarLibros (String jsonFilePath) {
	for(Libro li :LeerJsonLibros(jsonFilePath)) {
		AWS_DDB_Libros.Create(AWS_DDB_Login.Logg(),li);

	
		}
	}
	/*
	public static void ImportarEmpleados (String jsonFilePath) {
	for(Libro emp :LeerJsonEmpleados(jsonFilePath)) {
		AWS_DDB_Empleados.Create(AWS_DDB_Login.Logg(),emp);

	
		}
	}	
	
	*/
	
	
	
	private static ArrayList<Libro> LeerJsonLibros(String jsonFilePath) {
		ArrayList<Libro> libros_json = new ArrayList<>();

		ObjectMapper objectMapper = new ObjectMapper();

		try {
			JsonNode jsonArray = objectMapper.readTree(new File(jsonFilePath));

			if (jsonArray.isArray()) {
				for (JsonNode jsonNode : jsonArray) {
					Libro libro = new Libro();

					if (jsonNode.has("ISBN")) {
						libro.setISBN(jsonNode.get("ISBN").asText());
					}

					if (jsonNode.has("Titulo")) {
						libro.setTitulo(jsonNode.get("Titulo").asText());
					}

					if (jsonNode.has("Anio")) {
						libro.setAnio(String.valueOf(jsonNode.get("Anio").asInt()));
					}

					if (jsonNode.has("Autor")) {
						libro.setAutor(jsonNode.get("Autor").asText());
					}

					if (jsonNode.has("Editorial")) {
						libro.setEditorial(jsonNode.get("Editorial").asText());
					}

					if (jsonNode.has("Existencias")) {
						libro.setExistencias(String.valueOf(jsonNode.get("Existencias").asInt()));
					}

					if (jsonNode.has("Genero")) {
						libro.setGenero(jsonNode.get("Genero").asText());
					}

					if (jsonNode.has("Precio")) {
						libro.setPrecio(String.valueOf(jsonNode.get("Precio").asDouble()));
					}

					libros_json.add(libro);
				}
			} else {
				System.err.println("Error: El JSON no es un arreglo.");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return libros_json;
	}
}

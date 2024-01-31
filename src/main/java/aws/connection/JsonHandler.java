package aws.connection;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;


public class JsonHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(JsonHandler.class);
	public static void ImportarLibros(String jsonFilePath) {
		for (Libro li : LeerJsonLibros(jsonFilePath)) {
			AWS_DDB_Libros.Create(AWS_DDB_Login.Logg(), li);

		}
	}
	public static void ImportarEmpleados(String jsonFilePath) {
		for (Empleado emp : LeerJsonEmpleados(jsonFilePath)) {
			AWS_DDB_Empleados.Create(AWS_DDB_Login.Logg(), emp);

		}
	}
	public static void ExportarEmpleados(String OutFilePathEmpleados) {
		
		JsonHandler.EmpleadosJson(AWS_DDB_Empleados.getAllEmpleados(AWS_DDB_Login.Logg()), OutFilePathEmpleados);
		LOGGER.info("Datos de Empleados recuperados en el directorio: "+OutFilePathEmpleados);
	}
	
	public static void ExportarLibros(String OutFilePathLibros) {
		
		JsonHandler.LibrosJson(AWS_DDB_Libros.getAllLibros(AWS_DDB_Login.Logg()), OutFilePathLibros);
		LOGGER.info("Datos de Libros recuperados en el directorio: "+OutFilePathLibros);
	}
	 
	 
	// metodo para recuperar los datos de los empleados de la bbdd y insertarlos en un array de objetos empleado 
	public static ArrayList<Empleado> LeerJsonEmpleados(String jsonFilePath) {
		ArrayList<Empleado> empleados_json = new ArrayList<>();

		ObjectMapper objectMapper = new ObjectMapper();

		try {
			JsonNode jsonArray = objectMapper.readTree(new File(jsonFilePath));

			if (jsonArray.isArray()) {
				for (JsonNode jsonNode : jsonArray) {
					Empleado empleado = new Empleado();

					if (jsonNode.has("EmpleadoID")) {
						empleado.setEmpleadoID(jsonNode.get("EmpleadoID").asText());
					}

					if (jsonNode.has("Apellido")) {
						empleado.setApellido(jsonNode.get("Apellido").asText());
					}

					if (jsonNode.has("Cargo")) {
						empleado.setCargo(jsonNode.get("Cargo").asText());
					}

					if (jsonNode.has("Direccion")) {
						empleado.setDireccion(jsonNode.get("Direccion").asText());
					}

					if (jsonNode.has("FechaContrato")) {
						empleado.setFechaContrato(jsonNode.get("FechaContrato").asText());
					}

					if (jsonNode.has("Nombre")) {
						empleado.setNombre(jsonNode.get("Nombre").asText());
					}

					empleados_json.add(empleado);
					
				}
			} else {
				System.err.println("Error: El JSON no es un arreglo.");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		LOGGER.trace("Array de empleados creado correctamente con los datos de la BBDD");
		return empleados_json;
	}	
	
	// metodo para volcar los datos de un array de objetos empleado a un fichero json
	 private static void EmpleadosJson(ArrayList<Empleado> empleados, String OutFilePathEmpleados) {
	        ObjectMapper objectMapper = new ObjectMapper();
	        ArrayNode jsonArray = JsonNodeFactory.instance.arrayNode();

	        for (Empleado empleado : empleados) {
	            ObjectNode jsonNode = objectMapper.createObjectNode();

	            jsonNode.put("EmpleadoID", empleado.getEmpleadoID().s());
	            jsonNode.put("Apellido", empleado.getApellido().s());
	            jsonNode.put("Cargo", empleado.getCargo().s());
	            jsonNode.put("Direccion", empleado.getDireccion().s());
	            jsonNode.put("FechaContrato", empleado.getFechaContrato().s());
	            jsonNode.put("Nombre", empleado.getNombre().s());

	            jsonArray.add(jsonNode);
	        }

	        try {
	            objectMapper.writeValue(new File(OutFilePathEmpleados), jsonArray);
	         
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	// metodo para volcar los datos de un array de objetos Libro a un fichero json
	 public static void LibrosJson(ArrayList<Libro> libros, String outFilePathLibros) {
	        ObjectMapper objectMapper = new ObjectMapper();
	        ArrayNode jsonArray = objectMapper.createArrayNode();

	        for (Libro libro : libros) {
	            ObjectNode jsonNode = objectMapper.createObjectNode();

	            jsonNode.put("ISBN", libro.getISBN().s());
	            jsonNode.put("Anio", libro.getAnio().n());
	            jsonNode.put("Autor", libro.getAutor().s());
	            jsonNode.put("Editorial", libro.getEditorial().s());
	            jsonNode.put("Existencias", libro.getExistencias().n());
	            jsonNode.put("Genero", libro.getGenero().s());
	            jsonNode.put("Precio", libro.getPrecio().n());
	            jsonNode.put("Titulo", libro.getTitulo().s());

	            jsonArray.add(jsonNode);
	        }

	        try {
	            objectMapper.writeValue(new File(outFilePathLibros), jsonArray);
	        
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	
	
		
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
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

package aws.connection;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

/**
 * Clase que maneja la importación y exportación de datos entre JSON y DynamoDB.
 * Esta clase contiene métodos estáticos para convertir datos de y hacia JSON,
 * permitiendo la manipulación y transferencia de datos entre una representación
 * en archivo y la base de datos de AWS DynamoDB.
 */
public class JsonHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(JsonHandler.class);

	// --------------------- IMPORTAR ---------------------
	/**
	 * Importa empleados desde un archivo JSON y los almacena en DynamoDB. Lee un
	 * archivo JSON que contiene datos de empleados, los convierte en objetos
	 * {@link Empleado}, y luego los almacena en DynamoDB.
	 * 
	 * @param jsonFilePath Ruta del archivo JSON a leer.
	 */
	public static void ImportarEmpleados(String jsonFilePath) {
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

					AWS_DDB_Empleados.Create(AWS_DDB_Login.Logg(), empleado);
				}
			} else {
				System.err.println("Error: El JSON no es un arreglo.");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		LOGGER.trace("Importación de empleados completada correctamente.");
	}

	/**
	 * Importa libros desde un archivo JSON y los almacena en DynamoDB. Lee un
	 * archivo JSON que contiene datos de libros, los convierte en objetos
	 * {@link Libro}, y luego los almacena en DynamoDB.
	 * 
	 * @param jsonFilePath Ruta del archivo JSON a leer.
	 */
	public static void ImportarLibros(String jsonFilePath) {
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

					AWS_DDB_Libros.Create(AWS_DDB_Login.Logg(), libro);
				}
			} else {
				LOGGER.error("Error: El JSON no es un arreglo.");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		LOGGER.trace("Importación de Libros completada correctamente.");
	}

	/**
	 * Importa ventas desde un archivo JSON y las almacena en DynamoDB. Lee un
	 * archivo JSON que contiene datos de ventas, los convierte en objetos
	 * {@link Venta}, y luego los almacena en DynamoDB.
	 * 
	 * @param jsonFilePath Ruta del archivo JSON a leer.
	 */
	public static void ImportarVentas(String jsonFilePath) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			JsonNode jsonArray = objectMapper.readTree(new File(jsonFilePath));
			if (jsonArray.isArray()) {
				for (JsonNode jsonNode : jsonArray) {
					Venta venta = new Venta();

					if (jsonNode.has("VentaID")) {
						venta.setVentaID(jsonNode.get("VentaID").asText());
					}
					if (jsonNode.has("FechaVenta")) {
						venta.setFechaVenta(jsonNode.get("FechaVenta").asText());
					}
					if (jsonNode.has("Direccion")) {
						venta.setDireccion(jsonNode.get("Direccion").asText());
					}
					if (jsonNode.has("EmpleadoID")) {
						venta.setEmpleadoID(jsonNode.get("EmpleadoID").asText());
					}
					if (jsonNode.has("TotalVenta")) {
						venta.setTotalVenta(jsonNode.get("TotalVenta").asText());
					}
					if (jsonNode.has("ListaLibrosVendidos")) {
						JsonNode listaLibrosNode = jsonNode.get("ListaLibrosVendidos");
						String cantidad = listaLibrosNode.get("Cantidad").asText();
						String isbn = listaLibrosNode.get("ISBN").asText();
						venta.setListaLibrosVendidos(cantidad, isbn);
					}

					AWS_DDB_Ventas.Create(AWS_DDB_Login.Logg(), venta);
				}
			} else {
				LOGGER.error("Error: El JSON no es un arreglo.");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		LOGGER.trace("Importación de Ventas completada correctamente.");
	}

	// --------------------- EXPORTAR ---------------------
	/**
	 * Exporta los datos de empleados de DynamoDB a un archivo JSON. Recupera todos
	 * los empleados almacenados en DynamoDB y los escribe en un archivo JSON.
	 * 
	 * @param OutFilePathEmpleados Ruta del archivo JSON donde se guardarán los
	 *                             datos exportados.
	 */
	public static void ExportarEmpleados(String OutFilePathEmpleados) {
		ObjectMapper objectMapper = new ObjectMapper();
		ArrayNode jsonArray = JsonNodeFactory.instance.arrayNode();
		ArrayList<Empleado> empleados = AWS_DDB_Empleados.getAllEmpleados(AWS_DDB_Login.Logg());

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
			LOGGER.info("Datos de Empleados recuperados en el directorio: " + OutFilePathEmpleados);

		} catch (IOException e) {
			LOGGER.error("Error con la ruta especificad :" + e);
		}
	}

	/**
	 * Exporta los datos de libros de DynamoDB a un archivo JSON. Recupera todos los
	 * libros almacenados en DynamoDB y los escribe en un archivo JSON.
	 * 
	 * @param OutFilePathLibros Ruta del archivo JSON donde se guardarán los datos
	 *                          exportados.
	 */
	public static void ExportarLibros(String OutFilePathLibros) {
		ObjectMapper objectMapper = new ObjectMapper();
		ArrayNode jsonArray = objectMapper.createArrayNode();
		ArrayList<Libro> libros = AWS_DDB_Libros.getAllLibros(AWS_DDB_Login.Logg());

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
			objectMapper.writeValue(new File(OutFilePathLibros), jsonArray);
			LOGGER.info("Datos de Libros recuperados en el directorio: " + OutFilePathLibros);

		} catch (IOException e) {
			LOGGER.error("Error con la ruta especificad :" + e);
		}
	}

	/**
	 * Exporta los datos de ventas de DynamoDB a un archivo JSON. Recupera todas las
	 * ventas almacenadas en DynamoDB y las escribe en un archivo JSON.
	 * 
	 * @param OutFilePathVentas Ruta del archivo JSON donde se guardarán los datos
	 *                          exportados.
	 */
	public static void ExportarVentas(String OutFilePathVentas) {
		ObjectMapper objectMapper = new ObjectMapper();
		ArrayNode jsonArray = objectMapper.createArrayNode();
		ArrayList<Venta> ventas = AWS_DDB_Ventas.getAllVentas(AWS_DDB_Login.Logg());

		for (Venta venta : ventas) {
			ObjectNode jsonNode = objectMapper.createObjectNode();

			jsonNode.put("VentaID", venta.getVentaID().s());
			jsonNode.put("FechaVenta", venta.getFechaVenta().s());
			jsonNode.put("Direccion", venta.getDireccion().s());
			jsonNode.put("EmpleadoID", venta.getEmpleadoID().s());

			Map<String, AttributeValue> listaLibrosVendidos = venta.getListaLibrosVendidos();
			ObjectNode librosVendidosNode = jsonNode.putObject("ListaLibrosVendidos");
			librosVendidosNode.put("Cantidad", listaLibrosVendidos.get("Cantidad").n());
			librosVendidosNode.put("ISBN", listaLibrosVendidos.get("ISBN").s());

			jsonNode.put("TotalVenta", venta.getTotalVenta().s());

			jsonArray.add(jsonNode);
		}

		try {
			objectMapper.writeValue(new File(OutFilePathVentas), jsonArray);
			LOGGER.info("Datos de Ventas exportados en el directorio: " + OutFilePathVentas);
		} catch (IOException e) {
			LOGGER.error("Error con la ruta especificad :" + e);
		}
	}
}

package aws.connection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.DeleteItemRequest;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;
import software.amazon.awssdk.services.dynamodb.model.GetItemResponse;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.dynamodb.model.ReturnValue;
import software.amazon.awssdk.services.dynamodb.model.ScanRequest;
import software.amazon.awssdk.services.dynamodb.model.ScanResponse;
import software.amazon.awssdk.services.dynamodb.model.Select;
import software.amazon.awssdk.services.dynamodb.model.UpdateItemRequest;
import software.amazon.awssdk.services.dynamodb.model.UpdateItemResponse;

/**
 * Esta clase proporciona métodos para interactuar con una tabla DynamoDB
 * llamada "Ventas". Los métodos incluyen la creación, eliminación, edición y
 * recuperación de registros de ventas.
 */
public class AWS_DDB_Ventas {

	private static final Logger LOGGER = LoggerFactory.getLogger(AWS_DDB_Ventas.class);

	/**
	 * Este método crea un nuevo registro de venta en la tabla DynamoDB "Ventas".
	 *
	 * @param dynamoDbClient El cliente DynamoDB utilizado para realizar la
	 *                       operación.
	 * @param venta          El objeto Venta que se va a insertar en la tabla.
	 */
	public static void Create(DynamoDbClient dynamoDbClient, Venta venta) {
		try {
			// Crea un mapa de atributos para la venta
			Map<String, AttributeValue> item = new HashMap<>();
			LOGGER.info("Mapa creado correctamente");
			item.put("VentaID", venta.getVentaID());
			item.put("FechaVenta", venta.getFechaVenta());
			item.put("Direccion", venta.getDireccion());
			item.put("EmpleadoID", venta.getEmpleadoID());
			item.put("TotalVenta", venta.getTotalVenta());
			item.put("ListaLibrosVendidos", AttributeValue.builder().m(venta.getListaLibrosVendidos()).build());

			PutItemRequest putItemRequest = PutItemRequest.builder().tableName("Ventas").item(item).build();

			// Inserta la venta en la tabla DynamoDB
			dynamoDbClient.putItem(putItemRequest);
			LOGGER.info("Venta insertada: " + venta.getVentaID().s());

		} catch (DynamoDbException e) {
			LOGGER.error("Error al insertar la venta: " + e.getMessage());
		} finally {
			dynamoDbClient.close();
		}
	}

	/**
	 * Este método elimina un registro de venta en la tabla DynamoDB "Ventas" por su
	 * clave de partición (VentaID) y su clave de ordenación (FechaVenta).
	 *
	 * @param dynamoDbClient El cliente DynamoDB utilizado para realizar la
	 *                       operación.
	 * @param ventaID        El ID de la venta que se va a eliminar.
	 * @param FechaVenta     La fecha de la venta que se va a eliminar.
	 */
	public static void Delete(DynamoDbClient dynamoDbClient, String ventaID, String FechaVenta) {
		HashMap<String, AttributeValue> keyToDelete = new HashMap<>();
		keyToDelete.put("VentaID", AttributeValue.builder().s(ventaID).build());
		keyToDelete.put("FechaVenta", AttributeValue.builder().s(FechaVenta).build());
		DeleteItemRequest deleteItemRequest = DeleteItemRequest.builder().tableName("Ventas").key(keyToDelete).build();
		LOGGER.info("HashMap y Clave múltiple generados correctemente");
		try {
			dynamoDbClient.deleteItem(deleteItemRequest);
			LOGGER.info("Venta con clave de partición %s eliminada exitosamente.\n", ventaID);
		} catch (DynamoDbException e) {
			LOGGER.error("Error al eliminar la venta: " + e.getMessage());
		} finally {
			dynamoDbClient.close();
			LOGGER.info("Cliente DB Cerrado correctamente");
		}
	}

	/**
	 * Este método edita un atributo específico de un registro de venta en la tabla
	 * DynamoDB "Ventas".
	 *
	 * @param dynamoDbClient El cliente DynamoDB utilizado para realizar la
	 *                       operación.
	 * @param ventaID        El ID de la venta cuyo atributo se va a editar.
	 * @param campo          El nombre del atributo que se va a editar.
	 * @param valor          El nuevo valor para el atributo.
	 * @param FechaVenta     La fecha de la venta cuyo atributo se va a editar.
	 */
	public static void Edit(DynamoDbClient dynamoDbClient, String ventaID, String campo, String valor,
			String FechaVenta) {
		// Primero, obtenemos el objeto que deseamos editar
		HashMap<String, AttributeValue> keyToGet = new HashMap<>();
		keyToGet.put("VentaID", AttributeValue.builder().s(ventaID).build());
		keyToGet.put("FechaVenta", AttributeValue.builder().s(FechaVenta).build());
		GetItemRequest getItemRequest = GetItemRequest.builder().tableName("Ventas").key(keyToGet).build();
		LOGGER.info("HashMap y Clave múltiple generados correctemente");
		Map<String, AttributeValue> item = dynamoDbClient.getItem(getItemRequest).item();

		if (item.isEmpty()) {
			LOGGER.error("No se encontró ningún objeto con la clave de partición: " + ventaID);
			return;

		} else {
			LOGGER.info(item.toString());

			// Actualiza el atributo del objeto
			UpdateItemRequest updateItemRequest = UpdateItemRequest.builder().tableName("Ventas").key(keyToGet)
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

	/**
	 * Este método genera un nuevo ID de venta incrementando el último ID encontrado
	 * en la tabla DynamoDB "Ventas".
	 *
	 * @param dynamoDbClient El cliente DynamoDB utilizado para realizar la
	 *                       operación.
	 * @return Un nuevo ID de venta generado.
	 */
	public static String IncrementoVentaID(DynamoDbClient dynamoDbClient) {

		ScanRequest scanRequest = ScanRequest.builder().tableName("Ventas").select(Select.ALL_ATTRIBUTES).build();

		ScanResponse scanResponse = dynamoDbClient.scan(scanRequest);

		String ultimoID = "";
		for (Map<String, AttributeValue> item : scanResponse.items()) {
			AttributeValue id = item.get("VentaID");
			if (id != null && id.s() != null) {
				String idString = id.s();
				if (idString.compareTo(ultimoID) > 0) {
					ultimoID = idString;
				}
			}
		}

		if (ultimoID.matches("VEN_\\d{4}")) {

			String parteNumerica = ultimoID.substring(4);
			int numero = Integer.parseInt(parteNumerica);
			numero++;
			String nuevoNumero = String.format("%04d", numero);
			return "VEN_" + nuevoNumero;
		} else {
			return ultimoID;
		}
	}

	/**
	 * Este método obtiene el precio de un libro por su ISBN consultando la tabla
	 * DynamoDB "Libros".
	 *
	 * @param dynamoDbClient El cliente DynamoDB utilizado para realizar la
	 *                       operación.
	 * @param isbn           El ISBN del libro del que se desea obtener el precio.
	 * @return El precio del libro como una cadena de texto, o null si no se
	 *         encontró el libro.
	 */
	public static String obtenerPrecioPorISBN(DynamoDbClient dynamoDbClient, String isbn) {
		GetItemRequest getItemRequest = GetItemRequest.builder().tableName("Libros")
				.key(Map.of("ISBN", AttributeValue.builder().s(isbn).build())).projectionExpression("Precio").build();

		try {
			GetItemResponse getItemResponse = dynamoDbClient.getItem(getItemRequest);
			Map<String, AttributeValue> item = getItemResponse.item();

			if (item != null && item.containsKey("Precio")) {
				AttributeValue precioAttributeValue = item.get("Precio");
				return precioAttributeValue.n();
			}
		} catch (DynamoDbException e) {
			System.err.println("Error al obtener el precio por ISBN: " + e.getMessage());
		}

		return null;
	}

	public static ArrayList<Venta> getAllVentas(DynamoDbClient dynamoDbClient) {
		ArrayList<Venta> ventas = new ArrayList<>();

		ScanResponse response;
		Map<String, AttributeValue> lastEvaluatedKey = null;
		int count = 0;
		do {
			ScanRequest scanRequest = ScanRequest.builder().tableName("Ventas").exclusiveStartKey(lastEvaluatedKey)
					.build();

			response = dynamoDbClient.scan(scanRequest);

			List<Map<String, AttributeValue>> items = response.items();
			for (Map<String, AttributeValue> item : items) {
				Venta venta = new Venta();
				count++;
				if (item.containsKey("VentaID")) {
					venta.setVentaID(item.get("VentaID").s());
				}
				if (item.containsKey("FechaVenta")) {
					venta.setFechaVenta(item.get("FechaVenta").s());
				}
				if (item.containsKey("Direccion")) {
					venta.setDireccion(item.get("Direccion").s());
				}
				if (item.containsKey("EmpleadoID")) {
					venta.setEmpleadoID(item.get("EmpleadoID").s());
				}
				if (item.containsKey("ListaLibrosVendidos")) {
					Map<String, AttributeValue> listaLibrosVendidosMap = item.get("ListaLibrosVendidos").m();
					String cantidad = listaLibrosVendidosMap.get("Cantidad").n();
					String isbn = listaLibrosVendidosMap.get("ISBN").s();
					// Asume que tienes un método para ajustar estos valores
					venta.setListaLibrosVendidos(cantidad, isbn);
				}
				if (item.containsKey("TotalVenta")) {
					venta.setTotalVenta(item.get("TotalVenta").s());
				}

				ventas.add(venta);
			}

			lastEvaluatedKey = response.lastEvaluatedKey();

		} while (lastEvaluatedKey != null && !lastEvaluatedKey.isEmpty());
		LOGGER.info("Se han recuperado " + count + " registros de Ventas en la BBDD");
		return ventas;
	}

}

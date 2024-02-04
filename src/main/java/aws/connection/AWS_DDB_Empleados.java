package aws.connection;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Esta clase proporciona métodos para interactuar con una tabla DynamoDB
 * llamada "Empleados". Los métodos incluyen la creación, eliminación, edición y
 * recuperación de registros de empleados.
 */
public class AWS_DDB_Empleados {

	private static final Logger LOGGER = LoggerFactory.getLogger(AWS_DDB_Empleados.class);

	/**
	 * Este método crea un nuevo registro de empleado en la tabla DynamoDB
	 * "Empleados".
	 *
	 * @param dynamoDbClient El cliente DynamoDB utilizado para realizar la
	 *                       operación.
	 * @param empleado       El objeto Empleado que se va a insertar en la tabla.
	 */
	public static void Create(DynamoDbClient dynamoDbClient, Empleado empleado) {
		try {
			// Crea un mapa de atributos para el empleado
			Map<String, AttributeValue> item = Map.of("EmpleadoID", empleado.getEmpleadoID(), "Apellido",
					empleado.getApellido(), "Cargo", empleado.getCargo(), "Direccion", empleado.getDireccion(),
					"FechaContrato", empleado.getFechaContrato(), "Nombre", empleado.getNombre());

			PutItemRequest putItemRequest = PutItemRequest.builder().tableName("Empleados").item(item).build();
			LOGGER.info("Map y ItemRequest realizado correctamente");
			// Inserta el empleado en la tabla DynamoDB
			dynamoDbClient.putItem(putItemRequest);
			LOGGER.info("Empleado insertado: " + empleado.getEmpleadoID().s());

		} catch (DynamoDbException e) {
			LOGGER.error("Error al insertar el empleado: " + e.getMessage());
		} finally {
			dynamoDbClient.close();
		}
	}

	/**
	 * Este método elimina un registro de empleado en la tabla DynamoDB "Empleados"
	 * por su clave de partición (EmpleadoID).
	 *
	 * @param dynamoDbClient El cliente DynamoDB utilizado para realizar la
	 *                       operación.
	 * @param empleadoID     El ID del empleado que se va a eliminar.
	 */
	public static void Delete(DynamoDbClient dynamoDbClient, String empleadoID) {
		HashMap<String, AttributeValue> keyToDelete = new HashMap<>();
		keyToDelete.put("EmpleadoID", AttributeValue.builder().s(empleadoID).build());
		LOGGER.info("HashMap y Clave generados correctemente");
		DeleteItemRequest deleteItemRequest = DeleteItemRequest.builder().tableName("Empleados").key(keyToDelete)
				.build();

		try {
			dynamoDbClient.deleteItem(deleteItemRequest);
			LOGGER.info("Empleado con clave de partición %s eliminado exitosamente.\n", empleadoID);
		} catch (DynamoDbException e) {
			LOGGER.error("Error al eliminar el empleado: " + e.getMessage());
		} finally {
			dynamoDbClient.close();
			LOGGER.trace("Cliente DB Cerrado correctamente");
		}
	}

	/**
	 * Este método edita un atributo específico de un registro de empleado en la
	 * tabla DynamoDB "Empleados".
	 *
	 * @param dynamoDbClient El cliente DynamoDB utilizado para realizar la
	 *                       operación.
	 * @param empleadoID     El ID del empleado cuyo atributo se va a editar.
	 * @param campo          El nombre del atributo que se va a editar.
	 * @param valor          El nuevo valor para el atributo.
	 */
	public static void Edit(DynamoDbClient dynamoDbClient, String empleadoID, String campo, String valor) {
		// Primero, obtenemos el objeto que deseamos editar
		HashMap<String, AttributeValue> keyToGet = new HashMap<>();
		keyToGet.put("EmpleadoID", AttributeValue.builder().s(empleadoID).build());
		LOGGER.trace("HashMap y Clave generados correctemente");
		GetItemRequest getItemRequest = GetItemRequest.builder().tableName("Empleados").key(keyToGet).build();

		Map<String, AttributeValue> item = dynamoDbClient.getItem(getItemRequest).item();

		if (item.isEmpty()) {
			LOGGER.error("No se encontró ningún objeto con la clave de partición: " + empleadoID);
			return;

		} else {
			LOGGER.info(item.toString());

			// Actualiza el atributo del objeto
			UpdateItemRequest updateItemRequest = UpdateItemRequest.builder().tableName("Empleados").key(keyToGet)
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
	 * Este método recupera todos los registros de empleados de la tabla DynamoDB
	 * "Empleados" y los devuelve en forma de una lista de objetos Empleado.
	 *
	 * @param dynamoDbClient El cliente DynamoDB utilizado para realizar la
	 *                       operación.
	 * @return Una lista de objetos Empleado que representan los registros de
	 *         empleados en la tabla.
	 */
	public static ArrayList<Empleado> getAllEmpleados(DynamoDbClient dynamoDbClient) {
		ArrayList<Empleado> empleados = new ArrayList<>();

		ScanResponse response;
		Map<String, AttributeValue> lastEvaluatedKey = null;
		int count = 0;
		do {
			ScanRequest scanRequest = ScanRequest.builder().tableName("Empleados").exclusiveStartKey(lastEvaluatedKey)
					.build();

			response = dynamoDbClient.scan(scanRequest);

			List<Map<String, AttributeValue>> items = response.items();
			for (Map<String, AttributeValue> item : items) {
				Empleado empleado = new Empleado();

				if (item.containsKey("EmpleadoID")) {
					empleado.setEmpleadoID(item.get("EmpleadoID").s());
				}

				if (item.containsKey("Apellido")) {
					empleado.setApellido(item.get("Apellido").s());
				}

				if (item.containsKey("Cargo")) {
					empleado.setCargo(item.get("Cargo").s());
				}

				if (item.containsKey("Direccion")) {
					empleado.setDireccion(item.get("Direccion").s());
				}

				if (item.containsKey("FechaContrato")) {
					empleado.setFechaContrato(item.get("FechaContrato").s());
				}

				if (item.containsKey("Nombre")) {
					empleado.setNombre(item.get("Nombre").s());
				}

				empleados.add(empleado);
				count++;
			}

			lastEvaluatedKey = response.lastEvaluatedKey();

		} while (lastEvaluatedKey != null && !lastEvaluatedKey.isEmpty());

		LOGGER.info("Se han recuperado " + count + " registros de Empleados en la BBDD");
		return empleados;
	}

	/**
	 * Incrementa el valor del ID de empleado en función del valor máximo actual
	 * encontrado en la tabla DynamoDB.
	 *
	 * @param dynamoDbClient El cliente DynamoDB utilizado para realizar la
	 *                       operación.
	 * @return El nuevo ID de empleado incrementado.
	 */
	public static String IncrementoEmpleadoID(DynamoDbClient dynamoDbClient) {

		ScanRequest scanRequest = ScanRequest.builder().tableName("Empleados").select(Select.ALL_ATTRIBUTES).build();

		ScanResponse scanResponse = dynamoDbClient.scan(scanRequest);

		String ultimoID = "";
		for (Map<String, AttributeValue> item : scanResponse.items()) {
			AttributeValue id = item.get("EmpleadoID");
			if (id != null && id.s() != null) {
				String idString = id.s();
				if (idString.compareTo(ultimoID) > 0) {
					ultimoID = idString;
				}
			}
		}

		if (ultimoID.matches("EMP_\\d{4}")) {

			String parteNumerica = ultimoID.substring(4);
			int numero = Integer.parseInt(parteNumerica);
			numero++;
			String nuevoNumero = String.format("%04d", numero);
			return "EMP_" + nuevoNumero;
		} else {
			return ultimoID;
		}
	}

	/**
	 * Verifica si existe un empleado con el EmpleadoID proporcionado en la tabla
	 * DynamoDB "Empleados".
	 *
	 * @param dynamoDbClient El cliente DynamoDB utilizado para realizar la
	 *                       operación.
	 * @param empleadoID     El ID del empleado que se va a verificar.
	 * @return true si el empleado existe, false en caso contrario.
	 */
	public static boolean existeEmpleado(DynamoDbClient dynamoDbClient, String empleadoID) {
		try {
			GetItemRequest getItemRequest = GetItemRequest.builder().tableName("Empleados")
					.key(Map.of("EmpleadoID", AttributeValue.builder().s(empleadoID).build()))
					.projectionExpression("EmpleadoID").build();

			GetItemResponse getItemResponse = dynamoDbClient.getItem(getItemRequest);

			// Si getItemResponse no es nulo y contiene el atributo "EmpleadoID", entonces
			// el empleado existe
			return getItemResponse.hasItem() && getItemResponse.item().containsKey("EmpleadoID");
		} catch (DynamoDbException e) {
			LOGGER.error("Error al buscar el empleado: " + e.getMessage());
			return false;
		}
	}
}

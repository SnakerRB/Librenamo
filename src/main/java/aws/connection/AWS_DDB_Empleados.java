package aws.connection;

import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.DeleteItemRequest;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.dynamodb.model.ReturnValue;
import software.amazon.awssdk.services.dynamodb.model.UpdateItemRequest;
import software.amazon.awssdk.services.dynamodb.model.UpdateItemResponse;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AWS_DDB_Empleados {

	private static final Logger LOGGER = LoggerFactory.getLogger(AWS_DDB_Empleados.class);

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
			LOGGER.info("Cliente DB Cerrado correctamente");
		}
	}

	public static void Edit(DynamoDbClient dynamoDbClient, String empleadoID, String campo, String valor) {
		// Primero, obtenemos el objeto que deseamos editar
		HashMap<String, AttributeValue> keyToGet = new HashMap<>();
		keyToGet.put("EmpleadoID", AttributeValue.builder().s(empleadoID).build());
		LOGGER.info("HashMap y Clave generados correctemente");
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
}

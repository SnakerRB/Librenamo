package aws.connection;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.DeleteItemRequest;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.dynamodb.model.ReturnValue;
import software.amazon.awssdk.services.dynamodb.model.UpdateItemRequest;
import software.amazon.awssdk.services.dynamodb.model.UpdateItemResponse;

public class AWS_DDB_Ventas {

	private static final Logger LOGGER = LoggerFactory.getLogger(AWS_DDB_Ventas.class);

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
}

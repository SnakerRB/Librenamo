package aws.connection;

import java.util.HashMap;
import java.util.Map;

import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

/**
 * Esta clase representa un objeto Venta con atributos asociados.
 */
public class Venta {

	// -------------------- ATRIBUTOS --------------------
	private AttributeValue VentaID;
	private AttributeValue FechaVenta;
	private AttributeValue Direccion;
	private AttributeValue EmpleadoID;
	private AttributeValue TotalVenta;
	private Map<String, AttributeValue> ListaLibrosVendidos;

	// -------------------- CONSTRUCTOR --------------------
	/**
	 * Constructor por defecto de la clase Venta.
	 */
	public Venta() {

	}

	/**
	 * Constructor de la clase Venta que establece los valores de los atributos
	 * VentaID y FechaVenta.
	 *
	 * @param ventaID    El valor del atributo VentaID.
	 * @param fechaVenta El valor del atributo FechaVenta.
	 */
	public Venta(AttributeValue ventaID, AttributeValue fechaVenta) {
		VentaID = ventaID;
		FechaVenta = fechaVenta;
	}

	// -------------------- GETTERS --------------------
	/**
	 * Obtiene el valor del atributo VentaID.
	 *
	 * @return El valor del atributo VentaID.
	 */
	public AttributeValue getVentaID() {
		return VentaID;
	}

	/**
	 * Obtiene el valor del atributo FechaVenta.
	 *
	 * @return El valor del atributo FechaVenta.
	 */
	public AttributeValue getFechaVenta() {
		return FechaVenta;
	}

	/**
	 * Obtiene el valor del atributo Direccion.
	 *
	 * @return El valor del atributo Direccion.
	 */
	public AttributeValue getDireccion() {
		return Direccion;
	}

	/**
	 * Obtiene el valor del atributo EmpleadoID.
	 *
	 * @return El valor del atributo EmpleadoID.
	 */
	public AttributeValue getEmpleadoID() {
		return EmpleadoID;
	}

	/**
	 * Obtiene el valor del atributo TotalVenta.
	 *
	 * @return El valor del atributo TotalVenta.
	 */
	public AttributeValue getTotalVenta() {
		return TotalVenta;
	}

	/**
	 * Obtiene el mapa de libros vendidos.
	 *
	 * @return El mapa de libros vendidos.
	 */
	public Map<String, AttributeValue> getListaLibrosVendidos() {
		return ListaLibrosVendidos;
	}

	// -------------------- SETTERS --------------------
	/**
	 * Establece el valor del atributo VentaID.
	 *
	 * @param ventaID El valor a establecer para el atributo VentaID.
	 */
	public void setVentaID(String ventaID) {
		VentaID = AttributeValue.builder().s(ventaID).build();
	}

	/**
	 * Establece el valor del atributo FechaVenta.
	 *
	 * @param fechaVenta El valor a establecer para el atributo FechaVenta.
	 */
	public void setFechaVenta(String fechaVenta) {
		FechaVenta = AttributeValue.builder().s(fechaVenta).build();
	}

	/**
	 * Establece el valor del atributo Direccion.
	 *
	 * @param direccion El valor a establecer para el atributo Direccion.
	 */
	public void setDireccion(String direccion) {
		Direccion = AttributeValue.builder().s(direccion).build();
	}

	/**
	 * Establece el valor del atributo EmpleadoID.
	 *
	 * @param empleadoID El valor a establecer para el atributo EmpleadoID.
	 */
	public void setEmpleadoID(String empleadoID) {
		EmpleadoID = AttributeValue.builder().s(empleadoID).build();
	}

	/**
	 * Establece el valor del atributo TotalVenta.
	 *
	 * @param totalVenta El valor a establecer para el atributo TotalVenta.
	 */
	public void setTotalVenta(String totalVenta) {
		TotalVenta = AttributeValue.builder().s(totalVenta).build();
	}

	/**
	 * Establece el valor de la lista de libros vendidos.
	 *
	 * @param cantidad La cantidad de libros vendidos.
	 * @param isbn     El ISBN del libro vendido.
	 */
	public void setListaLibrosVendidos(String cantidad, String isbn) {
		if (this.ListaLibrosVendidos == null) {
			ListaLibrosVendidos = new HashMap<>();
		}

		ListaLibrosVendidos.put("Cantidad", AttributeValue.builder().n(cantidad).build());
		ListaLibrosVendidos.put("ISBN", AttributeValue.builder().s(isbn).build());
	}

}

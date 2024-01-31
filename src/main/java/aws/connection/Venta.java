package aws.connection;

import java.util.HashMap;
import java.util.Map;

import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

public class Venta {

	// -------------------- ATRIBUTOS --------------------
	private AttributeValue VentaID;
	private AttributeValue FechaVenta;
	private AttributeValue Direccion;
	private AttributeValue EmpleadoID;
	private AttributeValue TotalVenta;
	private Map<String, AttributeValue> ListaLibrosVendidos;

	// -------------------- CONSTRUCTOR --------------------
	public Venta() {

	}

	public Venta(AttributeValue ventaID, AttributeValue fechaVenta) {
		VentaID = ventaID;
		FechaVenta = fechaVenta;
	}

	// -------------------- GETTERS --------------------
	public AttributeValue getVentaID() {
		return VentaID;
	}

	public AttributeValue getFechaVenta() {
		return FechaVenta;
	}

	public AttributeValue getDireccion() {
		return Direccion;
	}

	public AttributeValue getEmpleadoID() {
		return EmpleadoID;
	}

	public AttributeValue getTotalVenta() {
		return TotalVenta;
	}

	public Map<String, AttributeValue> getListaLibrosVendidos() {
		return ListaLibrosVendidos;
	}

	// -------------------- SETTERS --------------------
	public void setVentaID(String ventaID) {
		VentaID = AttributeValue.builder().s(ventaID).build();
	}

	public void setFechaVenta(String fechaVenta) {
		FechaVenta = AttributeValue.builder().s(fechaVenta).build();
	}

	public void setDireccion(String direccion) {
		Direccion = AttributeValue.builder().s(direccion).build();
	}

	public void setEmpleadoID(String empleadoID) {
		EmpleadoID = AttributeValue.builder().s(empleadoID).build();
	}

	public void setTotalVenta(String totalVenta) {
		TotalVenta = AttributeValue.builder().s(totalVenta).build();
	}

	public void setListaLibrosVendidos(String cantidad, String isbn) {
		if (this.ListaLibrosVendidos == null) {
			ListaLibrosVendidos = new HashMap<>();
		}

		ListaLibrosVendidos.put("Cantidad", AttributeValue.builder().n(cantidad).build());
		ListaLibrosVendidos.put("ISBN", AttributeValue.builder().s(isbn).build());
	}

}

package aws.connection;

import java.util.HashMap;
import java.util.Map;

import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

public class Venta {

	// -------------------- ATRIBUTOS --------------------
	private AttributeValue VentaID;
	private AttributeValue FechaVenta;
	private AttributeValue Direccion;
	private AttributeValue EmpeladoID;
	private AttributeValue TotalVenta;
	private Map<AttributeValue, AttributeValue> ListaLibrosVendidos;

	// -------------------- CONSTRUCTOR --------------------
	public Venta() {

	}

	public Venta(AttributeValue ventaID) {
		VentaID = ventaID;
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

	public AttributeValue getEmpeladoID() {
		return EmpeladoID;
	}

	public AttributeValue getTotalVenta() {
		return TotalVenta;
	}

	public Map<AttributeValue, AttributeValue> getListaLibrosVendidos() {
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

	public void setEmpeladoID(String empeladoID) {
		EmpeladoID = AttributeValue.builder().s(empeladoID).build();
	}

	public void setTotalVenta(String totalVenta) {
		TotalVenta = AttributeValue.builder().s(totalVenta).build();
	}

	public void setListaLibrosVendidos(String cantidad, String isbn) {
		Map<AttributeValue, AttributeValue> lista = new HashMap<>();
		lista.put(AttributeValue.builder().n(cantidad).build(), AttributeValue.builder().s(isbn).build());

		ListaLibrosVendidos = lista;
	}

}

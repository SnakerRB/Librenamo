package aws.connection;

import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

public class Empleado {

	// -------------------- ATRIBUTOS --------------------
	private AttributeValue EmpleadoID;
	private AttributeValue Apellido;
	private AttributeValue Cargo;
	private AttributeValue Direccion;
	private AttributeValue FechaContrato;
	private AttributeValue Nombre;

	// -------------------- CONSTRUCTOR --------------------
	public Empleado() {

	}

	public Empleado(AttributeValue empleadoID) {
		EmpleadoID = empleadoID;
	}

	// -------------------- GETTERS --------------------
	public AttributeValue getEmpleadoID() {
		return EmpleadoID;
	}

	public AttributeValue getApellido() {
		return Apellido;
	}

	public AttributeValue getCargo() {
		return Cargo;
	}

	public AttributeValue getDireccion() {
		return Direccion;
	}

	public AttributeValue getFechaContrato() {
		return FechaContrato;
	}

	public AttributeValue getNombre() {
		return Nombre;
	}

	// -------------------- SETTERS --------------------
	public void setEmpleadoID(String empleadoID) {
		EmpleadoID = AttributeValue.builder().s(empleadoID).build();
	}

	public void setApellido(String apellido) {
		Apellido = AttributeValue.builder().s(apellido).build();
	}

	public void setCargo(String cargo) {
		Cargo = AttributeValue.builder().s(cargo).build();
	}

	public void setDireccion(String direccion) {
		Direccion = AttributeValue.builder().s(direccion).build();
	}

	public void setFechaContrato(String fechaContrato) {
		FechaContrato = AttributeValue.builder().s(fechaContrato).build();
	}

	public void setNombre(String nombre) {
		Nombre = AttributeValue.builder().s(nombre).build();
	}

	// -------------------- TO-STRING --------------------
	@Override
	public String toString() {
		return "Empleado [EmpleadoID=" + EmpleadoID + ", Apellido=" + Apellido + ", Cargo=" + Cargo + ", Direccion="
				+ Direccion + ", FechaContrato=" + FechaContrato + ", Nombre=" + Nombre + "]";
	}

}

package aws.connection;

import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

/**
 * Esta clase representa un objeto Empleado con atributos asociados.
 */
public class Empleado {

	// -------------------- ATRIBUTOS --------------------
	private AttributeValue EmpleadoID;
	private AttributeValue Apellido;
	private AttributeValue Cargo;
	private AttributeValue Direccion;
	private AttributeValue FechaContrato;
	private AttributeValue Nombre;

	// -------------------- CONSTRUCTOR --------------------
	/**
	 * Constructor por defecto de la clase Empleado.
	 */
	public Empleado() {

	}

	/**
	 * Constructor de la clase Empleado que establece el valor del atributo
	 * EmpleadoID.
	 *
	 * @param empleadoID El valor del atributo EmpleadoID.
	 */
	public Empleado(AttributeValue empleadoID) {
		EmpleadoID = empleadoID;
	}

	// -------------------- GETTERS --------------------
	/**
	 * Obtiene el valor del atributo EmpleadoID.
	 *
	 * @return El valor del atributo EmpleadoID.
	 */
	public AttributeValue getEmpleadoID() {
		return EmpleadoID;
	}

	/**
	 * Obtiene el valor del atributo Apellido.
	 *
	 * @return El valor del atributo Apellido.
	 */
	public AttributeValue getApellido() {
		return Apellido;
	}

	/**
	 * Obtiene el valor del atributo Cargo.
	 *
	 * @return El valor del atributo Cargo.
	 */
	public AttributeValue getCargo() {
		return Cargo;
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
	 * Obtiene el valor del atributo FechaContrato.
	 *
	 * @return El valor del atributo FechaContrato.
	 */
	public AttributeValue getFechaContrato() {
		return FechaContrato;
	}

	/**
	 * Obtiene el valor del atributo Nombre.
	 *
	 * @return El valor del atributo Nombre.
	 */
	public AttributeValue getNombre() {
		return Nombre;
	}

	// -------------------- SETTERS --------------------
	/**
	 * Establece el valor del atributo EmpleadoID.
	 *
	 * @param empleadoID El valor a establecer para el atributo EmpleadoID.
	 */
	public void setEmpleadoID(String empleadoID) {
		EmpleadoID = AttributeValue.builder().s(empleadoID).build();
	}

	/**
	 * Establece el valor del atributo Apellido.
	 *
	 * @param apellido El valor a establecer para el atributo Apellido.
	 */
	public void setApellido(String apellido) {
		Apellido = AttributeValue.builder().s(apellido).build();
	}

	/**
	 * Establece el valor del atributo Cargo.
	 *
	 * @param cargo El valor a establecer para el atributo Cargo.
	 */
	public void setCargo(String cargo) {
		Cargo = AttributeValue.builder().s(cargo).build();
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
	 * Establece el valor del atributo FechaContrato.
	 *
	 * @param fechaContrato El valor a establecer para el atributo FechaContrato.
	 */
	public void setFechaContrato(String fechaContrato) {
		FechaContrato = AttributeValue.builder().s(fechaContrato).build();
	}

	/**
	 * Establece el valor del atributo Nombre.
	 *
	 * @param nombre El valor a establecer para el atributo Nombre.
	 */
	public void setNombre(String nombre) {
		Nombre = AttributeValue.builder().s(nombre).build();
	}

	// -------------------- TO-STRING --------------------
	/**
	 * Devuelve una representación en forma de cadena de caracteres de la clase
	 * Empleado.
	 *
	 * @return Una cadena que representa el objeto Empleado y sus atributos.
	 */
	@Override
	public String toString() {
		return "Empleado [EmpleadoID=" + EmpleadoID + ", Apellido=" + Apellido + ", Cargo=" + Cargo + ", Direccion="
				+ Direccion + ", FechaContrato=" + FechaContrato + ", Nombre=" + Nombre + "]";
	}

}

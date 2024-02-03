package aws.connection;

import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

/**
 * Esta clase representa un objeto Libro con atributos asociados.
 */
public class Libro {

	// -------------------- ATRIBUTOS --------------------
	private AttributeValue ISBN;//
	private AttributeValue Anio;//
	private AttributeValue Autor;//
	private AttributeValue Editorial;//
	private AttributeValue Existencias;
	private AttributeValue Genero;//
	private AttributeValue Precio;
	private AttributeValue Titulo;//

	// -------------------- CONSTRUCTOR --------------------
	/**
	 * Constructor por defecto de la clase Libro.
	 */
	public Libro() {

	}

	/**
	 * Constructor de la clase Libro que establece el valor del atributo ISBN.
	 *
	 * @param iSBN El valor del atributo ISBN.
	 */
	public Libro(String iSBN) {
		ISBN = AttributeValue.builder().s(iSBN).build();
	}

	// -------------------- GETTERS --------------------
	/**
	 * Obtiene el valor del atributo ISBN.
	 *
	 * @return El valor del atributo ISBN.
	 */
	public AttributeValue getISBN() {
		return ISBN;
	}

	/**
	 * Obtiene el valor del atributo Anio.
	 *
	 * @return El valor del atributo Anio.
	 */
	public AttributeValue getAnio() {
		return Anio;
	}

	/**
	 * Obtiene el valor del atributo Autor.
	 *
	 * @return El valor del atributo Autor.
	 */
	public AttributeValue getAutor() {
		return Autor;
	}

	/**
	 * Obtiene el valor del atributo Editorial.
	 *
	 * @return El valor del atributo Editorial.
	 */
	public AttributeValue getEditorial() {
		return Editorial;
	}

	/**
	 * Obtiene el valor del atributo Existencias.
	 *
	 * @return El valor del atributo Existencias.
	 */
	public AttributeValue getExistencias() {
		return Existencias;
	}

	/**
	 * Obtiene el valor del atributo Genero.
	 *
	 * @return El valor del atributo Genero.
	 */
	public AttributeValue getGenero() {
		return Genero;
	}

	/**
	 * Obtiene el valor del atributo Precio.
	 *
	 * @return El valor del atributo Precio.
	 */
	public AttributeValue getPrecio() {
		return Precio;
	}

	/**
	 * Obtiene el valor del atributo Titulo.
	 *
	 * @return El valor del atributo Titulo.
	 */
	public AttributeValue getTitulo() {
		return Titulo;
	}

	// -------------------- SETTERS --------------------
	/**
	 * Establece el valor del atributo ISBN.
	 *
	 * @param ISBN El valor a establecer para el atributo ISBN.
	 */
	public void setISBN(String ISBN) {
		this.ISBN = AttributeValue.builder().s(ISBN).build();
	}

	/**
	 * Establece el valor del atributo Anio.
	 *
	 * @param anio El valor a establecer para el atributo Anio.
	 */
	public void setAnio(String anio) {
		Anio = AttributeValue.builder().n(anio).build();
	}

	/**
	 * Establece el valor del atributo Autor.
	 *
	 * @param autor El valor a establecer para el atributo Autor.
	 */
	public void setAutor(String autor) {
		Autor = AttributeValue.builder().s(autor).build();
	}

	/**
	 * Establece el valor del atributo Editorial.
	 *
	 * @param editorial El valor a establecer para el atributo Editorial.
	 */
	public void setEditorial(String editorial) {
		Editorial = AttributeValue.builder().s(editorial).build();
	}

	/**
	 * Establece el valor del atributo Existencias.
	 *
	 * @param existencias El valor a establecer para el atributo Existencias.
	 */
	public void setExistencias(String existencias) {
		Existencias = AttributeValue.builder().n(existencias).build();
	}

	/**
	 * Establece el valor del atributo Genero.
	 *
	 * @param genero El valor a establecer para el atributo Genero.
	 */
	public void setGenero(String genero) {
		Genero = AttributeValue.builder().s(genero).build();
	}

	/**
	 * Establece el valor del atributo Precio.
	 *
	 * @param precio El valor a establecer para el atributo Precio.
	 */
	public void setPrecio(String precio) {
		Precio = AttributeValue.builder().n(precio).build();
	}

	/**
	 * Establece el valor del atributo Titulo.
	 *
	 * @param titulo El valor a establecer para el atributo Titulo.
	 */
	public void setTitulo(String titulo) {
		Titulo = AttributeValue.builder().s(titulo).build();
	}

	// -------------------- TO-STRING --------------------
	/**
	 * Devuelve una representación en forma de cadena de caracteres de la clase
	 * Libro.
	 *
	 * @return Una cadena que representa el objeto Libro y sus atributos.
	 */
	@Override
	public String toString() {
		return "Libro [ISBN=" + ISBN + ", Anio=" + Anio + ", Autor=" + Autor + ", Editorial=" + Editorial
				+ ", Existencias=" + Existencias + ", Genero=" + Genero + ", Precio=" + Precio + ", Titulo=" + Titulo
				+ "]";
	}

}

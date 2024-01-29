package aws.connection;

import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

public class Libro {

	// -------------------- ATRIBUTOS --------------------
	private AttributeValue ISBN;
	private AttributeValue Anio;
	private AttributeValue Autor;
	private AttributeValue Editorial;
	private AttributeValue Existencias;
	private AttributeValue Genero;
	private AttributeValue Precio;
	private AttributeValue Titulo;

	// -------------------- CONSTRUCTOR --------------------
	public Libro() {

	}

	public Libro(String iSBN) {
		ISBN = AttributeValue.builder().s(iSBN).build();
	}

	// -------------------- GETTERS --------------------
	public AttributeValue getISBN() {
		return ISBN;
	}

	public AttributeValue getAnio() {
		return Anio;
	}

	public AttributeValue getAutor() {
		return Autor;
	}

	public AttributeValue getEditorial() {
		return Editorial;
	}

	public AttributeValue getExistencias() {
		return Existencias;
	}

	public AttributeValue getGenero() {
		return Genero;
	}

	public AttributeValue getPrecio() {
		return Precio;
	}

	public AttributeValue getTitulo() {
		return Titulo;
	}

	// -------------------- SETTERS --------------------
	public void setISBN(String ISBN) {
		this.ISBN = AttributeValue.builder().s(ISBN).build();
	}

	public void setAnio(String anio) {
		Anio = AttributeValue.builder().n(anio).build();
	}

	public void setAutor(String autor) {
		Autor = AttributeValue.builder().s(autor).build();
	}

	public void setEditorial(String editorial) {
		Editorial = AttributeValue.builder().s(editorial).build();
	}

	public void setExistencias(String existencias) {
		Existencias = AttributeValue.builder().n(existencias).build();
	}

	public void setGenero(String genero) {
		Genero = AttributeValue.builder().s(genero).build();
	}

	public void setPrecio(String precio) {
		Precio = AttributeValue.builder().n(precio).build();
	}

	public void setTitulo(String titulo) {
		Titulo = AttributeValue.builder().s(titulo).build();
	}

	// -------------------- TO-STRING --------------------
	@Override
	public String toString() {
		return "Libro [ISBN=" + ISBN + ", Anio=" + Anio + ", Autor=" + Autor + ", Editorial=" + Editorial
				+ ", Existencias=" + Existencias + ", Genero=" + Genero + ", Precio=" + Precio + ", Titulo=" + Titulo
				+ "]";
	}

}

package aws.connection;

import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

public class Libro {

	// -------------------- ATRIBUTOS --------------------
	private AttributeValue ISBN;
	private AttributeValue Titulo;
	private AttributeValue Autor;
	private AttributeValue AnoPublicacion;
	private AttributeValue Editorial;
	private AttributeValue Genero;

	// -------------------- CONSTRUCTOR --------------------
	public Libro() {

	}

	public Libro(String iSBN, String titulo) {
		ISBN = AttributeValue.builder().s(iSBN).build();
		Titulo = AttributeValue.builder().s(titulo).build();
	}

	// -------------------- GETTERS --------------------
	public AttributeValue getISBN() {
		return ISBN;
	}

	public AttributeValue getTitulo() {
		return Titulo;
	}

	public AttributeValue getAutor() {
		return Autor;
	}

	public AttributeValue getAnoPublicacion() {
		return AnoPublicacion;
	}

	public AttributeValue getEditorial() {
		return Editorial;
	}

	public AttributeValue getGenero() {
		return Genero;
	}

	// -------------------- SETTERS --------------------
	public void setISBN(String ISBN) {
		this.ISBN = AttributeValue.builder().s(ISBN).build();
	}

	public void setTitulo(String titulo) {
		Titulo = AttributeValue.builder().s(titulo).build();
	}

	public void setAutor(String autor) {
		Autor = AttributeValue.builder().s(autor).build();
	}

	public void setAnoPublicacion(String anoPublicacion) {
		AnoPublicacion = AttributeValue.builder().s(anoPublicacion).build();
	}

	public void setEditorial(String editorial) {
		Editorial = AttributeValue.builder().s(editorial).build();
	}

	public void setGenero(String genero) {
		Genero = AttributeValue.builder().s(genero).build();
	}

	// -------------------- TO-STRING --------------------
	@Override
	public String toString() {
		return "Libro [ISBN=" + ISBN + ", Titulo=" + Titulo + ", Autor=" + Autor + ", AnoPublicacion=" + AnoPublicacion
				+ ", Editorial=" + Editorial + ", Genero=" + Genero + "]";
	}

}

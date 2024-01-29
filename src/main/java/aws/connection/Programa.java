package aws.connection;

public class Programa {
	public static void main(String[] args) {

		// -------------------- IMPORTAR OBJETOS DESDE JSON --------------------
		//Ruta al archivo JSON en la carpeta resources
		String jsonFileLibros = "src/main/resources/libros.json";
		String jsonFileEmpleados = "";
		String jsonFileventas = "";
		
		AWS_DDB_Libros.ImportFromJson(AWS_DDB_Login.Logg(), jsonFileLibros);
		AWS_DDB_Empleados.ImportFromJson();
		AWS_DDB_Ventas.ImportFromJson();

		// -------------------- SELECT DE TODA LA TABLA -------------------- OK
		// Define los campos que deseas proyectar y mostrar
		// String[] campos = {"ISBN", "Titulo", "AnoPublicacion", "Autor", "Editorial", "Genero"};
		
		// AWS_DDB_Select.SelectAll(AWS_DDB_Login.Logg(), tableName, campos);

		// -------------------- INSERTAR 1 OBJETO -------------------- OK
		// Crea un objeto Libro con los detalles del libro que deseas insertar
		// Libro libro = new Libro();
		// libro.setISBN("ISBN-00100");
		// libro.setTitulo("Libro 100");
		// libro.setAutor("Autor B");
		// libro.setAnoPublicacion("1992");
		// libro.setEditorial("Editorial 4");
		// libro.setGenero("Ficción");

		// Llama al método para insertar el libro en la tabla
		// AWS_DDB_Libro.Create(AWS_DDB_Login.Logg(), tableName, libro);

		// -------------------- BUSCAR 1 OBJETO -------------------- OK
		// String isbnToSearch = "ISBN-00000";
		// String titleToSearch = "Libro 0";
		
		// AWS_DDB_Select.SelectLibro(AWS_DDB_Login.Logg(), tableName, isbnToSearch, titleToSearch);
		
		// -------------------- ELIMINAR 1 OBJETO -------------------- OK
		// String isbnToDelete = "ISBN-00000";
		// String titleToDelete = "Libro 0";
		
		// AWS_DDB_Libro.Delete(AWS_DDB_Login.Logg(), tableName, isbnToDelete, titleToDelete);
		
		// -------------------- EDITAR 1 OBJETO -------------------- Falta return con los nuevos datos
		// String isbnToUpdate = "ISBN-00001";
		// String titleToUpdate = "Libro 1";
		// String atribToUpdate = "Autor";
		// String valueToUpdate = "Autor POPO";
		
		// AWS_DDB_Libro.Edit(AWS_DDB_Login.Logg(), tableName, isbnToUpdate, titleToUpdate, atribToUpdate, valueToUpdate);
		

	}

}

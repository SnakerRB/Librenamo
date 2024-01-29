package aws.connection;

public class Programa {
	public static void main(String[] args) {

		// -------------------- IMPORTAR OBJETOS DESDE JSON --------------------
		//Ruta al archivo JSON en la carpeta resources
		String jsonFileLibros = "src/main/resources/libros.json";
		String jsonFileEmpleados = "";
		String jsonFileventas = "";
		
		//AWS_DDB_Libros.ImportFromJson(AWS_DDB_Login.Logg(), jsonFileLibros);
		//AWS_DDB_Empleados.ImportFromJson();
		//AWS_DDB_Ventas.ImportFromJson();

		// -------------------- SELECT DE TODA LA TABLA -------------------- OK
		// Define los campos que deseas proyectar y mostrar
		// String[] campos = {"ISBN", "Titulo", "AnoPublicacion", "Autor", "Editorial", "Genero"};
		
		// AWS_DDB_Select.SelectAll(AWS_DDB_Login.Logg(), tableName, campos);


		
		// -------------------- INSERTAR 1 LIBRO -------------------- OK
		// Crea un objeto Libro con los detalles del libro que deseas insertar
		Libro libro = new Libro();
		libro.setISBN("ISBN-00100");
		libro.setTitulo("Libro 100");
		libro.setAutor("Autor B");
		libro.setAnio("1992");
		libro.setEditorial("Editorial 4");
		libro.setGenero("Ficción");
		libro.setPrecio("25.50");
		libro.setExistencias("20");

		// Llama al método para insertar el libro en la tabla
		//AWS_DDB_Libros.Create(AWS_DDB_Login.Logg(), libro);
		
		
		//______________________________________TEST_LIBROS_________________________________ OK

		// -------------------- ELIMINAR 1 LIBRO ------------------ OK
		//String isbnToDelete = "ISBN-00100";
		
		//AWS_DDB_Libros.Delete(AWS_DDB_Login.Logg(),isbnToDelete);
		
		// -------------------- EDITAR 1 LIBRO -------------------- Falta return con los nuevos datos
		
		/*		
		String isbnToUpdate = "ISBN-00100";
		String atribToUpdate = "Autor";
		String valueToUpdate = "Autor POPO";
		
		AWS_DDB_Libros.Edit(AWS_DDB_Login.Logg(), isbnToUpdate, atribToUpdate, valueToUpdate);
		*/
	
		
		
		
		
		//______________________________________TEST_EMPLEADOS_____________________________ OK
		
		// -------------------- INSERTAR 1 EMPLEADO -------------------- OK
		// Crea un objeto empleado con los detalles del empleado que deseas insertar
		
		
		/*
		Empleado emp = new Empleado();
		emp.setNombre("Pedro");
		emp.setEmpleadoID("EMP_0002");
		emp.setApellido("Cuadrado");
		emp.setCargo("DC");
		emp.setFechaContrato("11/10/2022");
		emp.setDireccion("C/Pilar Lorengar");
		*/
		
		// -------------------- CREAR 1 EMPLEADO -------------------- OK
		//AWS_DDB_Empleados.Create(AWS_DDB_Login.Logg(),emp);

		// -------------------- EDITAR 1 EMPLEADO -------------------- OK
		//String ValueToChange="Nombre"
		//String NewValue="Andres"
		//AWS_DDB_Empleados.Edit(AWS_DDB_Login.Logg(), "EMP_0002",ValueToChange,NewValue);
	
		
		// -------------------- ELIMINAR 1 EMPLEADO -------------------- OK
		//String IdEmpleadoDelete="EMP_0002";
		//AWS_DDB_Empleados.Delete(AWS_DDB_Login.Logg(),IdEMpleadoDelete);
		
			
		
		
		// -------------------- BUSCAR 1 OBJETO -------------------- OK
		// String isbnToSearch = "ISBN-00000";
		// String titleToSearch = "Libro 0";
		
		// AWS_DDB_Select.SelectLibro(AWS_DDB_Login.Logg(), tableName, isbnToSearch, titleToSearch);
		
	}

}

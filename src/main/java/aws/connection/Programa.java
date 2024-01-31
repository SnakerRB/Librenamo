package aws.connection;

public class Programa {
	public static void main(String[] args) {
		/*
		 * PARA CONECTARNOS AL LA BBDD DE DYNAMO DB 
		 * 
		 * -INSERTAR ACCES KEY GENERADA EN LA SESIÓN
		 * -INSERTAR SECRET KEY GENERADA EN LA SESIÓN
		 * -INSERTAR SESSION TOKEN GENERADA EN LA SESIÓN
		 * 
		
		System.out.println("--INICIO DE SESION EN DynamoDB NECESARIO--");
		String accessKey="ASIAY7LABMP7F3I6IXFY";
		String secretKey="sYO5eRTxnSzg+I1midVAJMw0D8WWfPx1My1Z2hip";
		String sessionToken="FwoGZXIvYXdzEEsaDL3HhE17PHIDlL0NHiLCAcV8j8UW5nzHg+gXOBRYzEo36PuMmfKykZ0rLi7yGdW3bqTznDX6wDjWgTRmdMGJAGGxOk5fkADr8gzO5OlGEa3kgaHNRQA9eIJqe2TZ+1YcBZhKv0MuxAAfyXRVEmuqTaNJyGHYvIY+zov00JAPyQtQzynIXsk6jQ8qcDe0X4TN/sDFLTcZgnVyxNuGmYzJVdZxQFIxms+CEgzrDjqeQ0IaPkfg9b5Gxbc+lmjChl7pEZkuu2QHeKDz8C6WZ4a55SGtKNa8360GMi2seGnw7oiHJkLYIIGikORJ0ConOjTDuUSGK2NJ/LtCXVAsKabVPr93naBfafU= ";
		 */	

		// -------------------- IMPORTAR OBJETOS DESDE JSON --------------------
		//Ruta al archivo JSON en la carpeta resources
		//String jsonFileLibros = "src/main/resources/libros.json";
		//String jsonFileEmpleados = "";
		//String jsonFileventas = "";
		
		//AWS_DDB_Libros.ImportFromJson(AWS_DDB_Login.Logg(), jsonFileLibros);
		//AWS_DDB_Empleados.ImportFromJson();
		//AWS_DDB_Ventas.ImportFromJson();

		// -------------------- SELECT DE TODA LA TABLA -------------------- OK
		// Define los campos que deseas proyectar y mostrar
		// String[] campos = {"ISBN", "Titulo", "AnoPublicacion", "Autor", "Editorial", "Genero"};
		
		// AWS_DDB_Select.SelectAll(AWS_DDB_Login.Logg(), tableName, campos);


		//______________________________________TEST_LIBROS_________________________________ OK
		
		// -------------------- INSERTAR 1 LIBRO -------------------- OK
		// Crea un objeto Libro con los detalles del libro que deseas insertar
//		Libro libro = new Libro();
//		libro.setISBN("ISBN-00100");
//		libro.setTitulo("Libro 100");
//		libro.setAutor("Autor B");
//		libro.setAnio("1992");
//		libro.setEditorial("Editorial 4");
//		libro.setGenero("Ficción");
//		libro.setPrecio("25.50");
//		libro.setExistencias("20");

		// Llama al método para insertar el libro en la tabla
		//AWS_DDB_Libros.Create(AWS_DDB_Login.Logg(), libro);

		// -------------------- ELIMINAR 1 LIBRO ------------------ OK
		//String isbnToDelete = "ISBN-00100";
		
		//AWS_DDB_Libros.Delete(AWS_DDB_Login.Logg(),isbnToDelete);
		
		// -------------------- EDITAR 1 LIBRO -------------------- Falta return con los nuevos datos
		
				
//		String isbnToUpdate = "ISBN-00100";
//		String atribToUpdate = "Titulo";
//		String valueToUpdate = "TONTO";
		
		//AWS_DDB_Libros.Edit(AWS_DDB_Login.LoggMain(accessKey, secretKey, sessionToken), isbnToUpdate, atribToUpdate, valueToUpdate);
		//AWS_DDB_Libros.Edit(AWS_DDB_Login.Logg(), isbnToUpdate, atribToUpdate, valueToUpdate);
	
		
		
		
		
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
		
			
		
		
		//_____________________________ VENTAS ______________________________ 
		/*
		Venta vent = new Venta();
		vent.setVentaID("VEN_0001");
		vent.setFechaVenta("11/09/2011");
		vent.setDireccion("Calle de tus muertos");
		vent.setEmpleadoID("EMP_0001");
		vent.setListaLibrosVendidos("3", "ISBN-00000");
		vent.setTotalVenta("150");
		AWS_DDB_Ventas.Create(AWS_DDB_Login.Logg(), vent);
	
		AWS_DDB_Ventas.Edit(AWS_DDB_Login.Logg(),"VEN_0001","Direccion","AHORA SON MIS MUERTOS","11/09/2011");
		*/
		
		
		//_____________________________ LISTAR TODOS LOS ITEMS DE UNA TABLA ______________________________ 
		/*
		String tabla = "Empleados";
		
		AWS_DDB_Select.SelectAll(AWS_DDB_Login.Logg(), tabla);
		*/
		
		//_____________________________ LISTAR UN ITEM ESPECIFICOO ______________________________ 
		String tabla = "Ventas";
		
		AWS_DDB_Select.buscarItem(AWS_DDB_Login.Logg(), tabla, "VEN_0001", "11/09/2011");
		
	}

}

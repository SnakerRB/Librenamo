package aws.connection;

import java.util.Scanner;

public class Programa {
	public static void main(String[] args) {
		  Scanner scanner = new Scanner(System.in);
	        int opcion;

	        do {
	            System.out.println("Menú Principal:");
	            System.out.println("1. Importar datos");
	            System.out.println("2. Exportar datos");
	            System.out.println("3. Insertar registro");
	            System.out.println("4. Eliminar registro");
	            System.out.println("5. Editar registro");
	            System.out.println("6. Consultar registro");
	            System.out.println("7. Consultar tablas");
	            System.out.println("8. Salir");
	            System.out.print("Seleccione una opción: ");

	            opcion = scanner.nextInt();

	            if (opcion == 1) {
	                int subopcionImportar;
	                do {
	                    System.out.println("Submenú de Importar Datos:");
	                    System.out.println("1. Importar datos de libros desde JSON");
	                    System.out.println("2. Importar datos de ventas desde JSON");
	                    System.out.println("3. Importar datos de empleados desde JSON");
	                    System.out.println("4. Volver al menú principal");
	                    System.out.print("Seleccione una opción: ");

	                    subopcionImportar = scanner.nextInt();

	                    if (subopcionImportar == 1) {
	                        // Implementa la lógica para importar datos de libros desde JSON
	                    } else if (subopcionImportar == 2) {
	                        // Implementa la lógica para importar datos de ventas desde JSON
	                    } else if (subopcionImportar == 3) {
	                        // Implementa la lógica para importar datos de empleados desde JSON
	                    } else if (subopcionImportar == 4) {
	                        System.out.println("Volviendo al menú principal...");
	                    } else {
	                        System.out.println("Opción no válida");
	                    }
	                } while (subopcionImportar != 4);
	            } else if (opcion == 2) {
	                // Implementa la lógica para exportar datos a JSON
	            } else if (opcion == 3) {
	                int subopcionInsertar;
	                do {
	                    System.out.println("Submenú de Insertar Registro:");
	                    System.out.println("1. Insertar libro");
	                    System.out.println("2. Insertar venta");
	                    System.out.println("3. Insertar empleado");
	                    System.out.println("4. Volver al menú principal");
	                    System.out.print("Seleccione una opción: ");

	                    subopcionInsertar = scanner.nextInt();

	                    if (subopcionInsertar == 1) {
	                        // Implementa la lógica para insertar un libro
	                    } else if (subopcionInsertar == 2) {
	                        // Implementa la lógica para insertar una venta
	                    } else if (subopcionInsertar == 3) {
	                        // Implementa la lógica para insertar un empleado
	                    } else if (subopcionInsertar == 4) {
	                        System.out.println("Volviendo al menú principal...");
	                    } else {
	                        System.out.println("Opción no válida");
	                    }
	                } while (subopcionInsertar != 4);
	            } else if (opcion == 4) {
	                int subopcionEliminar;
	                do {
	                    System.out.println("Submenú de Eliminar Registro:");
	                    System.out.println("1. Eliminar libro");
	                    System.out.println("2. Eliminar venta");
	                    System.out.println("3. Eliminar empleado");
	                    System.out.println("4. Volver al menú principal");
	                    System.out.print("Seleccione una opción: ");

	                    subopcionEliminar = scanner.nextInt();

	                    if (subopcionEliminar == 1) {
	                        // Implementa la lógica para eliminar un libro
	                    } else if (subopcionEliminar == 2) {
	                        // Implementa la lógica para eliminar una venta
	                    } else if (subopcionEliminar == 3) {
	                        // Implementa la lógica para eliminar un empleado
	                    } else if (subopcionEliminar == 4) {
	                        System.out.println("Volviendo al menú principal...");
	                    } else {
	                        System.out.println("Opción no válida");
	                    }
	                } while (subopcionEliminar != 4);
	            } else if (opcion == 5) {
	                int subopcionEditar;
	                do {
	                    System.out.println("Submenú de Editar Registro:");
	                    System.out.println("1. Editar libro");
	                    System.out.println("2. Editar venta");
	                    System.out.println("3. Editar empleado");
	                    System.out.println("4. Volver al menú principal");
	                    System.out.print("Seleccione una opción: ");

	                    subopcionEditar = scanner.nextInt();

	                    if (subopcionEditar == 1) {
	                        // Implementa la lógica para editar un libro
	                    } else if (subopcionEditar == 2) {
	                        // Implementa la lógica para editar una venta
	                    } else if (subopcionEditar == 3) {
	                        // Implementa la lógica para editar un empleado
	                    } else if (subopcionEditar == 4) {
	                        System.out.println("Volviendo al menú principal...");
	                    } else {
	                        System.out.println("Opción no válida");
	                    }
	                } while (subopcionEditar != 4);
	            } else if (opcion == 6) {
	                int subopcionConsultarRegistro;
	                do {
	                    System.out.println("Submenú de Consultar Registro:");
	                    System.out.println("1. Consultar libro");
	                    System.out.println("2. Consultar venta");
	                    System.out.println("3. Consultar empleado");
	                    System.out.println("4. Volver al menú principal");
	                    System.out.print("Seleccione una opción: ");

	                    subopcionConsultarRegistro = scanner.nextInt();

	                    if (subopcionConsultarRegistro == 1) {
	                        // Implementa la lógica para consultar un libro
	                    } else if (subopcionConsultarRegistro == 2) {
	                        // Implementa la lógica para consultar una venta
	                    } else if (subopcionConsultarRegistro == 3) {
	                        // Implementa la lógica para consultar un empleado
	                    } else if (subopcionConsultarRegistro == 4) {
	                        System.out.println("Volviendo al menú principal...");
	                    } else {
	                        System.out.println("Opción no válida");
	                    }
	                } while (subopcionConsultarRegistro != 4);
	            } else if (opcion == 7) {
	                int subopcionConsultarTablas;
	                do {
	                    System.out.println("Submenú de Consultar Tablas:");
	                    System.out.println("1. Consultar tabla de libros");
	                    System.out.println("2. Consultar tabla de ventas");
	                    System.out.println("3. Consultar tabla de empleados");
	                    System.out.println("4. Volver al menú principal");
	                    System.out.print("Seleccione una opción: ");

	                    subopcionConsultarTablas = scanner.nextInt();

	                    if (subopcionConsultarTablas == 1) {
	                        // Implementa la lógica para consultar la tabla de libros
	                    } else if (subopcionConsultarTablas == 2) {
	                        // Implementa la lógica para consultar la tabla de ventas
	                    } else if (subopcionConsultarTablas == 3) {
	                        // Implementa la lógica para consultar la tabla de empleados
	                    } else if (subopcionConsultarTablas == 4) {
	                        System.out.println("Volviendo al menú principal...");
	                    } else {
	                        System.out.println("Opción no válida");
	                    }
	                } while (subopcionConsultarTablas != 4);
	            } else if (opcion == 8) {
	                System.out.println("¡Hasta luego!");
	            } else {
	                System.out.println("Opción no válida");
	            }
	        } while (opcion != 8);

	        scanner.close();
	    
		
		
		
	
		
		
		
		
		
		
		
		
		
		
//==========================================================================================================================================================================================	
		//________________________IMPORTAR DATOS DE JSON_________________________ OK (FALTA VENTAS)
		
		
		String jsonFileLibros = "src/main/resources/libros.json";
		String jsonFileEmpleados = "src/main/resources/empleados.json";
		
		
		String OutFilePathEmpleados="src/main/resources/empleadosOut.json";
		String OutFilePathLibros="src/main/resources/librosOut.json";
		//String jsonFileventas = "";
		
		//Insertar secuencia de libros desde json
			//JsonHandler.ImportarLibros(jsonFileLibros);
		
		// insertar secuencia de empleados desde json
			//JsonHandler.ImportarEmpleados(jsonFileEmpleados);
		
		
		
		
		
		//________________________EXPORTAR DATOS A JSON___________________________
		
		//Exportar empleados a un fichero json
		
		//JsonHandler.EmpleadosJson(AWS_DDB_Empleados.getAllEmpleados(AWS_DDB_Login.Logg()), OutFilePathEmpleados);
		
		JsonHandler.ExportarEmpleados(OutFilePathEmpleados);
		JsonHandler.ExportarLibros(OutFilePathLibros);
		


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
		
			
		
		
		
		
		
		//_________________________________________ VENTAS _________________________________________
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
		
		//AWS_DDB_Select.buscarItem(AWS_DDB_Login.Logg(), tabla, "VEN_0000", "26/01/2024");
		
	}

}

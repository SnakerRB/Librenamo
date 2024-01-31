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

						String jsonFileLibros = "src/main/resources/libros.json";
						JsonHandler.ImportarLibros(jsonFileLibros);

					} else if (subopcionImportar == 2) {

						// Implementa la lógica para importar datos de ventas desde JSON

					} else if (subopcionImportar == 3) {

						String jsonFileEmpleados = "src/main/resources/empleados.json";
						JsonHandler.ImportarEmpleados(jsonFileEmpleados);

					} else if (subopcionImportar == 4) {
						System.out.println("Volviendo al menú principal...");
					} else {
						System.out.println("Opción no válida");
					}
				} while (subopcionImportar != 4);
			} else if (opcion == 2) {
				int subopcionExportar;
				do {
					System.out.println("Submenú de Importar Datos:");
					System.out.println("1. Importar datos de libros desde JSON");
					System.out.println("2. Importar datos de ventas desde JSON");
					System.out.println("3. Importar datos de empleados desde JSON");
					System.out.println("4. Volver al menú principal");
					System.out.print("Seleccione una opción: ");

					subopcionExportar = scanner.nextInt();

					if (subopcionExportar == 1) {

						String OutFilePathLibros = "src/main/resources/librosOut.json";
						JsonHandler.ExportarLibros(OutFilePathLibros);

					} else if (subopcionExportar == 2) {

						// Implementa la lógica para exportar datos de ventas desde JSON

					} else if (subopcionExportar == 3) {

						String OutFilePathEmpleados = "src/main/resources/empleadosOut.json";
						JsonHandler.ExportarEmpleados(OutFilePathEmpleados);

					} else if (subopcionExportar == 4) {
						System.out.println("Volviendo al menú principal...");
					} else {
						System.out.println("Opción no válida");
					}
				} while (subopcionExportar != 4);
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

						Libro libro = new Libro();
						System.out.println("ISBN: ");
						libro.setISBN(scanner.next());
						System.out.println("Titulo: ");
						libro.setTitulo(scanner.next());
						System.out.println("Autor: ");
						libro.setAutor(scanner.next());
						System.out.println("Año: ");
						libro.setAnio(scanner.next());
						System.out.println("Editorial: ");
						libro.setEditorial(scanner.next());
						System.out.println("Genero: ");
						libro.setGenero(scanner.next());
						System.out.println("Precio: ");
						libro.setPrecio(scanner.next());
						System.out.println("Existencias: ");
						libro.setExistencias(scanner.next());

						AWS_DDB_Libros.Create(AWS_DDB_Login.Logg(), libro);

					} else if (subopcionInsertar == 2) {

						// Implementa la lógica para insertar una venta

					} else if (subopcionInsertar == 3) {

						Empleado emp = new Empleado();
						System.out.println("Nombre: ");
						emp.setNombre(scanner.next());
						System.out.println("Apellido: ");
						emp.setApellido(scanner.next());
						System.out.println("Cargo: ");
						emp.setCargo(scanner.next());
						System.out.println("Fecha de Contrato: ");
						emp.setFechaContrato(scanner.next());
						System.out.println("Direccion: ");
						emp.setDireccion(scanner.next());

						emp.setEmpleadoID("EMP_0002"); // LOGICA IDS

						AWS_DDB_Empleados.Create(AWS_DDB_Login.Logg(), emp);

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

						System.out.println("Introduzca el ISBN del libro a eliminar: ");

						AWS_DDB_Libros.Delete(AWS_DDB_Login.Logg(), scanner.next());

					} else if (subopcionEliminar == 2) {

						// Implementa la lógica para eliminar una venta

					} else if (subopcionEliminar == 3) {

						System.out.println("Introduzca el ID del Empleado a eliminar: ");

						AWS_DDB_Empleados.Delete(AWS_DDB_Login.Logg(), scanner.next());

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

						System.out.println("Introduce el ISBN del Libro que quieres editar: ");
						String isbnToUpdate = scanner.next();
						System.out.println("Introduce el Atributo que queres cambiar");
						String atribToUpdate = scanner.next();
						System.out.println("Introduce el nuevo valor del Atributo");
						String valueToUpdate = scanner.next();

						AWS_DDB_Libros.Edit(AWS_DDB_Login.Logg(), isbnToUpdate, atribToUpdate, valueToUpdate);

					} else if (subopcionEditar == 2) {

						// Implementa la lógica para editar una venta

					} else if (subopcionEditar == 3) {

						System.out.println("Introduce el ID del Empleado que quieres editar: ");
						String idToUpdate = scanner.next();
						System.out.println("Introduce el Atributo que queres cambiar");
						String ValueToChange = scanner.next();
						System.out.println("Introduce el nuevo valor del Atributo");
						String NewValue = scanner.next();

						AWS_DDB_Empleados.Edit(AWS_DDB_Login.Logg(), idToUpdate, ValueToChange, NewValue);

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

						System.out.println("Introduce el ISBN del Libro que desea buscar: ");
						AWS_DDB_Select.buscarItem(AWS_DDB_Login.Logg(), "Libros", scanner.next(), null);

					} else if (subopcionConsultarRegistro == 2) {

						System.out.println("Introduce el ID de la Venta que desea buscar: ");
						String idventa = scanner.next();
						System.out.println("Introduce la fecha de la Venta que desea buscar [DD/MM/AAAA]: ");
						AWS_DDB_Select.buscarItem(AWS_DDB_Login.Logg(), "Libros", idventa, scanner.next());

					} else if (subopcionConsultarRegistro == 3) {

						System.out.println("Introduce el ID del Empleado que desea buscar: ");
						AWS_DDB_Select.buscarItem(AWS_DDB_Login.Logg(), "Empleados", scanner.next(), null);

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

						AWS_DDB_Select.SelectAll(AWS_DDB_Login.Logg(), "Libros");

					} else if (subopcionConsultarTablas == 2) {

						AWS_DDB_Select.SelectAll(AWS_DDB_Login.Logg(), "Ventas");

					} else if (subopcionConsultarTablas == 3) {

						AWS_DDB_Select.SelectAll(AWS_DDB_Login.Logg(), "Empleados");

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
		// _________________________________________ VENTAS test
		/*
		 * Venta vent = new Venta(); vent.setVentaID("VEN_0001");
		 * vent.setFechaVenta("11/09/2011"); vent.setDireccion("Calle de tus muertos");
		 * vent.setEmpleadoID("EMP_0001"); vent.setListaLibrosVendidos("3",
		 * "ISBN-00000"); vent.setTotalVenta("150");
		 * AWS_DDB_Ventas.Create(AWS_DDB_Login.Logg(), vent);
		 * 
		 * AWS_DDB_Ventas.Edit(AWS_DDB_Login.Logg(),"VEN_0001",
		 * "Direccion","AHORA SON MIS MUERTOS","11/09/2011");
		 */
	}

}

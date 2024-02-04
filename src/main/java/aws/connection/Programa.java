package aws.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Esta clase representa el programa principal que permite interactuar con una
 * base de datos DynamoDB para gestionar registros de libros, ventas y
 * empleados.
 */
public class Programa {

	private static final Logger LOGGER = LoggerFactory.getLogger(AWS_DDB_Libros.class);

	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		int opcion;
		Boolean emexiste;
		do {
			System.out.println("Introduce el ID de empleado:");
			String idemp = scanner.next();
			emexiste = AWS_DDB_Empleados.existeEmpleado(AWS_DDB_Login.Logg(), idemp);

			if (emexiste == true) {
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

								String jsonFileVentas = "src/main/resources/ventas.json";
								JsonHandler.ImportarVentas(jsonFileVentas);

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
							System.out.println("Submenú de Exportar Datos:");
							System.out.println("1. Exportar datos de libros a JSON");
							System.out.println("2. Exportar datos de ventas a JSON");
							System.out.println("3. Exportar datos de empleados a JSON");
							System.out.println("4. Volver al menú principal");
							System.out.print("Seleccione una opción: ");

							subopcionExportar = scanner.nextInt();

							if (subopcionExportar == 1) {

								try {
									System.out.println("Introduce la ruta completa donde quieras que se guarde:");
									JsonHandler.ExportarLibros(reader.readLine());
								} catch (Exception e) {
									LOGGER.error("Error en el formulario: " + e);
								}

							} else if (subopcionExportar == 2) {

								try {
									System.out.println("Introduce la ruta completa donde quieras que se guarde:");
									JsonHandler.ExportarVentas(reader.readLine());
								} catch (Exception e) {
									LOGGER.error("Error en el formulario: " + e);
								}

							} else if (subopcionExportar == 3) {

								try {
									System.out.println("Introduce la ruta completa donde quieras que se guarde:");
									JsonHandler.ExportarEmpleados(reader.readLine());
								} catch (Exception e) {
									LOGGER.error("Error en el formulario: " + e);
								}

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

								try {

									Libro libro = new Libro();

									System.out.println("ISBN: ");
									libro.setISBN(reader.readLine());
									System.out.println("Titulo: ");
									libro.setTitulo(reader.readLine());
									System.out.println("Autor: ");
									libro.setAutor(reader.readLine());
									System.out.println("Año: ");
									libro.setAnio(reader.readLine());
									System.out.println("Editorial: ");
									libro.setEditorial(reader.readLine());
									System.out.println("Genero: ");
									libro.setGenero(reader.readLine());
									System.out.println("Precio: ");
									libro.setPrecio(reader.readLine());
									System.out.println("Existencias: ");
									libro.setExistencias(reader.readLine());

									AWS_DDB_Libros.Create(AWS_DDB_Login.Logg(), libro);

								} catch (IOException e) {
									LOGGER.error("Error en el formulario: " + e);
								}

							} else if (subopcionInsertar == 2) {

								try {
									Venta venta = new Venta();

									venta.setVentaID(AWS_DDB_Ventas.IncrementoVentaID(AWS_DDB_Login.Logg()));
									venta.setFechaVenta(LocalDate.now()
											.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")).toString());
									venta.setDireccion("Tienda 1");
									venta.setEmpleadoID(idemp);
									System.out.println("ISBN: ");
									String isbn = reader.readLine();
									System.out.println("Cantidad: ");
									String cantidad = reader.readLine();
									venta.setListaLibrosVendidos(cantidad, isbn);
									Double precio = Double
											.valueOf(AWS_DDB_Ventas.obtenerPrecioPorISBN(AWS_DDB_Login.Logg(), isbn))
											* Integer.valueOf(cantidad);
									venta.setTotalVenta(precio.toString());

									AWS_DDB_Ventas.Create(AWS_DDB_Login.Logg(), venta);

								} catch (IOException e) {
									LOGGER.error("Error en el formulario: " + e);
								}

							} else if (subopcionInsertar == 3) {

								try {
									Empleado emp = new Empleado();
									System.out.println("Nombre: ");
									emp.setNombre(reader.readLine());
									System.out.println("Apellido: ");
									emp.setApellido(reader.readLine());
									System.out.println("Cargo: ");
									emp.setCargo(reader.readLine());
									emp.setFechaContrato(LocalDate.now()
											.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")).toString());
									System.out.println("Direccion: ");
									emp.setDireccion(reader.readLine());

									emp.setEmpleadoID(AWS_DDB_Empleados.IncrementoEmpleadoID(AWS_DDB_Login.Logg()));

									AWS_DDB_Empleados.Create(AWS_DDB_Login.Logg(), emp);
								} catch (IOException e) {
									LOGGER.error("Error en el formulario: " + e);
								}

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

								try {
									System.out.println("Introduzca el ISBN del libro a eliminar: ");

									AWS_DDB_Libros.Delete(AWS_DDB_Login.Logg(), reader.readLine());
								} catch (IOException e) {
									LOGGER.error("Error en el formulario: " + e);
								}

							} else if (subopcionEliminar == 2) {

								try {
									System.out.println("Introduzca el ID de la Venta a eliminar: ");
									String id = reader.readLine();
									System.out.println("Introduzca la fecha de la Venta a eliminar: ");
									String fecha = reader.readLine();

									AWS_DDB_Ventas.Delete(AWS_DDB_Login.Logg(), id, fecha);
								} catch (IOException e) {
									LOGGER.error("Error en el formulario: " + e);
								}

							} else if (subopcionEliminar == 3) {

								try {
									System.out.println("Introduzca el ID del Empleado a eliminar: ");

									AWS_DDB_Empleados.Delete(AWS_DDB_Login.Logg(), reader.readLine());
								} catch (Exception e) {
									LOGGER.error("Error en el formulario: " + e);
								}

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

								try {
									System.out.println("Introduce el ISBN del Libro que quieres editar: ");
									String isbnToUpdate = reader.readLine();
									System.out.println("Introduce el Atributo que queres cambiar");
									String atribToUpdate = reader.readLine();
									System.out.println("Introduce el nuevo valor del Atributo");
									String valueToUpdate = reader.readLine();

									AWS_DDB_Libros.Edit(AWS_DDB_Login.Logg(), isbnToUpdate, atribToUpdate,
											valueToUpdate);
								} catch (IOException e) {
									LOGGER.error("Error en el formulario: " + e);
								}

							} else if (subopcionEditar == 2) {

								try {
									System.out.println("Introduce el ID de la Venta que quieres editar: ");
									String idventa = reader.readLine();
									System.out.println("Introduce la Fecha de la Venta que quieres editar: ");
									String fechaventa = reader.readLine();
									System.out.println("Introduce el Atributo que quieres editar: ");
									String campo = reader.readLine();
									System.out.println("Introduce nuevo valor del Atributo: ");
									String valor = reader.readLine();

									AWS_DDB_Ventas.Edit(AWS_DDB_Login.Logg(), idventa, fechaventa, campo, valor);
								} catch (IOException e) {
									LOGGER.error("Error en el formulario: " + e);
								}

							} else if (subopcionEditar == 3) {

								try {
									System.out.println("Introduce el ID del Empleado que quieres editar: ");
									String idToUpdate = reader.readLine();
									System.out.println("Introduce el Atributo que queres cambiar");
									String ValueToChange = reader.readLine();
									System.out.println("Introduce el nuevo valor del Atributo");
									String NewValue = reader.readLine();

									AWS_DDB_Empleados.Edit(AWS_DDB_Login.Logg(), idToUpdate, ValueToChange, NewValue);
								} catch (IOException e) {
									LOGGER.error("Error en el formulario: " + e);
								}

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

								try {
									System.out.println("Introduce el ISBN del Libro que desea buscar: ");
									AWS_DDB_Select.buscarItem(AWS_DDB_Login.Logg(), "Libros", reader.readLine(), null);
								} catch (Exception e) {
									LOGGER.error("Error en el formulario: " + e);
								}

							} else if (subopcionConsultarRegistro == 2) {

								try {
									System.out.println("Introduce el ID de la Venta que desea buscar: ");
									String idventa = reader.readLine();
									System.out
											.println("Introduce la fecha de la Venta que desea buscar [DD/MM/AAAA]: ");
									AWS_DDB_Select.buscarItem(AWS_DDB_Login.Logg(), "Ventas", idventa,
											reader.readLine());
								} catch (Exception e) {
									LOGGER.error("Error en el formulario: " + e);
								}

							} else if (subopcionConsultarRegistro == 3) {

								try {
									System.out.println("Introduce el ID del Empleado que desea buscar: ");
									AWS_DDB_Select.buscarItem(AWS_DDB_Login.Logg(), "Empleados", reader.readLine(),
											null);
								} catch (Exception e) {
									LOGGER.error("Error en el formulario: " + e);
								}

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
			} else {
				System.out.println("El empleado no existe");
				System.out.println("Introduce el ID de empleado:");
				idemp = scanner.next();
			}
		} while (emexiste == false);
		scanner.close();

	}
}

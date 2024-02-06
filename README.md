## Construido con

* [Maven](https://maven.apache.org/) - Manejador de Dependencias

## Autores

* **Daniel Rodriguez** - *Developer* - [SnakerRB](https://github.com/SnakerRB)
* **Diego Alonso** - *Developer* - [DAdB03](https://github.com/DAdB03)

## :warning: **Caution:**
> Es necesario tener una cuenta de Amazon Web Services.

## :exclamation:**Important:**
>Es necesario Tener Instalada la correspondiente versión de el JDK


## :bulb: **Consejos**
># Sistema de Gestión AWS DynamoDB
>Este proyecto contiene una serie de clases diseñadas para facilitar la interacción con AWS DynamoDB, centradas en la gestión de empleados, libros, ventas y configuraciones de AWS.

>## Manejadores de Tablas

>### AWS_DDB_Empleados
>Gestiona registros de empleados en DynamoDB. Provee métodos para crear, eliminar, editar y obtener empleados, además de generar nuevos IDs de empleado y verificar la existencia de un registro.
>[AWS_DDB_Empleados](https://github.com/SnakerRB/Librenamo/blob/main/src/main/java/aws/connection/AWS_DDB_Empleados.java)
>
>### AWS_DDB_Libros
>Se especializa en la gestión de registros de libros en DynamoDB. Ofrece funcionalidades para la creación, eliminación, edición y recuperación de registros de libros, manejo de atributos como ISBN, autor, año, etc.
>[AWS_DDB_Libros](https://github.com/SnakerRB/Librenamo/blob/main/src/main/java/aws/connection/AWS_DDB_Libros.java)
>### AWS_DDB_Ventas
>Administra operaciones en la tabla "Ventas" de DynamoDB. Incluye métodos para crear, eliminar, editar y recuperar registros de ventas, así como funcionalidades adicionales como la generación de nuevos IDs de venta.
>[AWS_DDB_Ventas](https://github.com/SnakerRB/Librenamo/blob/main/src/main/java/aws/connection/AWS_DDB_Ventas.java)

>## Manejadores de Objeto
>
>### Empleado
>Representa un empleado con atributos como ID, apellido, cargo, entre otros. Proporciona métodos para acceder y modificar estos atributos, adecuados para la integración con AWS DynamoDB.
>[Empleado](https://github.com/SnakerRB/Librenamo/blob/main/src/main/java/aws/connection/Empleado.java)
>
>### Libro
>Representa un libro con atributos como ISBN, autor, precio, entre otros. Incluye métodos para la manipulación de estos datos, adecuados para DynamoDB.
>[Libro](https://github.com/SnakerRB/Librenamo/blob/main/src/main/java/aws/connection/Libro.java)
>
>### Venta
>Representa una venta con atributos como ID de venta, total de venta, entre otros. Gestiona una lista de libros vendidos y permite modificar los atributos relevantes.
>[Venta](https://github.com/SnakerRB/Librenamo/blob/main/src/main/java/aws/connection/Venta.java)

>## Auxiliares
>
>### AWS_DDB_Login
>Provee un cliente DynamoDB autenticado, configurándolo con credenciales de AWS. Esencial para operaciones de base de datos autenticadas.
>[AWS_DDB_Login](https://github.com/SnakerRB/Librenamo/blob/main/src/main/java/aws/connection/AWS_DDB_Login.java)
>
>### AWS_DDB_Properties
>Gestiona la configuración de AWS para DynamoDB. Carga propiedades desde un archivo de configuración y es clave para la autenticación y configuración de la base de datos.
>[AWS_DDB_Properties](https://github.com/SnakerRB/Librenamo/blob/main/src/main/java/aws/connection/AWS_DDB_Properties.java)
>
>### AWS_DDB_Select
>Permite realizar consultas en tablas DynamoDB. Ofrece métodos para listar todos los registros y buscar registros específicos.
>[AWS_DDB_Select](https://github.com/SnakerRB/Librenamo/blob/main/src/main/java/aws/connection/AWS_DDB_Select.java)
>
>### JsonHandler
>Maneja la importación y exportación de datos entre formatos JSON y DynamoDB. Incluye métodos para leer y almacenar datos en DynamoDB, así como para exportar datos a archivos JSON.
>[JsonHandler](https://github.com/SnakerRB/Librenamo/blob/main/src/main/java/aws/connection/JsonHandler.java)

>### Programa
>Es el punto de entrada principal para la aplicación, permitiendo operaciones sobre registros de libros, ventas y empleados. Gestiona la interacción del usuario con la base de datos a través de un menú en consola
>[Programa](https://github.com/SnakerRB/Librenamo/blob/main/src/main/java/aws/connection/Programa.java)

>## Comenzando
>Para utilizar estas clases, clona el repositorio y sigue las instrucciones de instalación y configuración específicas en la documentación de cada clase.


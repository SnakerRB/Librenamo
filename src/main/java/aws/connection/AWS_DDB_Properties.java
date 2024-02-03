package aws.connection;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Esta clase proporciona métodos para cargar y acceder a propiedades
 * relacionadas con la configuración de AWS. Las propiedades se cargan desde un
 * archivo de propiedades llamado "DynamoDB.properties".
 */
public class AWS_DDB_Properties {

	private Properties properties;
	private static final Logger LOGGER = LoggerFactory.getLogger(AWS_DDB_Properties.class);

	/**
	 * Constructor de la clase que carga las propiedades desde el archivo
	 * "DynamoDB.properties".
	 */
	public AWS_DDB_Properties() {
		properties = new Properties();

		try {
			// Carga el archivo de propiedades desde la carpeta resources
			InputStream inputStream = getClass().getClassLoader().getResourceAsStream("DynamoDB.properties");

			if (inputStream != null) {
				properties.load(inputStream);
				LOGGER.trace("Fichero Properties Cargado correctamente");
			} else {
				LOGGER.error("No se pudo encontrar el archivo de propiedades.");

			}
		} catch (IOException e) {
			LOGGER.error("Error al cargar el archivo de propiedades: " + e.getMessage());
		}
	}

	/**
	 * Obtiene la clave de acceso de AWS desde las propiedades cargadas.
	 *
	 * @return La clave de acceso de AWS.
	 */
	public String getAccessKey() {
		return properties.getProperty("aws.accessKey");
	}

	/**
	 * Obtiene la clave secreta de AWS desde las propiedades cargadas.
	 *
	 * @return La clave secreta de AWS.
	 */
	public String getSecretKey() {
		return properties.getProperty("aws.secretKey");
	}

	/**
	 * Obtiene el token de sesión de AWS desde las propiedades cargadas.
	 *
	 * @return El token de sesión de AWS.
	 */
	public String getSessionToken() {
		return properties.getProperty("aws.sessionToken");
	}
}

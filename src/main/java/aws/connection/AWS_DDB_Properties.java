package aws.connection;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AWS_DDB_Properties {

	private Properties properties;
	private static final Logger LOGGER = LoggerFactory.getLogger(AWS_DDB_Properties.class);
	public AWS_DDB_Properties() {
		properties = new Properties();
		
		try {
			// Carga el archivo de propiedades desde la carpeta resources
			InputStream inputStream = getClass().getClassLoader().getResourceAsStream("DynamoDB.properties");
			
			if (inputStream != null) {
				properties.load(inputStream);
				LOGGER.info("Fichero Properties Cargado correctamente");
			} else {
				LOGGER.error("No se pudo encontrar el archivo de propiedades.");	
				
			}
		} catch (IOException e) {
			LOGGER.error("Error al cargar el archivo de propiedades: " + e.getMessage());
		}
	}

	public String getAccessKey() {
		return properties.getProperty("aws.accessKey");
	}

	public String getSecretKey() {
		return properties.getProperty("aws.secretKey");
	}

	public String getSessionToken() {
		return properties.getProperty("aws.sessionToken");
	}
}

package aws.connection;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AWS_DDB_Properties {

	private Properties properties;

	public AWS_DDB_Properties() {
		properties = new Properties();
		try {
			// Carga el archivo de propiedades desde la carpeta resources
			InputStream inputStream = getClass().getClassLoader().getResourceAsStream("DynamoDB.properties");
			if (inputStream != null) {
				properties.load(inputStream);
			} else {
				System.err.println("No se pudo encontrar el archivo de propiedades.");
			}
		} catch (IOException e) {
			System.err.println("Error al cargar el archivo de propiedades: " + e.getMessage());
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

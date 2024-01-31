package aws.connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import software.amazon.awssdk.auth.credentials.AwsSessionCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

public class AWS_DDB_Login {

	private static final Logger LOGGER = LoggerFactory.getLogger(AWS_DDB_Login.class);
	public static DynamoDbClient Logg() {
		
		// Cargamos propiedades
		AWS_DDB_Properties config = new AWS_DDB_Properties();
		
		// Configura las credenciales de AWS
		String accessKey = config.getAccessKey();
		String secretKey = config.getSecretKey();
		String sessionToken = config.getSessionToken();
		
		// Crea las credenciales incluyendo el token de sesión
		AwsSessionCredentials credentials = AwsSessionCredentials.create(accessKey, secretKey, sessionToken);
		LOGGER.trace("Credenciales cargadas correctamente");
		// Imprime las credenciales personalizadas
		LOGGER.trace("Access Key: " + credentials.accessKeyId());
		LOGGER.trace("Secret Key: " + credentials.secretAccessKey());
		LOGGER.trace("Session Token: " + credentials.sessionToken());

		// Configura el cliente de DynamoDB
		Region region = Region.US_EAST_1;
		DynamoDbClient dynamoDbClient = DynamoDbClient.builder().region(region)
				.credentialsProvider(StaticCredentialsProvider.create(credentials)).build();
		
		return dynamoDbClient;
	}
	
	
	
	// METODO PARA INICIO DE SESION
	/*
	public static DynamoDbClient LoggMain(String accessKey,String secretKey,String sessionToken) {

		// Crea las credenciales incluyendo el token de sesión
		AwsSessionCredentials credentials = AwsSessionCredentials.create(accessKey, secretKey, sessionToken);
	
		// Imprime las credenciales personalizadas
		System.out.println("Access Key: " + credentials.accessKeyId());
		System.out.println("Secret Key: " + credentials.secretAccessKey());
		System.out.println("Session Token: " + credentials.sessionToken());
	
		// Configura el cliente de DynamoDB
		Region region = Region.US_EAST_1;
		DynamoDbClient dynamoDbClient = DynamoDbClient.builder().region(region)
				.credentialsProvider(StaticCredentialsProvider.create(credentials)).build();
		
		return dynamoDbClient;
	}

*/
}


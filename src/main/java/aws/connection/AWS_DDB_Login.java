package aws.connection;

import software.amazon.awssdk.auth.credentials.AwsSessionCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

public class AWS_DDB_Login {


	public static DynamoDbClient Logg() {
		
		// Cargamos propiedades
		AWS_DDB_Properties config = new AWS_DDB_Properties();
		
		// Configura las credenciales de AWS
		String accessKey = config.getAccessKey();
		String secretKey = config.getSecretKey();
		String sessionToken = config.getSessionToken();

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
}

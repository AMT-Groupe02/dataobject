package ch.amt.dataobject.aws;

import ch.amt.dataobject.ICloudClient;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.rekognition.RekognitionClient;

public class AwsCloudClient implements ICloudClient {
    private static AwsCloudClient instance = null;
    AwsCredentialsProvider credentialsProvider;
    private RekognitionClient rekognitionClient = new RekognitionClient() {
        @Override
        public String serviceName() {
            return null;
        }

        @Override
        public void close() {

        }
    };

    Region region;
    public static AwsCloudClient getInstance(){
        if(instance == null){
            instance = new AwsCloudClient();
        }
        return instance;
    }

    private AwsCloudClient() {
        credentialsProvider = EnvironmentVariableCredentialsProvider.create();
        region = Region.EU_WEST_2;
    }

    public AwsCredentialsProvider getCredentialsProvider() {
        return credentialsProvider;
    }

    public Region getRegion() {
        return region;
    }

    RekognitionClient getRekognitionClient() {
        return rekognitionClient;
    }
}

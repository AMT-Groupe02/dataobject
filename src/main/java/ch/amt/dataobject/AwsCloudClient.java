package ch.amt.dataobject;

import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.rekognition.RekognitionClient;

public class AwsCloudClient implements ICloudClient {
    static AwsCloudClient instance = null;
    AwsCredentialsProvider credentialsProvider;
    private AwsLabelDetectorHelper labelDetectorHelper;
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
        labelDetectorHelper = new AwsLabelDetectorHelper();
        region = Region.EU_WEST_1;
    }

    public AwsCredentialsProvider getCredentialsProvider() {
        return credentialsProvider;
    }

    public Region getRegion() {
        return region;
    }


    @Override
    public ILabelDetector getLabelDetector() {
        return labelDetectorHelper;
    }

    RekognitionClient getRekognitionClient() {
        return rekognitionClient;
    }
}

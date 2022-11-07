package ch.amt.dataobject;

import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.regions.Region;

public class AwsCloudClient implements ICloudClient {
    static AwsCloudClient instance = null;
    AwsCredentialsProvider credentialsProvider;
    AwsLabelDetectorHelper labelDetectorHelper;

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
}

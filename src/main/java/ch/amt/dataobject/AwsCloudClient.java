package ch.amt.dataobject;

import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.regions.Region;

public class AwsCloudClient implements ICloudClient {
    private static AwsCloudClient instance = null;
    AwsCredentialsProvider credentialsProvider;
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
}

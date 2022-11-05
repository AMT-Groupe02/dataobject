package ch.amt.dataobject;

import org.junit.jupiter.api.Test;
import software.amazon.awssdk.regions.Region;

import static org.junit.jupiter.api.Assertions.*;

class AwsCloudClientTest {

    @Test
    void isSingleton(){
        AwsCloudClient client1 = AwsCloudClient.getInstance();
        AwsCloudClient client2 = AwsCloudClient.getInstance();
        assertEquals(client1, client2);
    }

    @Test
    void regionIsEUWEST1(){
        AwsCloudClient client = AwsCloudClient.getInstance();
        assertEquals(client.getRegion(), Region.EU_WEST_1);
    }

    @Test
    void getCredientialsProvider(){
        AwsCloudClient client = AwsCloudClient.getInstance();
        assertNotNull(client.getCredentialsProvider());
    }
}
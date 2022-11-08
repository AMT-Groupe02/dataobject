package ch.amt.dataobject;
import org.junit.jupiter.api.Test;
import software.amazon.awssdk.core.waiters.WaiterResponse;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.HeadBucketRequest;
import software.amazon.awssdk.services.s3.model.HeadBucketResponse;
import software.amazon.awssdk.services.s3.waiters.S3Waiter;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class AwsLabelDetectorHelperTest {
    private String BUCKET_NAME = "amt.team02.diduno.education";
    @Test
    void getLabelsShouldReturnLabelsTest() {
        //create
        try(S3Client s3 = S3Client.builder().credentialsProvider(AwsCloudClient.getInstance().getCredentialsProvider()).region(AwsCloudClient.getInstance().getRegion()).build()){

            S3Waiter waiter = s3.waiter();

            CreateBucketRequest createBucketRequest = CreateBucketRequest.builder()
                    .bucket(BUCKET_NAME)
                    .build();
            s3.createBucket(createBucketRequest);

            HeadBucketRequest bucketRequestWait = HeadBucketRequest.builder()
                    .bucket(BUCKET_NAME)
                    .build();

            WaiterResponse<HeadBucketResponse> waiterResponse = waiter.waitUntilBucketExists(bucketRequestWait);
            waiterResponse.matched().response().ifPresent(System.out::println);
            System.out.println(BUCKET_NAME +" is ready");

        }catch (Exception e){
            e.printStackTrace();
        }


        ILabelDetector labelDetectorHelper = AwsCloudClient.getInstance().getLabelDetector();

        List<LabelObj> labels = labelDetectorHelper.getLabelsFromImage(BUCKET_NAME, );

        assertNotEquals(0, labels.size());

        for (LabelObj label : labels) {
            assertNotEquals(0, label.getName().length());
        }
    }
}

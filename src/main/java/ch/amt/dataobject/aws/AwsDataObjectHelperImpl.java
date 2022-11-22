package ch.amt.dataobject.aws;

import ch.amt.dataobject.IDataObjectHelper;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.core.waiters.WaiterResponse;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.waiters.S3Waiter;


import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class AwsDataObjectHelperImpl implements IDataObjectHelper {
    private static final AwsCloudClient awsClient = AwsCloudClient.getInstance();
    @Override
    public void createBucket(String bucketName) {
        try(S3Client s3 = S3Client.builder().credentialsProvider(awsClient.getCredentialsProvider()).region(awsClient.getRegion()).build()){

            S3Waiter waiter = s3.waiter();

            CreateBucketRequest createBucketRequest = CreateBucketRequest.builder()
                    .bucket(bucketName)
                    .build();
            s3.createBucket(createBucketRequest);

            HeadBucketRequest bucketRequestWait = HeadBucketRequest.builder()
                    .bucket(bucketName)
                    .build();

            WaiterResponse<HeadBucketResponse> waiterResponse = waiter.waitUntilBucketExists(bucketRequestWait);
            //TODO REVIEW Remove all println (or convert it into log assertion)
            waiterResponse.matched().response().ifPresent(System.out::println);
            System.out.println(bucketName +" is ready");

        }catch (Exception e){
            //TODO REVIEW Do not catch the whole world ! Be more specific.
            //TODO REVIEW Exception management does not mean catch and print...
            e.printStackTrace();
        }
    }

    @Override
    public void deleteBucket(String bucketName) {
        try(S3Client s3 = S3Client.builder().credentialsProvider(awsClient.getCredentialsProvider()).region(awsClient.getRegion()).build()){

            S3Waiter waiter = s3.waiter();

            ListObjectsV2Request listObjectsV2Request = ListObjectsV2Request.builder()
                    .bucket(bucketName)
                    .build();
            ListObjectsV2Response listObjectsV2Response;

            do {
                listObjectsV2Response = s3.listObjectsV2(listObjectsV2Request);
                for (S3Object s3Object : listObjectsV2Response.contents()) {
                    DeleteObjectRequest request = DeleteObjectRequest.builder()
                            .bucket(bucketName)
                            .key(s3Object.key())
                            .build();
                    s3.deleteObject(request);
                }
            } while (Boolean.TRUE.equals(listObjectsV2Response.isTruncated()));

            DeleteBucketRequest deleteBucketRequest = DeleteBucketRequest.builder()
                    .bucket(bucketName)
                    .build();
            s3.deleteBucket(deleteBucketRequest);

            HeadBucketRequest bucketRequestWait = HeadBucketRequest.builder()
                    .bucket(bucketName)
                    .build();

            WaiterResponse<HeadBucketResponse> waiterResponse = waiter.waitUntilBucketNotExists(bucketRequestWait);
            waiterResponse.matched().response().ifPresent(System.out::println);
            System.out.println(bucketName +" is deleted");

        }
    }


    @Override
    public boolean bucketExists(String bucketName) {
        try(S3Client s3 = S3Client.builder().credentialsProvider(awsClient.getCredentialsProvider()).region(awsClient.getRegion()).build()){

            ListBucketsResponse response = s3.listBuckets();

            for (Bucket bucket : response.buckets()) {
                if(bucket.name().equals(bucketName)){
                    return true;
                }
            }

        }
        return false;
    }

    @Override
    public void uploadImageInBucket(String bucketName, String fileName, String base64Data, String contentType) {
        try(S3Client s3 = S3Client.builder().credentialsProvider(awsClient.getCredentialsProvider()).region(awsClient.getRegion()).build()){

            byte[] bI = java.util.Base64.getDecoder().decode(base64Data);
            InputStream fis = new ByteArrayInputStream(bI);

            s3.putObject(PutObjectRequest.builder().bucket(bucketName).key(fileName)
                            .contentType(contentType)
                            .contentLength((long) bI.length)
                            .build(),
                    RequestBody.fromInputStream(fis, bI.length));
        }
    }

    @Override
    public boolean imageExistsInBucket(String bucketName, String imageName) {
        try(S3Client s3 = S3Client.builder().credentialsProvider(awsClient.getCredentialsProvider()).region(awsClient.getRegion()).build()){

            GetObjectRequest request = GetObjectRequest.builder().bucket(bucketName).key(imageName).build();

             s3.getObject(request);

        }catch(NoSuchKeyException e){
            return false;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }
}

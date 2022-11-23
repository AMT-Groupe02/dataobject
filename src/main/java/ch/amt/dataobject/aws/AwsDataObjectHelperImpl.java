package ch.amt.dataobject.aws;

import ch.amt.dataobject.IDataObjectHelper;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.core.waiters.WaiterResponse;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.waiters.S3Waiter;


import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

public class AwsDataObjectHelperImpl implements IDataObjectHelper {
    private static final AwsCloudClient awsClient = AwsCloudClient.getInstance();
    private final String bucketName = "amt.team02.diduno.education";
    public static void createBucket(String bucketName) {
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
            waiterResponse.matched().response().ifPresent(System.out::println);
            System.out.println(bucketName +" is ready");

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void deleteBucket(String bucketName) {
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


    public static boolean bucketExists(String bucketName) {
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
    public String getUrl(String filePath) {
        return null;
    }

    @Override
    public void deleteFile(String filePath) {

    }

    @Override
    public void sendFile(String filePath, String base64Data) {


        try(S3Client s3 = S3Client.builder().credentialsProvider(awsClient.getCredentialsProvider()).region(awsClient.getRegion()).build()){

            byte[] bI = java.util.Base64.getDecoder().decode(base64Data);
            InputStream fis = new ByteArrayInputStream(bI);


            s3.putObject(PutObjectRequest.builder().bucket(bucketName).key(filePath)
                            .contentLength((long) bI.length)
                            .build(),
                    RequestBody.fromInputStream(fis, bI.length));
        }
    }

    @Override
    public boolean fileExists(String filePath) {
        try(S3Client s3 = S3Client.builder().credentialsProvider(awsClient.getCredentialsProvider()).region(awsClient.getRegion()).build()){

            GetObjectRequest request = GetObjectRequest.builder().bucket(bucketName).key(filePath).build();

            s3.getObject(request);

        }catch(NoSuchKeyException e){
            return false;
        }
        catch (Exception e){
            e.printStackTrace();
            // TODO créer une exception custom pour chaque type d'erreur
        }
        return true;
    }

    public static void createFolder(String[] args) {
        String bucketName = "nam-public-images";
        String folderName = "asia/vietnam/";

        S3Client client = S3Client.builder().build();

        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName).key(folderName).build();


        client.putObject(request, RequestBody.empty());

        S3Waiter waiter = client.waiter();
        HeadObjectRequest requestWait = HeadObjectRequest.builder()
                .bucket(bucketName).key(folderName).build();

        WaiterResponse<HeadObjectResponse> waiterResponse = waiter.waitUntilObjectExists(requestWait);

        waiterResponse.matched().response().ifPresent(System.out::println);

        System.out.println("Folder " + folderName + " is ready.");
    }

}

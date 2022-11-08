package ch.amt.dataobject;

import java.io.File;

public interface IDataObjectHelper {
    void createBucket(String bucketName);

    void deleteBucket(String bucketName);
    boolean bucketExists(String bucketName);
    void uploadImageInBucket(String bucketName, String fileName, String base64Data, String contentType);

    boolean imageExistsInBucket(String bucketName, String imageName);
}

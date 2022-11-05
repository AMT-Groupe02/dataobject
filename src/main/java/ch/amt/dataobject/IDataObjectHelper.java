package ch.amt.dataobject;

import java.io.File;

public interface IDataObjectHelper {
    void createBucket(String bucketName);
    boolean bucketExists(String bucketName);
    void uploadImageInBucket(String bucketName, File image);
}

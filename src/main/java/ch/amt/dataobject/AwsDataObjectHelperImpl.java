package ch.amt.dataobject;

import java.io.File;

public class AwsDataObjectHelperImpl implements IDataObjectHelper {
    @Override
    public void createBucket(String bucketName) {

    }

    @Override
    public boolean bucketExists(String bucketName) {
        return false;
    }

    @Override
    public void uploadImageInBucket(String bucketName, File image) {

    }
}

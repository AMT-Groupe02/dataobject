package ch.amt.dataobject;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AwsDataObjectHelperImplTest {

    @AfterEach
    void tearDown() {
        AwsDataObjectHelperImpl awsDataObjectHelper = new AwsDataObjectHelperImpl();
        awsDataObjectHelper.deleteBucket("test");
    }

    @Test
    void createBucket() {
        AwsDataObjectHelperImpl awsDataObjectHelper = new AwsDataObjectHelperImpl();
        awsDataObjectHelper.createBucket("test");
        assertTrue(awsDataObjectHelper.bucketExists("test"));
    }

    @Test
    void deleteBucket(){
        AwsDataObjectHelperImpl awsDataObjectHelper = new AwsDataObjectHelperImpl();
        awsDataObjectHelper.createBucket("test");
        awsDataObjectHelper.deleteBucket("test");
        assertFalse(awsDataObjectHelper.bucketExists("test"));
    }

    @Test
    void bucketExists() {
        AwsDataObjectHelperImpl awsDataObjectHelper = new AwsDataObjectHelperImpl();
        assertFalse(awsDataObjectHelper.bucketExists("test"));
        awsDataObjectHelper.createBucket("test");
        assertTrue(awsDataObjectHelper.bucketExists("test"));
    }

    @Test
    void uploadImageInBucket() {
    }
}
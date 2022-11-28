package ch.amt.dataobject.aws;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@Disabled("Disabled because AWS don't like creating buckets again and again...")
class AwsBucketHelperTest {

    static String bucketName = "amt.team02.diduno.education";

    @BeforeEach
    void setUp() {
        if(AwsBucketHelper.bucketExists(bucketName)){
            AwsBucketHelper.deleteBucket(bucketName);
        }
    }

    @AfterEach
    void tearDown() {
        if(AwsBucketHelper.bucketExists(bucketName)){
            AwsBucketHelper.deleteBucket(bucketName);
        }
    }

    @AfterAll
    static void afterAll(){
        if(!AwsBucketHelper.bucketExists(bucketName)){
            AwsBucketHelper.createBucket(bucketName);
        }
    }

    @Test
    void createBucket() {
        AwsBucketHelper.createBucket(bucketName);
        assertTrue(AwsBucketHelper.bucketExists(bucketName));
    }

    @Test
    void deleteBucket(){
        AwsBucketHelper.createBucket(bucketName);
        AwsBucketHelper.deleteBucket(bucketName);
        assertFalse(AwsBucketHelper.bucketExists(bucketName));
    }

    @Test
    void bucketExists() {
        assertFalse(AwsBucketHelper.bucketExists(bucketName));
        AwsBucketHelper.createBucket(bucketName);
        assertTrue(AwsBucketHelper.bucketExists(bucketName));
    }
}
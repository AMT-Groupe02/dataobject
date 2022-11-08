package ch.amt.dataobject.aws;

import ch.amt.dataobject.LabelObj;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

public class AwsApp {
    static final private String bucketName = "amt.team02.diduno.education";
    static final private String imageKey = "image";

    public static String getExtension(String filename) {
        return filename.substring(filename.lastIndexOf(".") + 1);
    }


    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java -jar aws-app.jar <imagePath>");
            System.exit(1);
        }

        try {
            byte[] fileContent = FileUtils.readFileToByteArray(new File(args[0]));
            String encodedString = Base64.getEncoder().encodeToString(fileContent);

            AwsDataObjectHelperImpl awsDataObjectHelper = new AwsDataObjectHelperImpl();
            AwsLabelDetectorHelper awsLabelDetectorHelper = new AwsLabelDetectorHelper();

            if (!awsDataObjectHelper.bucketExists(bucketName)) {
                awsDataObjectHelper.createBucket(bucketName);
            }

            awsDataObjectHelper.uploadImageInBucket(bucketName, imageKey, encodedString, getExtension(args[0]));
            List<LabelObj> labels = awsLabelDetectorHelper.getLabelsFromImage(bucketName, imageKey);

            System.out.println("Labels:");
            for (LabelObj label : labels) {
                System.out.println(label.getName() + " " + label.getConfidence());
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}

package ch.amt.dataobject;

import ch.amt.dataobject.aws.AwsDataObjectHelperImpl;
import ch.amt.dataobject.aws.AwsLabelDetectorHelper;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

public class App {
    static final private String bucketName = "amt.team02.diduno.education";
    static final private String imageKey = "image";

    public static String getExtension(String filename) {
        return filename.substring(filename.lastIndexOf(".") + 1);
    }


    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Parameter : image path");
            System.exit(1);
        }

        try {
            byte[] fileContent = FileUtils.readFileToByteArray(new File(args[0]));
            String encodedString = Base64.getEncoder().encodeToString(fileContent);

            IDataObjectHelper dataObjectHelper = new AwsDataObjectHelperImpl();
            ILabelDetector labelDetectorHelper = new AwsLabelDetectorHelper();

            if (!dataObjectHelper.bucketExists(bucketName)) {
                dataObjectHelper.createBucket(bucketName);
            }

            dataObjectHelper.uploadImageInBucket(bucketName, imageKey, encodedString, getExtension(args[0]));
            List<LabelObj> labels = labelDetectorHelper.getLabelsFromImage(bucketName, imageKey);

            System.out.println("Labels:");
            for (LabelObj label : labels) {
                System.out.println(label.getName() + " " + label.getConfidence());
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}

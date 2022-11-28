package ch.amt.dataobject;

import ch.amt.dataobject.aws.AwsDataObject;
import ch.amt.dataobject.aws.AwsLabelDetectorHelper;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

public class App {
    private static final String BUCKET_NAME = "amt.team02.diduno.education";
    private static final String IMAGE_KEY = "image";
    private static final int ARGS_LEN = 3;

    private static String getExtension(String filename) {
        return filename.substring(filename.lastIndexOf(".") + 1);
    }


    public static void main(final String[] args) {
        if (args.length != ARGS_LEN) {
            System.out.println("Parameter : <image path> <max labels> <min confidence>");
            System.exit(1);
        }

        try {
            byte[] fileContent = FileUtils.readFileToByteArray(new File(args[0]));
            String encodedString = Base64.getEncoder().encodeToString(fileContent);

            IDataObject object = new AwsDataObject(BUCKET_NAME + "/" + IMAGE_KEY + "." + getExtension(args[0]));

            ILabelDetector labelDetectorHelper = new AwsLabelDetectorHelper();

            object.upload(encodedString);

            String url = object.getUrl();

            List<LabelObj> labels = labelDetectorHelper.getLabelsFromImage(url, Integer.parseInt(args[1]), Integer.parseInt(args[2]));

            System.out.println("Labels:");
            for (LabelObj label : labels) {
                System.out.println(label.getName() + " " + label.getConfidence());
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NumberFormatException e) {
            throw new RuntimeException("max labels and min confidence must be integers");
        }
    }
}

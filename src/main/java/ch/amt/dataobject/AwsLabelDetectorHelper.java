package ch.amt.dataobject;

import software.amazon.awssdk.services.rekognition.model.*;
import java.util.LinkedList;
import java.util.List;

public class AwsLabelDetectorHelper implements ILabelDetector {
    @Override
    public List<LabelObj> getLabelsFromImage(String bucketName, String imageKey) {

        try {
            DetectLabelsRequest detectLabelsRequest = DetectLabelsRequest.builder().image(
                    Image.builder().s3Object(
                            S3Object.builder().name(imageKey).bucket(bucketName).build()).build()).build();

            DetectLabelsResponse labelsResponse = AwsCloudClient.getInstance().getRekognitionClient().detectLabels(detectLabelsRequest);
            List<Label> labels = labelsResponse.labels();
            System.out.println("Detected labels for the given photo");
            for (Label label: labels) {
                System.out.println(label.name() + ": " + label.confidence().toString());
            }

            return awsLabelsToLabelObjs(labels);

        } catch (RekognitionException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
        return null;
    }

    private List<LabelObj> awsLabelsToLabelObjs(List<Label> labels) {
        List<LabelObj> labelObjs = new LinkedList<>();
        for (Label label : labels) {
            labelObjs.add(awsLabelToLabelObj(label));
        }
        return labelObjs;
    }

    private LabelObj awsLabelToLabelObj(Label label) {
        List<LabelObj.InstanceObj> instances = new LinkedList<>();
        if (label.instances() != null) {
            for (Instance instance : label.instances()) {
                instances.add(new LabelObj.InstanceObj(instance.confidence(), instance.boundingBox() != null ?
                        new LabelObj.InstanceObj.BoundingBoxObj(
                                instance.boundingBox().height(),
                                instance.boundingBox().left(),
                                instance.boundingBox().top(),
                                instance.boundingBox().width()) : null));
            }
        }

        List<String> parents = new LinkedList<>();
        if (label.parents() != null) {
            for (Parent parent : label.parents()) {
                parents.add(parent.name());
            }
        }
        return new LabelObj(label.name(), label.confidence(), instances, parents);
    }
}

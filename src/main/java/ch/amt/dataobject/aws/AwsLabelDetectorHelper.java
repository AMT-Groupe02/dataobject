package ch.amt.dataobject.aws;

import ch.amt.dataobject.ILabelDetector;
import ch.amt.dataobject.LabelObj;
import software.amazon.awssdk.services.rekognition.RekognitionClient;
import software.amazon.awssdk.services.rekognition.model.*;
import java.util.LinkedList;
import java.util.List;

public class AwsLabelDetectorHelper implements ILabelDetector {
    private static final AwsCloudClient awsClient = AwsCloudClient.getInstance();

    @Override
    public List<LabelObj> getLabelsFromImage(String bucketName, String imageKey, int maxLabels, float minConfidence) {

        if (maxLabels < 0 || minConfidence < 0 || minConfidence > 100) {
            throw new IllegalArgumentException("maxLabels and minConfidence must be greater or equal to 0");
        }

        try(RekognitionClient rekClient = RekognitionClient.builder().credentialsProvider(awsClient.getCredentialsProvider()).region(awsClient.getRegion()).build()) {

            DetectLabelsRequest detectLabelsRequest = DetectLabelsRequest.builder().image(
                    Image.builder().s3Object(
                            S3Object.builder().name(imageKey).bucket(bucketName).build()).build())
                    .maxLabels(maxLabels)
                    .minConfidence(minConfidence)
                    .build();

            DetectLabelsResponse labelsResponse = rekClient.detectLabels(detectLabelsRequest);
            List<Label> labels = labelsResponse.labels();

            return awsLabelsToLabelObjs(labels);

        }
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

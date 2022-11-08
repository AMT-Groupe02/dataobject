package ch.amt.dataobject;

import software.amazon.awssdk.services.rekognition.model.Label;

import java.util.List;

public interface ILabelDetector {
    List<LabelObj> getLabelsFromImage(String bucketName, String imageKey);
}

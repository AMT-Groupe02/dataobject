package ch.amt.dataobject;

import java.util.List;

public interface ILabelDetector {
    List<LabelObj> getLabelsFromImage(String bucketName, String imageKey, int maxLabels, float minConfidence);
}

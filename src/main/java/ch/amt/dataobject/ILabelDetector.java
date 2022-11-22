package ch.amt.dataobject;

import software.amazon.awssdk.services.rekognition.model.Label;

import java.util.List;

public interface ILabelDetector {
    // TODO ajouter un paramètre pour limiter le nombre de labels retourné
    // TODO ajouter un paramètre pour limiter le niveau confience minimal accepté
    List<LabelObj> getLabelsFromImage(String bucketName, String imageKey);

    // TODO Label detection with base64 picture
}

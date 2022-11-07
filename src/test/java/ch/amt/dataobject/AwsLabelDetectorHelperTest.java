package ch.amt.dataobject;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class AwsLabelDetectorHelperTest {
    @Test
    void getLabelsShouldReturnLabelsTest() {
        ILabelDetector labelDetectorHelper = AwsCloudClient.getInstance().getLabelDetector();

        List<LabelObj> labels = labelDetectorHelper.getLabelsFromImage("https://cms.motoscout24.ch/media/23101/motorradpruefung_motoscout24.jpg?width=990&height=557&center=0.505338078291815,0.49645390070922&mode=crop");

        assertNotEquals(0, labels.size());

        for (LabelObj label : labels) {
            assertNotEquals(0, label.getName().length());
        }
    }
}

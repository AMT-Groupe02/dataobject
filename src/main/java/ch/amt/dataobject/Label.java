package ch.amt.dataobject;

public class Label {
    public String getName() {
        return name;
    }

    public float getConfidence() {
        return confidence;
    }

    private String name;
    private float confidence;

    public Label(String name, float confidence) {
        this.name = name;
        this.confidence = confidence;
    }
}

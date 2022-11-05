package ch.amt.dataobject;

public class Label {
    private String name;
    private float confidence;

    public Label(String name, float confidence) {
        this.name = name;
        this.confidence = confidence;
    }
}

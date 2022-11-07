package ch.amt.dataobject;

import java.util.LinkedList;
import java.util.List;

public class LabelObj {
    private String name;
    private float confidence;
    private List<InstanceObj> instances;
    private List<String> parents;

    public LabelObj(String name, float confidence) {
        this.name = name;
        this.confidence = confidence;
        this.instances = new LinkedList<>();
        this.parents = new LinkedList<>();
    }

    public String getName() {
        return name;
    }

    public float getConfidence() {
        return confidence;
    }

    public List<InstanceObj> getInstances() {
        return instances;
    }

    public List<String> getParents() {
        return parents;
    }

    public class InstanceObj {
        private double confidence;
        private BoundingBoxObj boundingBox;

        public InstanceObj(double confidence, BoundingBoxObj boundingBox) {
            this.confidence = confidence;
            this.boundingBox = boundingBox;
        }


        public class BoundingBoxObj {
            private double width;
            private double height;
            private double left;
            private double top;

            BoundingBoxObj(double width, double height, double left, double top) {
                this.width = width;
                this.height = height;
                this.left = left;
                this.top = top;
            }

            public double getWidth() {
                return width;
            }

            public double getHeight() {
                return height;
            }

            public double getLeft() {
                return left;
            }

            public double getTop() {
                return top;
            }
        }
    }
}

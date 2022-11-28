package ch.amt.dataobject;

public class DataObjectNotFoundException extends RuntimeException {
    public DataObjectNotFoundException() {
        super("File not found");
    }
}

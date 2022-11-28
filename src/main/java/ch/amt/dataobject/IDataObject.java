package ch.amt.dataobject;

public interface IDataObject {
    String getUrl();
    void delete();
    void upload(String dataBase64);
    void upload(byte[] dataBytes);

    byte[] download() throws Exception;
    boolean exists();

}

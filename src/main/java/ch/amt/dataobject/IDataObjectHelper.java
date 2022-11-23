package ch.amt.dataobject;

import java.io.File;

public interface IDataObjectHelper {
    String getUrl(String filePath);
    void deleteFile(String filePath);
    void sendFile(String filePath, String base64Data);

    boolean fileExists(String filePath);

}

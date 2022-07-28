package com.gamesmart.simplechat.enghine.util;
public class FileUtil {

 
    public static String getExtension(String filename) {
        if (filename == null) {
            return null;
        }
        int extensionPos = filename.lastIndexOf('.');  
 
        if (extensionPos == -1) {
            return "";
        } else {
            return filename.substring(extensionPos+1);
        }
    }
}
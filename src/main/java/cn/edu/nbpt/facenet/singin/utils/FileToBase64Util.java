package cn.edu.nbpt.facenet.singin.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Base64;

//file流转化为base64
public class FileToBase64Util {
    public static String fileToBase64(File file) {
        if (file == null) {
            return null;
        }
        String base64 = null;
        FileInputStream in = null;
        try {
            in = new FileInputStream(file);
            byte[] buff = new byte[in.available()];
            in.read(buff);
            base64 = Base64.getEncoder().encodeToString(buff);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return base64;
    }

}

package cn.edu.nbpt.facenet.singin.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

//base64转化为file流
public class Base64ToFileUtil {

    /**
     * @param base64 base64的图片文件
     * @param prefix 文件的前缀名
     * @return
     */
    public static File base64ToFile(String base64,String prefix) {
        if (base64 == null || "".equals(base64)) {
            return null;
        }
        byte[] buff = Base64.getDecoder().decode(base64);
        File file = null;
        FileOutputStream out = null;
        try {
            file = File.createTempFile(prefix, ".png");
            out = new FileOutputStream(file);
            out.write(buff);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return file;
    }
}

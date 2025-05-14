package model.map;

import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;

public class FileHashUtil {
    //todo:后续添加文件SHA256值一键比较功能,或者是防止玩家作弊
    public static String getFileSHA256(File file) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                digest.update(buffer, 0, bytesRead);
            }
        }
        byte[] hashBytes = digest.digest();
        StringBuilder hashString = new StringBuilder();
        for (byte b : hashBytes) {
            hashString.append(String.format("%02x", b));
        }
        return hashString.toString();
    }

    public static void main(String[] args) {
        try {
            File file = new File("path/to/your/file.txt");
            String sha256 = getFileSHA256(file);
            System.out.println("文件的SHA-256值: " + sha256);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
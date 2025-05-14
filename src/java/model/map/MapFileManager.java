package model.map;

import org.json.JSONObject;

import java.io.*;
import java.nio.file.*;
import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;

public class MapFileManager {

    private static final String MAP_DIR = "src/resources/maps/";
    private static final String MAP_DIR_DEFAULT = "src/resources/maps/default/";
    // 设计思路:通过mapId来唯一标识地图文件,操作文件的时候显然更简便
    // 存储地图
    public static void saveMap(MapModel mapModel) throws IOException {
        Path dirPath = Paths.get(MAP_DIR_DEFAULT);
        if (!Files.exists(dirPath)) {
            Files.createDirectories(dirPath);
        }

        // 使用实例调用 toJson()
        JSONObject json = mapModel.toJson();
        String jsonContent = json.toString(2); // 美化格式输出

        // 计算 SHA-256 值
        String sha256 = calculateSHA256(jsonContent);
        mapModel.setMapId(sha256); // 更新 mapId 为 SHA-256 值

        try (FileWriter writer = new FileWriter(MAP_DIR_DEFAULT + sha256 + ".json")) {
            writer.write(jsonContent);
        }
    }

    // 删除地图
    public static void deleteMap(String mapId) throws IOException {
        Path path = Paths.get(MAP_DIR + mapId + ".json");
        if (Files.exists(path)) {
            Files.delete(path);
        } else {
            System.out.println("文件不存在: " + mapId);
            //todo:这里可以补充根据mapId返回文件名
        }
    }

    // 加载地图
    public static MapModel loadMapFromFile(String mapId) throws IOException {
        Path path = Paths.get(MAP_DIR_DEFAULT + mapId + ".json");
        if (!Files.exists(path)) {
            throw new FileNotFoundException("地图文件不存在: " + mapId + ".json");
        }

        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(path.toFile()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
        }

        JSONObject jsonObject = new JSONObject(content.toString());
        return MapModel.fromJson(jsonObject);
    }

    // 计算字符串的 SHA-256 值
    private static String calculateSHA256(String content) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(content.getBytes());
            StringBuilder hashString = new StringBuilder();
            for (byte b : hashBytes) {
                hashString.append(String.format("%02x", b));
            }
            return hashString.toString();
        } catch (Exception e) {
            throw new RuntimeException("计算 SHA-256 失败", e);
        }
    }
}
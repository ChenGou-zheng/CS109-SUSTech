package model;

import org.json.JSONObject;

import java.io.*;
import java.nio.file.*;

public class MapFileManager {

    private static final String MAP_DIR = "maps/";

    // 存储地图
    public static void saveMap(String filename, MapModel mapModel) throws IOException {
        Path dirPath = Paths.get(MAP_DIR);
        if (!Files.exists(dirPath)) {
            Files.createDirectories(dirPath);
        }

        // 使用实例调用 toJson()
        JSONObject json = mapModel.toJson();
        try (FileWriter writer = new FileWriter(MAP_DIR + filename)) {
            writer.write(json.toString(2)); // 美化格式输出
        }
    }

    // 删除地图
    public static void deleteMap(String filename) throws IOException {
        Path path = Paths.get(MAP_DIR + filename);
        if (Files.exists(path)) {
            Files.delete(path);
        } else {
            System.out.println("文件不存在: " + filename);
        }
    }

    // 加载地图
    public static MapModel loadMapFromFile(String filename) throws IOException {
        Path path = Paths.get(MAP_DIR + filename);
        if (!Files.exists(path)) {
            throw new FileNotFoundException("地图文件不存在: " + filename);
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
}
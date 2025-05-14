package model.map;

import org.json.JSONObject;

import java.io.*;
import java.nio.file.*;

public class MapFileManager {

    private static final String MAP_DIR = "maps/";
    // 设计思路:通过mapId来唯一标识地图文件,操作文件的时候显然更简便
    // 存储地图
    public static void saveMap(MapModel mapModel) throws IOException {
        Path dirPath = Paths.get(MAP_DIR);
        if (!Files.exists(dirPath)) {
            Files.createDirectories(dirPath);
        }

        // 使用实例调用 toJson()
        JSONObject json = mapModel.toJson();
        try (FileWriter writer = new FileWriter(MAP_DIR + mapModel.getMapId() + ".json")) {
            writer.write(json.toString(2)); // 美化格式输出
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
        Path path = Paths.get(MAP_DIR + mapId + ".json");
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
}
package model;

import java.io.*;
import java.util.Timer;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.NotSerializableException;
import java.io.InvalidClassException;

import model.map.MapModel;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import model.map.MapModel;
import view.game.GamePanel;
import model.timer.TimerManager;


public class GameSaveManager {
    // todo:保存游戏进度,但是不需要类实例化,使用静态方法改写
    private static final String MAP_DIR = "src/resources/archives/autosave/";
    private static final String AUTO_SAVE_FILE = "src/resources/archives/autosave/autoSave.json";
//todo:autoSave应该是和专属于用户,而且每次打开新游戏创建一份新的,还有两个字段:accout and mapId
//todo:对应的autoload

    // 使用 JSON 保存游戏
    public static void saveGame(String saveFilePath, MapModel mapModel, int steps, long remainingTime) throws IOException {
        Path filePath = Paths.get(saveFilePath);
        Path dirPath = filePath.getParent(); // 获取文件夹路径

        // 如果文件夹不存在，则创建
        if (dirPath != null && !Files.exists(dirPath)) {
            Files.createDirectories(dirPath);
        }

        // 构建 JSON 对象
        JSONObject json = new JSONObject();
        json.put("mapModel", mapModel.toJson()); // 假设 MapModel 有 toJson 方法
        json.put("steps", steps);
        json.put("remainingTime", remainingTime);

        // 保存到指定文件
        try (FileWriter writer = new FileWriter(saveFilePath)) {
            writer.write(json.toString(2)); // 美化格式输出
            System.out.println("游戏进度已保存到：" + saveFilePath);
        }
    }

    // 使用 JSON 加载游戏
    public static Object[] loadGame(String saveFilePath) throws IOException {
        Path filePath = Paths.get(saveFilePath);
        if (!Files.exists(filePath)) {
            throw new IOException("保存文件不存在：" + saveFilePath);
        }

        StringBuilder content = new StringBuilder();
        try (var reader = Files.newBufferedReader(filePath)) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
        }

        JSONObject json = new JSONObject(content.toString());
        MapModel mapModel = MapModel.fromJson(json.getJSONObject("mapModel")); // 假设 MapModel 有 fromJson 方法
        int steps = json.getInt("steps");
        long remainingTime = json.getLong("remainingTime");

        return new Object[]{mapModel, steps, remainingTime};
    }

    public static void autoSave(MapModel mapModel, int steps, long remainingTime) throws IOException {
        saveGame(AUTO_SAVE_FILE, mapModel, steps, remainingTime);
        System.out.println("自动保存已完成！");
    }

    // 自动加载
    public static Object[] autoLoad() throws IOException {
        return loadGame(AUTO_SAVE_FILE);
    }

    // 停止自动保存定时器
    public void stopAutoSave(Timer autoSaveTimer) {
        if (autoSaveTimer != null) {
            autoSaveTimer.cancel();
            System.out.println("自动保存定时器已停止！");
        }
    }
}
package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

//todo:注意区别log文件和user文件，log用于调试，记录游戏运行状态的

public class LogModel {
    private static final String LOG_FILE_PATH = "logs.json";
    private JSONArray logs;

    public LogModel() {
        loadLogs();
    }

    //2025年5月11日添加动态代理功能。

    // 加载日志数据
    private void loadLogs() {
        try {
            File file = new File(LOG_FILE_PATH);
            if (!file.exists()) {
                logs = new JSONArray();
                saveLogs();
            } else {
                String content = new String(Files.readAllBytes(Paths.get(LOG_FILE_PATH)));
                logs = new JSONArray(content);
            }
        } catch (IOException e) {
            e.printStackTrace();
            logs = new JSONArray();
        }
    }

    // 保存日志数据到文件
    private void saveLogs() {
        try (FileWriter writer = new FileWriter(LOG_FILE_PATH)) {
            writer.write(logs.toString(4)); // 格式化输出
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 添加日志
    public void addLog(String username, String password, int[][] mapModel, String action) {
        JSONObject log = new JSONObject();
        log.put("username", username);
        log.put("password", password);
        log.put("mapModel", mapModelToJson(mapModel));
        log.put("action", action);
        log.put("timestamp", System.currentTimeMillis());
        logs.put(log);
        saveLogs();
    }

    // 将地图模型转换为 JSON
    private JSONArray mapModelToJson(int[][] mapModel) {
        JSONArray jsonArray = new JSONArray();
        for (int[] row : mapModel) {
            JSONArray jsonRow = new JSONArray();
            for (int cell : row) {
                jsonRow.put(cell);
            }
            jsonArray.put(jsonRow);
        }
        return jsonArray;
    }

    // 获取所有日志
    public JSONArray getLogs() {
        return logs;
    }
}
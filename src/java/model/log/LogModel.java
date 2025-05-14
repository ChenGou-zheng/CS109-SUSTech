package model.log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

//todo:注意区别log文件和user文件，log用于调试，记录游戏运行状态的


//todo:这里是怎么管理文件路径的?
//功能说明:
//动态代理：在调用 saveGame 方法时，拦截并计算 mapData 的 SHA256 值。
  //      SHA256 计算：将二维数组转为字符串后计算其哈希值。
    //    日志记录：将用户名、SHA256 值和 mapData 记录到日志文件中。日志文件以 JSON 格式存储，便于后续分析。

public class LogModel {
    private static final String LOG_FILE = "resources/logs/game_logs.json";
    private static final String LOG_SHA_FILE = "resources/logs/sha256_logs.json";
    private JSONArray logs;

    public LogModel() {
        initializeLogFileIfNotExists();
        //loadLogs();
    }

    private void initializeLogFileIfNotExists() {
        try {
            Files.createDirectories(Paths.get("resources/logs"));
            if (!Files.exists(Paths.get(LOG_FILE))) {
                try (FileWriter writer = new FileWriter(LOG_FILE)) {
                    writer.write("[]");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addSHA256Log(String username, String sha256Hash, int[][] mapData) {
        try {
            String content = new String(Files.readAllBytes(Paths.get(LOG_SHA_FILE)));
            JSONArray logs = new JSONArray(content);

            JSONObject logEntry = new JSONObject();
            logEntry.put("username", username);
            logEntry.put("sha256", sha256Hash);
            logEntry.put("mapData", mapDataToString(mapData));

            logs.put(logEntry);

            try (FileWriter writer = new FileWriter(LOG_SHA_FILE)) {
                writer.write(logs.toString(4));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addGameLog(int[][] mapData, String action) {
        try {
            String content = new String(Files.readAllBytes(Paths.get(LOG_FILE)));
            JSONArray logs = new JSONArray(content);

            JSONObject logEntry = new JSONObject();
            //logEntry.put("username", username);
            logEntry.put("mapData", mapDataToString(mapData));
            logEntry.put("action", action);
            logEntry.put("timestamp", System.currentTimeMillis());

            logs.put(logEntry);

            try (FileWriter writer = new FileWriter(LOG_FILE)) {
                writer.write(logs.toString(4));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int[][] getLastState() {
        try {
            String content = new String(Files.readAllBytes(Paths.get(LOG_FILE)));
            JSONArray logs = new JSONArray(content);

            if (logs.length() > 1) {
                JSONObject lastLog = logs.getJSONObject(logs.length() - 2); // 获取倒数第二条日志
                return stringToMapData(lastLog.getString("mapData"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private int[][] stringToMapData(String mapDataString) {
        String[] rows = mapDataString.split(";");
        int[][] mapData = new int[rows.length][];
        for (int i = 0; i < rows.length; i++) {
            String[] cells = rows[i].split(",");
            mapData[i] = new int[cells.length];
            for (int j = 0; j < cells.length; j++) {
                mapData[i][j] = Integer.parseInt(cells[j]);
            }
        }
        return mapData;
    }


    private String mapDataToString(int[][] mapData) {
        StringBuilder builder = new StringBuilder();
        for (int[] row : mapData) {
            for (int cell : row) {
                builder.append(cell).append(",");
            }
            builder.append(";");
        }
        return builder.toString();
    }
    //2025年5月11日添加动态代理功能。

    // 加载日志数据
    private void loadLogs() {
        try {
            File file = new File(LOG_FILE);
            if (!file.exists()) {
                logs = new JSONArray();
                saveLogs();
            } else {
                String content = new String(Files.readAllBytes(Paths.get(LOG_FILE)));
                logs = new JSONArray(content);
            }
        } catch (IOException e) {
            e.printStackTrace();
            logs = new JSONArray();
        }
    }

    // 保存日志数据到文件
    private void saveLogs() {
        try (FileWriter writer = new FileWriter(LOG_FILE)) {
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
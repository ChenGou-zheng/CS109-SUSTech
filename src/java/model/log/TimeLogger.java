package model.log;

import model.timer.GameTime;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONObject;
import org.json.JSONArray;

public class TimeLogger {
    private static final String TIME_LOG_FILE = "resources/logs/time_logs.json";
    private final GameTime gameTime;

    public TimeLogger(GameTime gameTime) {
        this.gameTime = gameTime;
        initializeLogFileIfNotExists();
    }

    private void initializeLogFileIfNotExists() {
        try {
            Files.createDirectories(Paths.get("resources/logs"));
            if (!new File(TIME_LOG_FILE).exists()) {
                try (FileWriter writer = new FileWriter(TIME_LOG_FILE)) {
                    writer.write("[]");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void logAction(String username, String actionType) {
        JSONObject logEntry = new JSONObject();
        logEntry.put("username", username);
        logEntry.put("action", actionType);
        logEntry.put("timestamp", System.currentTimeMillis());
        logEntry.put("total_seconds", gameTime.getTotalSeconds());

        appendLogToFile(logEntry);
    }

    private void appendLogToFile(JSONObject logEntry) {
        try {
            String content = new String(Files.readAllBytes(Paths.get(TIME_LOG_FILE)));
            JSONArray logs = new JSONArray(content);
            logs.put(logEntry);

            try (FileWriter writer = new FileWriter(TIME_LOG_FILE)) {
                writer.write(logs.toString(4));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
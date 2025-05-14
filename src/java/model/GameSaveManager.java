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
import view.game.GamePanel;
import model.timer.TimerManager;


public class GameSaveManager {
    // 保存游戏进度,但是不需要类实例化
    public static void saveGame(String saveFilePath, MapModel mapModel, int steps, long remainingTime) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(saveFilePath))) {
            oos.writeObject(mapModel); // 保存地图模型
            oos.writeInt(steps); // 保存步数
            oos.writeLong(remainingTime); // 保存剩余时间
            System.out.println("游戏进度已保存！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Object[] loadGame(String saveFilePath) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(saveFilePath))) {
            MapModel mapModel = (MapModel) ois.readObject(); // 恢复地图模型
            int steps = ois.readInt(); // 恢复步数
            long remainingTime = ois.readLong(); // 恢复剩余时间
            return new Object[]{mapModel, steps, remainingTime};
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    //确保文件路径有效
    private void ensureAutoSaveFilePath(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
                System.out.println("自动保存文件已创建：" + filePath);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("无法创建自动保存文件：" + filePath);
            }
        }
    }

    // 自动保存方法
    public void autoSave(String filePath, MapModel mapModel, GamePanel view, TimerManager timerManager) {
        ensureAutoSaveFilePath(filePath);
        GameSaveManager.saveGame(filePath, mapModel, view.getSteps(), timerManager.getRemainingTime());
        System.out.println("游戏已自动保存！");
    }

    // 停止自动保存定时器
    public void stopAutoSave(Timer autoSaveTimer) {
        if (autoSaveTimer != null) {
            autoSaveTimer.cancel();
            System.out.println("自动保存定时器已停止！");
        }
    }
    /*
    //todo:高robust版本?
    public static void saveGame(String saveFilePath, MapModel mapModel, int steps, long remainingTime) {
        try {
            // 确保文件路径有效
            ensureFilePath(saveFilePath);

            // 保存游戏数据
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(saveFilePath))) {
                oos.writeObject(mapModel); // 保存地图模型
                oos.writeInt(steps); // 保存步数
                oos.writeLong(remainingTime); // 保存剩余时间
                System.out.println("游戏进度已保存！");
            }
        } catch (NotSerializableException e) {
            System.err.println("序列化失败：某些对象未实现 Serializable 接口 - " + e.getMessage());
        } catch (InvalidClassException e) {
            System.err.println("序列化失败：类的序列化版本号不匹配 - " + e.getMessage());
        } catch (IOException e) {
            System.err.println("保存失败：发生 IO 异常 - " + e.getMessage());
        } catch (Exception e) {
            System.err.println("保存失败：发生未知异常 - " + e.getMessage());
        }
    }

    private static void ensureFilePath(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            File parentDir = file.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                if (!parentDir.mkdirs()) {
                    throw new IOException("无法创建目录：" + parentDir.getAbsolutePath());
                }
            }
            if (!file.createNewFile()) {
                throw new IOException("无法创建文件：" + filePath);
            }
        }
    }
    */
}
import javafx.application.Application;
import javafx.stage.Stage;
import model.LogModel;
import model.map.MapFileManager;
import model.map.MapModel;
import model.timer.TimerManager;
import view.game.GameFrame;
import view.login.LoginFrame;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        //创建日志模型
        //todo:写入log
        LogModel logModel = new LogModel();

        // 创建地图模型
        // 目前是直接指定地图，todo:使用load功能加载并选中
        String fileName = "level1.json";
        //todo:输入地图文件的路径与UI

        MapModel loadedMap = null;
        try {
            loadedMap = MapFileManager.loadMapFromFile("x000");
            // 检查是否成功加载并打印地图
            if (loadedMap != null) {
                System.out.println("成功加载地图：");
                loadedMap.printMap();
            } else {
                System.out.println("加载地图失败！");
            }
        } catch (java.io.IOException e) {
            e.printStackTrace();
            System.out.println("加载地图时发生错误！");
        }
        // 检查是否成功加载并打印地图
        if (loadedMap != null) {
            System.out.println("成功加载地图：");
            loadedMap.printMap();
        } else {
            System.out.println("加载地图失败！");
        }
        // 创建游戏界面
        GameFrame gameFrame = new GameFrame(loadedMap, new TimerManager(120));

        // 创建登录界面
        LoginFrame loginFrame = new LoginFrame();
        loginFrame.setGameFrame(gameFrame);

        // 显示登录界面
        loginFrame.start(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}





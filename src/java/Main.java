import javafx.application.Application;
import javafx.stage.Stage;
import model.LogModel;
import model.MapModel;
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
        MapModel mapModel = new MapModel(new int[][]{
                {1, 2, 2, 1},
                {1, 3, 2, 2},
                {1, 3, 4, 4},
                {0, 0, 4, 4}
        });
        //todo:兼任了UI Controller
        // 创建游戏界面
        GameFrame gameFrame = new GameFrame(mapModel);

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





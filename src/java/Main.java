import javafx.application.Application;
import javafx.stage.Stage;
import model.MapModel;
import view.game.GameFrame;
import view.login.LoginFrame;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        // 创建地图模型
        MapModel mapModel = new MapModel(new int[][]{
                {1, 2, 2, 1},
                {1, 3, 2, 2},
                {1, 3, 4, 4},
                {0, 0, 4, 4}
        });

        // 创建游戏界面
        GameFrame gameFrame = new GameFrame();

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
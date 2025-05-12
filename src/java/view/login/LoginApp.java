package view.login;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.game.GameFrame;
import model.MapModel;

import java.io.IOException;

public class LoginApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginFrame.fxml"));
            Scene scene = new Scene(loader.load());

            MapModel mapModel = new MapModel(new int[][]{
                    {1, 2, 2, 1},
                    {1, 3, 2, 2},
                    {1, 3, 4, 4},
                    {0, 0, 4, 4}
            });
            GameFrame gameFrame = new GameFrame(mapModel);

            LoginController controller = loader.getController();
            controller.setGameFrame(gameFrame);

            primaryStage.setTitle("Login Frame");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
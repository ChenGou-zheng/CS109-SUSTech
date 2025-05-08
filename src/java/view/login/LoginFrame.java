package view.login;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import view.FxComponentUtil;
import view.game.GameFrame;

public class LoginFrame extends Application {
    private TextField username;
    private PasswordField password;
    private Button submitBtn;
    private Button resetBtn;
    private GameFrame gameFrame;

    @Override
    public void start(Stage primaryStage) {
        Pane root = new Pane();
        root.setStyle("-fx-background-color: #f5f5f5;");
        root.setPrefSize(400, 300);

        // 标题
        Label title = FxComponentUtil.createLabel(root, "Login", Font.font("Arial", 24), 150, 30);

        // 用户名标签和输入框
        FxComponentUtil.createLabel(root, 50, 80, "Username:");
        username = FxComponentUtil.createTextField(root, 120, 75, 200);

        // 密码标签和输入框
        FxComponentUtil.createLabel(root, 50, 130, "Password:");
        password = FxComponentUtil.createPasswordField(root, 120, 125, 200);

        // 按钮
        submitBtn = FxComponentUtil.createButton(root, "Confirm", 80, 180);
        submitBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        submitBtn.setPrefWidth(100);

        resetBtn = FxComponentUtil.createButton(root, "Reset", 220, 180);
        resetBtn.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");
        resetBtn.setPrefWidth(100);

        // 事件处理
        submitBtn.setOnAction(e -> {
            System.out.println("Username = " + username.getText());
            System.out.println("Password = " + password.getText());
            if (this.gameFrame != null) {
                this.gameFrame.show();
                primaryStage.hide();
            }
            //todo: check login info
        });

        resetBtn.setOnAction(e -> {
            username.clear();
            password.clear();
        });

        Scene scene = new Scene(root);
        primaryStage.setTitle("Login Frame");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public void setGameFrame(GameFrame gameFrame) {
        this.gameFrame = gameFrame;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
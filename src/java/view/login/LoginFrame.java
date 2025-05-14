package view.login;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import view.util.FrameUtil;
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
        Label title = FrameUtil.createLabel(root, "Login", Font.font("Arial", 24), 150, 30);

        // 用户名标签和输入框
        FrameUtil.createLabel(root, 50, 80, "Username:");
        username = FrameUtil.createTextField(root, 120, 75, 200);

        // 密码标签和输入框
        FrameUtil.createLabel(root, 50, 130, "Password:");
        password = FrameUtil.createPasswordField(root, 120, 125, 200);

        // 按钮
        submitBtn = FrameUtil.createButton(root, "Confirm","file:resources/icons/submit.png", 80, 180);
        submitBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        submitBtn.setPrefWidth(100);

        resetBtn = FrameUtil.createButton(root, "Reset", "file:resources/icons/reset.png",220, 180);
        resetBtn.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");
        resetBtn.setPrefWidth(100);

        // 事件处理
        submitBtn.setOnAction(e -> {
            String user = username.getText();
            String pass = password.getText();
            if (user.isEmpty() || pass.isEmpty()) {
                showAlert("Error", "Username or password cannot be empty!");
                return;
            }

            System.out.println("Username = " + user);
            System.out.println("Password = " + pass);

            if (this.gameFrame != null) {
                Stage gameStage = new Stage();
                this.gameFrame.start(gameStage);
                primaryStage.hide();
            }
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

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
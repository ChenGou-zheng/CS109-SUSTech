package view.login;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.UserModel;

public class RegisterPage extends Application {
    @Override
    public void start(Stage primaryStage) {
        UserModel userModel = new UserModel();

        // 创建布局
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        // 添加输入字段
        Label usernameLabel = new Label("用户名:");
        TextField usernameField = new TextField();
        Label passwordLabel = new Label("密码:");
        PasswordField passwordField = new PasswordField();
        Label emailLabel = new Label("邮箱:");
        TextField emailField = new TextField();
        Label confirmPasswordLabel = new Label("确认密码:");
        PasswordField confirmPasswordField = new PasswordField();
        Button registerButton = new Button("注册");
        Label messageLabel = new Label();

        // 布局位置
        gridPane.add(usernameLabel, 0, 0);
        gridPane.add(usernameField, 1, 0);
        gridPane.add(passwordLabel, 0, 1);
        gridPane.add(passwordField, 1, 1);
        gridPane.add(emailLabel, 0, 2);
        gridPane.add(emailField, 1, 2);
        gridPane.add(confirmPasswordLabel, 0, 3);
        gridPane.add(confirmPasswordField, 1, 3);
        gridPane.add(registerButton, 1, 4);
        gridPane.add(messageLabel, 1, 5);

        // 注册按钮事件
        registerButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            String email = emailField.getText();
            String confirmPassword = confirmPasswordField.getText();

            if (username.isEmpty() || password.isEmpty() || email.isEmpty() || confirmPassword.isEmpty()) {
                messageLabel.setText("所有字段均不能为空！");
                return;
            }

            if (!password.equals(confirmPassword)) {
                messageLabel.setText("密码和确认密码不一致！");
                return;
            }

            boolean success = userModel.addUser(username, password, email);
            if (success) {
                messageLabel.setText("注册成功！");
            } else {
                messageLabel.setText("用户名已存在！");
            }
        });

        // 设置场景
        Scene scene = new Scene(gridPane, 400, 250);
        primaryStage.setTitle("用户注册");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
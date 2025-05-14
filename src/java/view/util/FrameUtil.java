package view.util;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * JavaFX UI组件工具类
 */
public class FrameUtil {
    public static Label createLabel(Pane parent, int x, int y, String text) {
        Label label = new Label(text);
        label.setLayoutX(x);
        label.setLayoutY(y);
        parent.getChildren().add(label);
        return label;
    }

    public static Label createLabel(Pane parent, String text, Font font, int x, int y) {
        Label label = new Label(text);
        label.setFont(font);
        label.setLayoutX(x);
        label.setLayoutY(y);
        parent.getChildren().add(label);
        return label;
    }

    public static TextField createTextField(Pane parent, int x, int y, int width) {
        TextField textField = new TextField();
        textField.setLayoutX(x);
        textField.setLayoutY(y);
        textField.setPrefWidth(width);
        parent.getChildren().add(textField);
        return textField;
    }

    public static PasswordField createPasswordField(Pane parent, int x, int y, int width) {
        PasswordField passwordField = new PasswordField();
        passwordField.setLayoutX(x);
        passwordField.setLayoutY(y);
        passwordField.setPrefWidth(width);
        parent.getChildren().add(passwordField);
        return passwordField;
    }

    public static Button createButton(Pane parent, String text, String iconPath, int x, int y) {
        Button button = new Button(text);
        button.setLayoutX(x);
        button.setLayoutY(y);

        // 如果提供了图标路径，则加载图标
        if (iconPath != null && !iconPath.isEmpty()) {
            ImageView icon = new ImageView(new Image(iconPath));
            icon.setFitWidth(16); // 设置图标宽度
            icon.setFitHeight(16); // 设置图标高度
            button.setGraphic(icon); // 设置图标到按钮
        }

        parent.getChildren().add(button);
        return button;
    }
}
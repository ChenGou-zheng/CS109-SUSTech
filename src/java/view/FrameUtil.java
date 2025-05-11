package view;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

/**
 * JavaFX UI组件工具类
 */
public class FrameUtil {
    public static Label createLabel(Pane parent, double x, double y, String text) {
        Label label = new Label(text);
        label.setLayoutX(x);
        label.setLayoutY(y);
        parent.getChildren().add(label);
        return label;
    }

    public static Label createLabel(Pane parent, String text, Font font, double x, double y) {
        Label label = new Label(text);
        label.setFont(font);
        label.setLayoutX(x);
        label.setLayoutY(y);
        parent.getChildren().add(label);
        return label;
    }

    public static TextField createTextField(Pane parent, double x, double y, double width) {
        TextField textField = new TextField();
        textField.setLayoutX(x);
        textField.setLayoutY(y);
        textField.setPrefWidth(width);
        parent.getChildren().add(textField);
        return textField;
    }

    public static PasswordField createPasswordField(Pane parent, double x, double y, double width) {
        PasswordField passwordField = new PasswordField();
        passwordField.setLayoutX(x);
        passwordField.setLayoutY(y);
        passwordField.setPrefWidth(width);
        parent.getChildren().add(passwordField);
        return passwordField;
    }

    public static Button createButton(Pane parent, String text, double x, double y) {
        Button button = new Button(text);
        button.setLayoutX(x);
        button.setLayoutY(y);
        parent.getChildren().add(button);
        return button;
    }
}
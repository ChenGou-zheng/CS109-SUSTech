package view.login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import view.game.GameFrame;

public class LoginController {

    @FXML private TextField username;
    @FXML private PasswordField password;
    @FXML private Button submitBtn;
    @FXML private Button resetBtn;

    private GameFrame gameFrame;

    public void setGameFrame(GameFrame gameFrame) {
        this.gameFrame = gameFrame;
    }

    @FXML
    private void handleSubmit(ActionEvent event) {
        String user = username.getText();
        String pass = password.getText();

        if (user.isEmpty() || pass.isEmpty()) {
            showAlert("Error", "Username or password cannot be empty!");
            return;
        }

        System.out.println("Username = " + user);
        System.out.println("Password = " + pass);

        if (gameFrame != null) {
            Stage gameStage = new Stage();
            gameFrame.start(gameStage);
            ((Stage) submitBtn.getScene().getWindow()).hide();
        }
    }

    @FXML
    private void handleReset(ActionEvent event) {
        username.clear();
        password.clear();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
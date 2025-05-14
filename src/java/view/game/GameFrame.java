package view.game;

import java.util.Optional;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import model.GameSaveManager;
import model.map.MapModel;
import controller.GameController;
import model.map.MapFileManager;
import model.timer.TimerManager;


public class GameFrame extends Application {
    private GameController controller;
    private Button restartBtn;
    private Button loadBtn;
    private Label stepLabel;
    private Label timeLabel;
    private Timeline timeline;
    private GamePanel gamePanel;
    private MapModel mapModel;//当前游戏中传入的model模型
    private TimerManager timerManager;

    public GameFrame(MapModel mapModel) {
        this.mapModel = mapModel;
    }
    public void setFrameSteps(int steps) {
        stepLabel.setText("Steps: " + steps);
    }
    public GameFrame(MapModel mapModel, TimerManager timerManager) {
        this.timerManager = timerManager;

        this.mapModel = mapModel;}

    @Override
    public void start(Stage primaryStage) {
        // 初始化计时器显示
        timeLabel = new Label("Time Left: " + formatTime(timerManager.getRemainingTime()));
        timeLabel.setStyle("-fx-font-size: 16; -fx-text-fill: black;");

        // 定时更新计时器
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            long remainingTime = timerManager.getRemainingTime();
            timeLabel.setText("Time Left: " + formatTime(remainingTime));
            if (remainingTime <= 0) {
                timeline.stop();
                // 触发时间到的逻辑
                onTimeUp();
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();



        // 初始化模型和面板
        gamePanel = new GamePanel(mapModel);
        GameSaveManager gameSaveManager = new GameSaveManager();
        controller = new GameController(gamePanel, mapModel, timerManager, gameSaveManager);
        gamePanel.setController(controller);
        controller.setGameFrame(this); // 设置 GameFrame 的引用

        // 创建UI组件
        stepLabel = new Label("Steps: 0");
        stepLabel.setStyle("-fx-font-size: 22; -fx-font-style: italic;");

        restartBtn = createButton("Restart", e -> {
            controller.restartGame();
            gamePanel.requestFocus();
        });
        restartBtn.setFocusTraversable(false); // 禁用焦点

        loadBtn = createButton("Load", e -> handleLoadMap());
        loadBtn.setFocusTraversable(false); // 禁用焦点
        // 布局
        VBox controlPanel = new VBox(20, stepLabel, restartBtn, loadBtn);
        controlPanel.setPadding(new Insets(20));
        controlPanel.setStyle("-fx-background-color: #f0f0f0;");

        BorderPane root = new BorderPane();
        root.setCenter(gamePanel);
        root.setRight(controlPanel);

        // 动态调整宽度
        gamePanel.prefWidthProperty().bind(root.widthProperty().multiply(0.7));
        controlPanel.prefWidthProperty().bind(root.widthProperty().multiply(0.3));

        // 场景和舞台设置
        Scene scene = new Scene(root, 800, 600);
        //调整后删除，转移 GamePanel, setupKeyEvents(scene);

        primaryStage.setTitle("2025 CS109 Project Demo");
        primaryStage.setScene(scene);
        primaryStage.show();

        // 初始焦点
        gamePanel.requestFocus();

        // 设置步数更新回调

        gamePanel.setStepUpdateCallback(steps -> stepLabel.setText("Steps: " + steps));

    }

    private Button createButton(String text, javafx.event.EventHandler<javafx.event.ActionEvent> action) {
        Button button = new Button(text);
        button.setStyle("-fx-font-size: 16; -fx-padding: 10;");
        button.setOnAction(action);
        return button;
    }

    private void handleLoadMap() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Load Map");
        dialog.setHeaderText("Enter the path to the map file:");
        dialog.setContentText("Path:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(path -> {
            try {
                MapModel newModel = MapFileManager.loadMapFromFile(path);
                controller.loadMap(newModel);
                gamePanel.requestFocus();
            } catch (Exception e) {
                showError("Failed to load map. Please check the file path.");
            }
        });
    }

    private void showError(String message) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }


    private String formatTime(long millis) {
        int seconds = (int) (millis / 1000);
        int minutes = seconds / 60;
        seconds %= 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    private void onTimeUp() {
        System.out.println("Time is up! Game over.");
        // 可以在这里调用失败逻辑或显示提示
    }

    public VBox getGameLayout() {
        VBox layout = new VBox(10, timeLabel);
        layout.setStyle("-fx-padding: 10;");
        return layout;
    }
}
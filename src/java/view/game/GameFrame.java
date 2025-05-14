package view.game;

import java.util.Optional;

import view.util.FrameUtil;

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
import javafx.scene.layout.HBox;

import model.GameSaveManager;
import model.log.LogModel;
import model.map.MapModel;
import controller.GameController;
import model.map.MapFileManager;
import model.timer.TimerManager;
import model.Direction;

import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;


public class GameFrame extends Application {
    private GameController controller;
    private Button restartBtn;
    private Button loadBtn;
    private Button undoBtn;
    private Button saveBtn;
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
        // 初始化模型和面板
        LogModel logModel = new LogModel();
        gamePanel = new GamePanel(mapModel);
        GameSaveManager gameSaveManager = new GameSaveManager();
        controller = new GameController(logModel, gamePanel, mapModel, timerManager, gameSaveManager);
        gamePanel.setController(controller);
        controller.setGameFrame(this); // 设置 GameFrame 的引用

        BorderPane root = new BorderPane(); // 修改为 BorderPane

        // 创建上下左右按钮
        Button upBtn = createButton("Up", e -> gamePanel.doMove(gamePanel.getSelectedBox().getRow(), gamePanel.getSelectedBox().getCol(), Direction.UP));
        Button downBtn = createButton("Down", e -> gamePanel.doMove(gamePanel.getSelectedBox().getRow(), gamePanel.getSelectedBox().getCol(), Direction.DOWN));
        Button leftBtn = createButton("Left", e -> gamePanel.doMove(gamePanel.getSelectedBox().getRow(), gamePanel.getSelectedBox().getCol(), Direction.LEFT));
        Button rightBtn = createButton("Right", e -> gamePanel.doMove(gamePanel.getSelectedBox().getRow(), gamePanel.getSelectedBox().getCol(), Direction.RIGHT));

        // 设置按钮布局
        VBox verticalButtons = new VBox(10, upBtn, downBtn);
        verticalButtons.setPadding(new Insets(10));

        HBox horizontalButtons = new HBox(10, leftBtn, rightBtn);
        horizontalButtons.setPadding(new Insets(10));


// 将按钮组合到一个容器中
        VBox buttonControl = new VBox(10, verticalButtons, horizontalButtons);
        buttonControl.setPadding(new Insets(20));
        buttonControl.setStyle("-fx-background-color: #e0e0e0;");




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

        saveBtn = FrameUtil.createButton(root, "Save", "file:resources/icons/save.png", 100, 50);
        saveBtn.setOnAction(e -> {
            try {
                String saveFilePath = "src/resources/archives/save/saveGame.json"; // 保存文件路径
                int steps = controller.getSteps(); // 获取当前步数
                long remainingTime = timerManager.getRemainingTime(); // 获取剩余时间
                GameSaveManager.saveGame(saveFilePath, mapModel, steps, remainingTime); // 调用保存方法
                showInfo("Game saved successfully!");
            } catch (Exception ex) {
                showError("Failed to save the game: " + ex.getMessage());
            }
        });
        saveBtn.setFocusTraversable(false);

        undoBtn = createButton("Undo", e -> controller.undo());
        undoBtn.setFocusTraversable(false);

        // 初始化计时器显示
        timeLabel = new Label("Time Left: " + formatTime(timerManager.getRemainingTime()));
        timeLabel.setStyle("-fx-font-size: 16; -fx-text-fill: black;");

        // 定时更新计时器
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            long remainingTime = timerManager.getRemainingTime();
            timeLabel.setText("Time Left: " + formatTime(remainingTime));
            if (remainingTime <= 0) {
                timeline.stop();
                onTimeUp(); // 触发时间到的逻辑
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        // 创建控制面板并添加所有组件
        VBox controlPanel = new VBox(20, stepLabel, restartBtn, loadBtn, saveBtn, undoBtn, timeLabel);
        controlPanel.setPadding(new Insets(20));
        controlPanel.setStyle("-fx-background-color: #f0f0f0;");

        // 布局

        root.setCenter(gamePanel);
        root.setRight(controlPanel);
        // 将按钮控制面板添加到主布局
        root.setBottom(buttonControl);

        // 动态调整宽度
        gamePanel.prefWidthProperty().bind(root.widthProperty().multiply(0.7));
        controlPanel.prefWidthProperty().bind(root.widthProperty().multiply(0.3));

        // 场景和舞台设置
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("2025 CS109 Project Demo");
        primaryStage.setScene(scene);
        primaryStage.show();

        // 初始焦点
        gamePanel.requestFocus();

        // 设置步数更新回调
        gamePanel.setStepUpdateCallback(steps -> stepLabel.setText("Steps: " + steps));

        // 设置快捷键 Ctrl+S
        saveBtn.getScene().getAccelerators().put(
                new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN),
                () -> saveBtn.fire() // 触发按钮的点击事件
        );

        // 设置快捷键 Ctrl+L
        loadBtn.getScene().getAccelerators().put(
                new KeyCodeCombination(KeyCode.L, KeyCombination.CONTROL_DOWN),
                () -> loadBtn.fire() // 触发按钮的点击事件
        );

// 设置快捷键 Ctrl+R
        restartBtn.getScene().getAccelerators().put(
                new KeyCodeCombination(KeyCode.R, KeyCombination.CONTROL_DOWN),
                () -> restartBtn.fire() // 触发按钮的点击事件
        );

// 设置快捷键 Ctrl+U
        undoBtn.getScene().getAccelerators().put(
                new KeyCodeCombination(KeyCode.U, KeyCombination.CONTROL_DOWN),
                () -> undoBtn.fire() // 触发按钮的点击事件
        );



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

    private void showInfo(String message) {
        //saveBtn的辅助信息
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
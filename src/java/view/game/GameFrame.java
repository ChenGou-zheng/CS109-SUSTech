package view.game;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.MapModel;

public class GameFrame extends Application {
    private GameController controller;
    private Button restartBtn;
    private Button loadBtn;
    private Label stepLabel;
    private GamePanel gamePanel;

    @Override
    public void start(Stage primaryStage) {
        // 初始化模型和面板
        MapModel mapModel = new MapModel(5, 5); // 示例，实际应从参数传入
        gamePanel = new GamePanel(mapModel);
        controller = new GameController(gamePanel, mapModel);
        gamePanel.setController(controller);

        // 创建UI组件
        stepLabel = new Label("Start");
        stepLabel.setStyle("-fx-font-size: 22; -fx-font-style: italic;");

        restartBtn = new Button("Restart");
        loadBtn = new Button("Load");

        // 在GameFrame中使游戏面板自适应窗口大小
        gamePanel.prefWidthProperty().bind(root.widthProperty().multiply(0.7));

        // 按钮事件处理
        restartBtn.setOnAction(e -> {
            controller.restartGame();
            gamePanel.requestFocus();
        });

        loadBtn.setOnAction(e -> {
            // 使用JavaFX的输入对话框
            String path = javax.swing.JOptionPane.showInputDialog("Input path:");
            System.out.println(path);
            gamePanel.requestFocus();
        });

        // 布局
        VBox controlPanel = new VBox(20, stepLabel, restartBtn, loadBtn);
        controlPanel.setPadding(new Insets(20));

        BorderPane root = new BorderPane();
        root.setCenter(gamePanel);
        root.setRight(controlPanel);

        // 场景和舞台设置
        Scene scene = new Scene(root, 800, 600);

        // 键盘事件处理
        scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case RIGHT -> gamePanel.doMove(Direction.RIGHT);
                case LEFT -> gamePanel.doMove(Direction.LEFT);
                case UP -> gamePanel.doMove(Direction.UP);
                case DOWN -> gamePanel.doMove(Direction.DOWN);
            }
        });

        primaryStage.setTitle("2025 CS109 Project Demo");
        primaryStage.setScene(scene);
        primaryStage.show();

        // 初始焦点
        gamePanel.requestFocus();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
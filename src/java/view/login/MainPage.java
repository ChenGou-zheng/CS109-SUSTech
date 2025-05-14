package view.login;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import view.selector.MapSelector;
import view.selector.SaveSelector;

public class MainPage extends Application {

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox(20); // 设置垂直间距
        root.setAlignment(Pos.CENTER); // 设置居中对齐

        // 创建按钮
        Button mapSelectorButton = new Button("打开地图选择器");
        Button saveSelectorButton = new Button("打开存档选择器");

        // 设置按钮点击事件
        mapSelectorButton.setOnAction(e -> {
            MapSelector mapSelector = new MapSelector();
            mapSelector.start(primaryStage); // 切换到 MapSelector
        });

        saveSelectorButton.setOnAction(e -> {
            SaveSelector saveSelector = new SaveSelector();
            saveSelector.start(primaryStage); // 切换到 SaveSelector
        });

        // 将按钮添加到布局
        root.getChildren().addAll(mapSelectorButton, saveSelectorButton);

        // 设置场景并显示
        Scene scene = new Scene(root, 400, 300);
        primaryStage.setTitle("主页面");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
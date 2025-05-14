package view.selector;

import org.json.JSONObject;
import org.json.JSONTokener;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapSelector extends Application {

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox(10);
        Accordion accordion = new Accordion();

        File mapFolder = new File("resources/maps");
        File[] mapFiles = mapFolder.listFiles((dir, name) -> name.endsWith(".json"));

        if (mapFiles != null) {
            List<TitledPane> panes = new ArrayList<>();

            for (File mapFile : mapFiles) {
                try (FileReader reader = new FileReader(mapFile)) {
                    // 使用 org.json 解析 JSON 文件
                    JSONObject mapData = new JSONObject(new JSONTokener(reader));
                    String mapName = mapData.optString("mapName", "未知地图");
                    String mapId = mapData.optString("mapId", "未知ID");

                    VBox content = new VBox(5);
                    content.getChildren().add(new Label("地图名称: " + mapName));
                    content.getChildren().add(new Label("地图ID: " + mapId));
                    TitledPane pane = new TitledPane(mapName, content);

                    pane.setOnMouseClicked(e -> handleMapSelection(mapName, mapId));

                    panes.add(pane);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            accordion.getPanes().addAll(panes);
        } else {
            root.getChildren().add(new Label("未找到任何地图文件！"));
        }

        root.getChildren().add(accordion);

        Scene scene = new Scene(root, 400, 600);
        primaryStage.setTitle("地图选择器");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void handleMapSelection(String mapName, String mapId) {
        System.out.println("选择了地图: " + mapName + " (ID: " + mapId + ")");
        // 在这里添加地图选择后的处理逻辑，例如加载地图或切换场景
    }

    public static void main(String[] args) {
        launch(args);
    }
}
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

public class SaveSelector extends Application {

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox(10);
        Accordion accordion = new Accordion();

        File saveFolder = new File("resources/saves");
        File[] saveFiles = saveFolder.listFiles((dir, name) -> name.endsWith(".json"));

        if (saveFiles != null) {
            List<TitledPane> panes = new ArrayList<>();

            for (File saveFile : saveFiles) {
                try (FileReader reader = new FileReader(saveFile)) {
                    // 使用 org.json 解析 JSON 文件
                    JSONObject saveData = new JSONObject(new JSONTokener(reader));
                    String saveName = saveData.optString("saveName", "未知存档");
                    String saveTime = saveData.optString("saveTime", "未知时间");

                    VBox content = new VBox(5);
                    content.getChildren().add(new Label("存档名称: " + saveName));
                    content.getChildren().add(new Label("存档时间: " + saveTime));
                    TitledPane pane = new TitledPane(saveName, content);

                    pane.setOnMouseClicked(e -> handleSaveSelection(saveFile));

                    panes.add(pane);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            accordion.getPanes().addAll(panes);
        } else {
            root.getChildren().add(new Label("未找到任何存档文件！"));
        }

        root.getChildren().add(accordion);

        Scene scene = new Scene(root, 400, 600);
        primaryStage.setTitle("存档选择器");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void handleSaveSelection(File saveFile) {
        try (FileReader reader = new FileReader(saveFile)) {
            JSONObject saveData = new JSONObject(new JSONTokener(reader));

            // 示例：加载存档数据
            String saveName = saveData.optString("saveName", "未知存档");
            String saveTime = saveData.optString("saveTime", "未知时间");
            System.out.println("加载存档: " + saveName + " (保存时间: " + saveTime + ")");

            // 在这里添加具体的存档加载逻辑，例如更新游戏状态
            // GameStateManager.load(saveData);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
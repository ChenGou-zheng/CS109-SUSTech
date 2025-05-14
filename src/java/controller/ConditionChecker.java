package controller;

import model.map.MapModel;
import model.timer.TimerManager;
import view.game.BoxComponent;
import javafx.animation.FadeTransition;
import javafx.util.Duration;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.Label;
import javafx.animation.ScaleTransition;
import javafx.animation.RotateTransition;
import javafx.animation.FillTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.SequentialTransition;
import view.game.GamePanel;


public class ConditionChecker {
    private final MapModel model;

    public ConditionChecker(MapModel model) {
        this.model = model;
    }
    //todo:show winning message,后期可以加入一些美观的样式。
    public boolean checkWinCondition() {
        int[][] matrix = model.getMatrix();
        int height = model.getHeight();
        int width = model.getWidth();

        //todo:以后这里根据mapType改变判断条件？
        // 示例：检查2×2方块即曹操是否在目标位置，这里的height和width应该是后来设置的判断条件，根据modeType决定。
        return matrix[2][1] == 4;

    }
    public boolean checkLoseCondition(int steps, TimerManager timerManager) {
        int[][] matrix = model.getMatrix();
        int height = model.getHeight();
        int width = model.getWidth();

        //todo:以后这里根据mapType改变判断条件？
        // 示例：检查2×2方块即曹操是否在目标位置
        if (steps > 20) {
            return true;
        }
        if (timerManager.isTimedOut()) {
            return true;
        }
        return false;
    }
    public void showWinMessage() {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setTitle("Victory");
        alert.setHeaderText(null);
        alert.setContentText("Congratulations! You have won the game!");
        alert.showAndWait();
    }
    public void showLoseMessage() {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
        alert.setTitle("Game Over");
        alert.setHeaderText(null);
        alert.setContentText("You have lost the game. Better luck next time!");
        alert.showAndWait();
    }
    public void playWinAnimation(GamePanel view) {
        // 淡出所有方块
        for (BoxComponent box : view.getBoxes()) {
            FadeTransition fadeOut = new FadeTransition(Duration.millis(500), box);
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);
            fadeOut.play();
        }

        // 显示胜利提示
        Label winLabel = new Label("You Win!");
        winLabel.setStyle("-fx-font-size: 36px; -fx-text-fill: green;");
        winLabel.setLayoutX(view.getWidth() / 2 - 100);
        winLabel.setLayoutY(view.getHeight() / 2 - 50);
        view.getChildren().add(winLabel);

        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(500), winLabel);
        scaleTransition.setFromX(0.5);
        scaleTransition.setFromY(0.5);
        scaleTransition.setToX(1.5);
        scaleTransition.setToY(1.5);
        scaleTransition.setCycleCount(2);
        scaleTransition.setAutoReverse(true);
        scaleTransition.play();
    }

    public void playLoseAnimation(GamePanel view) {
        // 方块变红、缩小并旋转
        for (BoxComponent box : view.getBoxes()) {
            // 颜色渐变
            Rectangle rectangle = box.getRectangle(); // 假设 BoxComponent 提供了 getRectangle() 方法
            FillTransition fillTransition = new FillTransition(Duration.millis(500), rectangle);
            fillTransition.setToValue(Color.RED);

            fillTransition.setToValue(Color.RED);

            // 缩小动画
            ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(500), box);
            scaleTransition.setToX(0.5);
            scaleTransition.setToY(0.5);

            // 旋转动画
            RotateTransition rotateTransition = new RotateTransition(Duration.millis(500), box);
            rotateTransition.setByAngle(360);

            // 动画组合
            ParallelTransition parallelTransition = new ParallelTransition(fillTransition, scaleTransition, rotateTransition);
            parallelTransition.play();
        }

        // 显示失败提示
        Label loseLabel = new Label("Game Over");
        loseLabel.setStyle("-fx-font-size: 36px; -fx-text-fill: red;");
        loseLabel.setLayoutX(view.getWidth() / 2 - 100);
        loseLabel.setLayoutY(view.getHeight() / 2 - 50);
        view.getChildren().add(loseLabel);

        // 提示文字的缩放和淡入动画
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(500), loseLabel);
        scaleTransition.setFromX(0.5);
        scaleTransition.setFromY(0.5);
        scaleTransition.setToX(1.5);
        scaleTransition.setToY(1.5);

        FadeTransition fadeTransition = new FadeTransition(Duration.millis(500), loseLabel);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);

        // 动画序列
        SequentialTransition sequentialTransition = new SequentialTransition(scaleTransition, fadeTransition);
        sequentialTransition.play();
    }

}
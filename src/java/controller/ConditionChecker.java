package controller;

import model.MapModel;

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
        int[] position1 = model.getTargetPosition()[0];
        int[] position2 = model.getTargetPosition()[1];

        //todo:以后这里根据mapType改变判断条件？
        // 示例：检查2×2方块即曹操是否在目标位置，这里的height和width应该是后来设置的判断条件，根据modeType决定。
        if (height >= 3 && width >= 5) {
            return matrix[position1[0]][position1[1]] == 4 &&
                    matrix[position2[0]][position2[1]] == 4;
        }
        return false;
    }
    public boolean checkLoseCondition() {
        int[][] matrix = model.getMatrix();
        int height = model.getHeight();
        int width = model.getWidth();
        int[] position1 = model.getTargetPosition()[0];
        int[] position2 = model.getTargetPosition()[1];

        //todo:以后这里根据mapType改变判断条件？
        // 示例：检查2×2方块即曹操是否在目标位置
        if (height >= 3 && width >= 5) {
            return matrix[position1[0]][position1[1]] == 4 &&
                    matrix[position2[0]][position2[1]] == 4;
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
}
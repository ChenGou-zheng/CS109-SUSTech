package controller;

import model.MapModel;

public class WinConditionChecker {
    private final MapModel model;

    public WinConditionChecker(MapModel model) {
        this.model = model;
    }

    //todo:show winning message
    public boolean checkWinCondition() {
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

}
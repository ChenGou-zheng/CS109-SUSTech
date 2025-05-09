package controller;

import model.Direction;
import model.MapModel;
import view.game.BoxComponent;
import view.game.GamePanel;
import javax.swing.*;

/**
 * It is a bridge to combine GamePanel(view) and MapMatrix(model) in one game.
 * You can design several methods about the game logic in this class.
 */
public class GameController {
    private final GamePanel view;
    private final MapModel model;

    public GameController(GamePanel view, MapModel model) {
        this.view = view;
        this.model = model;
        view.setController(this);
    }

    public void restartGame() {
        /* 重新加载初始地图
        int[][] originalMatrix = {
                {1, 2, 2, 1, 1},
                {3, 4, 4, 2, 2},
                {3, 4, 4, 1, 0},
                {1, 2, 2, 1, 0},
                {1, 1, 1, 1, 1}
        };
        this.model = new MapModel(originalMatrix);*/
        this.view.resetGame(this.model);
        this.view.setSteps(0);
        this.view.setSelectedBox(null); // 清除选中状态
    }

    public boolean doMove(int row, int col, Direction direction) {
        int blockId = model.getId(row, col);
        if (blockId == 0) return false; // 空白格不能移动

        // 检查是否可以移动
        if (!canMove(row, col, direction, blockId)) {
            return false;
        }

        // 执行移动
        moveBlock(row, col, direction, blockId);

        // 更新UI
        updateBoxPositions();
        return true;
    }

    private boolean canMove(int row, int col, Direction dir, int blockId) {
        switch (blockId) {
            case 1: // 1×1方块
                return checkSingleMove(row, col, dir);
            case 2: // 1×2方块
                return checkHorizontalMove(row, col, dir);
            case 3: // 2×1方块
                return checkVerticalMove(row, col, dir);
            case 4: // 2×2方块
                return checkBigBlockMove(row, col, dir);
            default:
                return false;
        }
    }

    private boolean checkSingleMove(int row, int col, Direction dir) {
        int newRow = row + dir.getRow();
        int newCol = col + dir.getCol();
        return model.checkInHeightSize(newRow) &&
                model.checkInWidthSize(newCol) &&
                model.getId(newRow, newCol) == 0;
    }

    private boolean checkHorizontalMove(int row, int col, Direction dir) {
        if (dir == Direction.UP || dir == Direction.DOWN) {
            // 垂直移动需要检查两列
            int newRow = row + dir.getRow();
            return model.checkInHeightSize(newRow) &&
                    model.getId(newRow, col) == 0 &&
                    model.getId(newRow, col + 1) == 0;
        } else {
            // 水平移动
            int newCol = col + (dir == Direction.LEFT ? -1 : 2);
            return model.checkInWidthSize(newCol) &&
                    model.getId(row, newCol) == 0;
        }
    }

    private boolean checkVerticalMove(int row, int col, Direction dir) {
        if (dir == Direction.LEFT || dir == Direction.RIGHT) {
            // 水平移动需要检查两行
            int newCol = col + dir.getCol();
            return model.checkInWidthSize(newCol) &&
                    model.getId(row, newCol) == 0 &&
                    model.getId(row + 1, newCol) == 0;
        } else {
            // 垂直移动
            int newRow = row + (dir == Direction.UP ? -1 : 2);
            return model.checkInHeightSize(newRow) &&
                    model.getId(newRow, col) == 0;
        }
    }

    private boolean checkBigBlockMove(int row, int col, Direction dir) {
        int newRow = row + dir.getRow();
        int newCol = col + dir.getCol();

        // 检查四个角是否可以移动
        boolean corner1 = model.checkInHeightSize(newRow) &&
                model.checkInWidthSize(newCol) &&
                model.getId(newRow, newCol) == 0;

        boolean corner2 = model.checkInHeightSize(newRow) &&
                model.checkInWidthSize(newCol + 1) &&
                model.getId(newRow, newCol + 1) == 0;

        boolean corner3 = model.checkInHeightSize(newRow + 1) &&
                model.checkInWidthSize(newCol) &&
                model.getId(newRow + 1, newCol) == 0;

        boolean corner4 = model.checkInHeightSize(newRow + 1) &&
                model.checkInWidthSize(newCol + 1) &&
                model.getId(newRow + 1, newCol + 1) == 0;

        return corner1 && corner2 && corner3 && corner4;
    }

    private void moveBlock(int row, int col, Direction dir, int blockId) {
        // 清除原位置
        clearBlock(row, col, blockId);

        // 设置新位置
        int newRow = row + dir.getRow();
        int newCol = col + dir.getCol();
        setBlock(newRow, newCol, blockId);
    }

    private void clearBlock(int row, int col, int blockId) {
        model.getMatrix()[row][col] = 0;
        switch (blockId) {
            case 2:
                model.getMatrix()[row][col + 1] = 0;
                break;
            case 3:
                model.getMatrix()[row + 1][col] = 0;
                break;
            case 4:
                model.getMatrix()[row][col + 1] = 0;
                model.getMatrix()[row + 1][col] = 0;
                model.getMatrix()[row + 1][col + 1] = 0;
                break;
        }
    }

    private void setBlock(int row, int col, int blockId) {
        model.getMatrix()[row][col] = blockId;
        switch (blockId) {
            case 2:
                model.getMatrix()[row][col + 1] = blockId;
                break;
            case 3:
                model.getMatrix()[row + 1][col] = blockId;
                break;
            case 4:
                model.getMatrix()[row][col + 1] = blockId;
                model.getMatrix()[row + 1][col] = blockId;
                model.getMatrix()[row + 1][col + 1] = blockId;
                break;
        }
    }

    private void updateBoxPositions() {
        // 保存当前选中方块的位置
        BoxComponent selected = view.getSelectedBox();
        int selectedRow = -1, selectedCol = -1;
        if (selected != null) {
            selectedRow = selected.getRow();
            selectedCol = selected.getCol();
        }

        // 重置所有方块位置
        view.removeAllBoxes();
        view.initialGame();

        // 恢复选中状态
        if (selectedRow != -1 && selectedCol != -1) {
            for (BoxComponent box : view.getBoxes()) {
                if (box.getRow() == selectedRow && box.getCol() == selectedCol) {
                    view.setSelectedBox(box);
                    break;
                }
            }
        }

        // 检查游戏是否胜利
        checkWinCondition();
    }

    private void checkWinCondition() {
        // 假设胜利条件是2×2方块(曹操)移动到最下方中间位置
        int[][] matrix = model.getMatrix();
        int height = model.getHeight();
        int width = model.getWidth();

        // 检查2×2方块是否在目标位置(示例: 最下方中间)
        if (height >= 3 && width >= 5) {
            if (matrix[height-2][width/2-1] == 4 &&
                    matrix[height-2][width/2] == 4 &&
                    matrix[height-1][width/2-1] == 4 &&
                    matrix[height-1][width/2] == 4) {
                showWinMessage();
            }
        }
    }

    private void showWinMessage() {
        JOptionPane.showMessageDialog(view,
                "恭喜你赢了！\n步数: " + view.getSteps(),
                "游戏胜利",
                JOptionPane.INFORMATION_MESSAGE);

        // 重置游戏
        restartGame();
    }






    //todo: add other methods such as loadGame, saveGame...






}

package controller;

import model.Direction;
import model.MapModel;

public class MoveHandler {
    private final MapModel model;

    public MoveHandler(MapModel model) {
        this.model = model;
    }

    public boolean canMove(int row, int col, Direction dir, int blockId) {
        // 移动逻辑拆分到这里
        return switch (blockId) {
            case 1 -> checkSingleMove(row, col, dir);
            case 2 -> checkHorizontalMove(row, col, dir);
            case 3 -> checkVerticalMove(row, col, dir);
            case 4 -> checkBigBlockMove(row, col, dir);
            default -> false;
        };
    }

    public void moveBlock(int row, int col, Direction dir, int blockId) {
        // 清除原位置
        clearBlock(row, col, blockId);

        // 设置新位置
        int newRow = row + dir.getRow();
        int newCol = col + dir.getCol();
        setBlock(newRow, newCol, blockId);
    }

//具体移动逻辑书语属于私有方法，这里针对不同块移动的check
    private boolean checkSingleMove(int row, int col, Direction dir) {
        int newRow = row + dir.getRow();
        int newCol = col + dir.getCol();
        return model.getId(newRow, newCol) == 0 &&
            model.checkInHeightSize(newRow) &&
            model.checkInWidthSize(newCol);
    }

    private boolean checkHorizontalMove(int row, int col, Direction dir) {
        int newRow = row + dir.getRow();
        int newCol = col + dir.getCol();
        if (newCol < 0 || newCol >= model.getWidth()) {
            return false; // 超出边界，不能移动
        }
        if (dir == Direction.UP || dir == Direction.DOWN) {
            // 垂直移动时检查两列
            return model.checkInHeightSize(newRow) &&
                    model.getId(newRow, col) == 0 &&
                    model.getId(newRow, col + 1) == 0;
        } else {
            // 水平移动时检查新列
            return model.checkInWidthSize(newCol) &&
                    model.getId(row, newCol) == 0 &&
                    model.getId(row, newCol - 1) == 0;
        }
    }

    private boolean checkVerticalMove(int row, int col, Direction dir) {
        int newCol = col + dir.getCol();
        int newRow = row + dir.getRow();
        if (newRow < 0 || newRow >= model.getHeight()) {
            return false; // 超出边界，不能移动
        }
        if (dir == Direction.LEFT || dir == Direction.RIGHT) {
            // 水平移动时检查两行
            return model.checkInWidthSize(newCol) &&
                    model.getId(row, newCol) == 0 &&
                    model.getId(row + 1, newCol) == 0;
        } else {
            // 垂直移动时检查新行

            return model.checkInHeightSize(newRow) &&
                    model.getId(newRow, col) == 0 &&
                    model.getId(newRow - 1, col) == 0;
        }
    }

    private boolean checkBigBlockMove(int row, int col, Direction dir) {
        int newRow = row + dir.getRow();
        int newCol = col + dir.getCol();

        // 检查四个角是否可以移动
        return model.checkInHeightSize(newRow + 1) &&
                model.checkInWidthSize(newCol + 1) &&
                model.getId(newRow, newCol) == 0 &&
                model.getId(newRow, newCol + 1) == 0 &&
                model.getId(newRow + 1, newCol) == 0 &&
                model.getId(newRow + 1, newCol + 1) == 0;
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


}
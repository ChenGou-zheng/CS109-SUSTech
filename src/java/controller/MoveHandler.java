package controller;

import model.Direction;
import model.map.MapModel;

public class MoveHandler {
    private final MapModel mapModel;

    public MoveHandler(MapModel mapModel) {
        this.mapModel = mapModel;
    }

    public void moveBlock(int row, int col, Direction dir, int blockId) {
        //中间寄存器，底层集成
        int newRow = row + dir.getRow();
        int newCol = col + dir.getCol();
        clearBlockAfterMove(row, col, blockId);
        setBlock(newRow, newCol, blockId);

    }

    //感觉是检查到自己身上来了。所以必须逐个定义，先查范围后查可行。
    //这里newRow和row交替用实在是没有必要。

    public boolean canMove(int row, int col, Direction dir, int blockId) {
        //接口方法统领分支判断流程
        return switch (blockId) {
            case 1 -> checkSingleMove(row, col, dir);
            case 2 -> checkHorizontalMove(row, col, dir);
            case 3 -> checkVerticalMove(row, col, dir);
            case 4 -> checkBigBlockMove(row, col, dir);
            default -> false;
        };
    }
    private boolean checkSingleMove(int row, int col, Direction dir) {
        int newRow = row + dir.getRow();
        int newCol = col + dir.getCol();
        return mapModel.checkInHeightSize(newRow) &&
            mapModel.checkInWidthSize(newCol) &&
            mapModel.getId(newRow, newCol) == 0;
    }
    private boolean checkHorizontalMove(int row, int col, Direction dir) {
        int newRow = row + dir.getRow();
        int newCol = col + dir.getCol();

        switch (dir) {
            case UP, DOWN -> {
                // 垂直移动时检查两列
                return mapModel.checkInHeightSize(newRow) &&
                        mapModel.getId(newRow, col) == 0 &&
                        mapModel.getId(newRow, col + 1) == 0;
            }
            case LEFT -> {
                return mapModel.checkInWidthSize(newCol) &&
                        mapModel.getId(row, newCol) == 0;
            }
            case RIGHT -> {
                return mapModel.checkInWidthSize(newCol + 1) &&
                        mapModel.getId(row, newCol + 1) == 0;
            }
            default -> {
                return false; // 默认情况，方向无效
            }
        }
    }
    private boolean checkVerticalMove(int row, int col, Direction dir) {
        int newCol = col + dir.getCol();
        int newRow = row + dir.getRow();

        switch (dir) {
            case LEFT, RIGHT -> {
                // 水平移动时检查两行
                return mapModel.checkInWidthSize(newCol) &&
                        mapModel.getId(row, newCol) == 0 &&
                        mapModel.getId(row + 1, newCol) == 0;
            }
            case UP -> {
                // 垂直移动时检查新行
                return mapModel.checkInHeightSize(newRow) &&
                        mapModel.getId(newRow, col) == 0;
            }
            case DOWN -> {
                return mapModel.checkInHeightSize(newRow + 1) &&
                        mapModel.getId(newRow + 1, col) == 0;
            }
            default -> {
                return false; // 默认情况，方向无效
            }
        }
    }
    private boolean checkBigBlockMove(int row, int col, Direction dir) {
        int newRow = row + dir.getRow();
        int newCol = col + dir.getCol();

        switch (dir) {
            case UP -> {
               return mapModel.checkInHeightSize(newRow) &&
                       mapModel.getId(newRow, col) == 0 &&
                       mapModel.getId(newRow, col + 1) == 0;
                // 垂直移动时检查两行
            }
            case DOWN -> {
                return mapModel.checkInHeightSize(newRow + 1) &&
                        mapModel.getId(newRow + 1, col) == 0 &&
                        mapModel.getId(newRow + 1, col + 1) == 0;
            }
            case LEFT -> {
                return mapModel.checkInWidthSize(newCol) &&
                        mapModel.getId(row, newCol) == 0 &&
                        mapModel.getId(row + 1, newCol) == 0;
            }
            case RIGHT -> {
                return mapModel.checkInWidthSize(newCol + 1) &&
                        mapModel.getId(row, newCol + 1) == 0 &&
                        mapModel.getId(row + 1, newCol + 1) == 0;
            }
            default -> {
                return false; // 默认情况，方向无效
            }
        }
    }

    private void clearBlockAfterMove(int row, int col, int blockId) {
        mapModel.setMatrix(row, col, 0);
        switch (blockId) {
            case 2:
                mapModel.setMatrix(row, col + 1, 0);
                break;
            case 3:
                mapModel.setMatrix(row + 1, col, 0);
                break;
            case 4:
                mapModel.setMatrix(row, col + 1, 0);
                mapModel.setMatrix(row + 1, col, 0);
                mapModel.setMatrix(row + 1, col + 1, 0);
                break;
        }
    }
    private void setBlock(int row, int col, int blockId) {
        //注意set新位置
        mapModel.setMatrix(row, col, blockId);
        switch (blockId) {
            case 2:
                mapModel.setMatrix(row, col + 1, blockId);
                break;
            case 3:
                mapModel.setMatrix(row + 1, col, blockId);
                break;
            case 4:
                mapModel.setMatrix(row, col + 1, blockId);
                mapModel.setMatrix(row + 1, col, blockId);
                mapModel.setMatrix(row + 1, col + 1, blockId);
                break;
        }
    }

    //todo:后期考虑interface抽象然后写特殊模式的运动规则与限制。代理工厂，策略模式
    //todo:什么是硬编码
}
package view.game;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.Label;
import model.Direction;
import model.MapModel;
import controller.GameController;


import java.util.ArrayList;
import java.util.List;

public class GamePanel extends Pane {
    private List<BoxComponent> boxes;
    private MapModel mapModel;
    private GameController controller;
    private int steps;
    private final int GRID_SIZE = 50;
    private BoxComponent selectedBox;
    private Label stepLabel;
    private Circle mouseShadow;//鼠标悬停阴影, 用于强化标记鼠标

    public GamePanel(MapModel mapModel) {
        boxes = new ArrayList<>();
        this.mapModel = mapModel;
        this.selectedBox = null;
        this.setClip(null);

        // 初始化鼠标阴影
        mouseShadow = new Circle(10, Color.GRAY);
        mouseShadow.setOpacity(0.5); // 设置半透明
        mouseShadow.setVisible(false); // 初始隐藏
        this.getChildren().add(mouseShadow);

        // 添加鼠标移动事件
        this.setOnMouseMoved(e -> {
            mouseShadow.setCenterX(e.getX());
            mouseShadow.setCenterY(e.getY());
            mouseShadow.setVisible(true);
        });

        // 鼠标离开时隐藏阴影
        this.setOnMouseExited(e -> mouseShadow.setVisible(false));

        // 设置背景和边框
        Rectangle background = new Rectangle(
                mapModel.getWidth() * GRID_SIZE + 4,
                mapModel.getHeight() * GRID_SIZE + 4,
                Color.LIGHTGRAY
        );
        background.setStroke(Color.DARKGRAY);
        background.setStrokeWidth(2);
        this.getChildren().add(background);

        // 初始化步数标签
        stepLabel = new Label("Step: 0");
        stepLabel.setLayoutX(10);
        stepLabel.setLayoutY(mapModel.getHeight() * GRID_SIZE + 10);
        this.getChildren().add(stepLabel);

        initialGame();

// 添加键盘事件处理，使面板可以接收键盘输入
        this.setFocusTraversable(true);
        this.setOnKeyPressed(e -> {
            if (selectedBox != null) {
                int row = selectedBox.getRow();
                int col = selectedBox.getCol();
                switch (e.getCode()) {
                    case RIGHT -> doMove(row, col, Direction.RIGHT);
                    case LEFT -> doMove(row, col, Direction.LEFT);
                    case UP -> doMove(row, col, Direction.UP);
                    case DOWN -> doMove(row, col, Direction.DOWN);
                }
            }
        });
    }

    public int getGRID_SIZE() {return GRID_SIZE;}
    public void setController(GameController controller) {this.controller = controller;}
    public List<BoxComponent> getBoxes() {return boxes;}
    public int getSteps() {return steps;}
    public void setSteps(int steps) {
        this.steps = steps;
        updateStepLabel();//todo：这里写这个合适吗？update更新是不是应该在controller里？setter and getter 是不是应该只提供 fields
    }
    public BoxComponent getSelectedBox() {
        return selectedBox;
    }

    public BoxComponent getBoxAt(int row, int col) {
        for (BoxComponent box : boxes) {
            if (box.getRow() == row && box.getCol() == col) {
                return box;
            }
        }
        return null;//todo：恢复连续选中？！
    }

    public void initialGame() {
        // 重置步数并更新步数标签
        //为什么要重置步数？导致步数一直为1
//        this.steps = 0;
        updateStepLabel();

        // 清空现有的 BoxComponent
        boxes.clear();
        //清空子节点里所有的boxcomponent
        this.getChildren().removeIf(node -> node instanceof BoxComponent);

        // 获取地图数据
        int[][] map = mapModel.getMatrix();

        // 遍历地图并创建 BoxComponent
        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[row].length; col++) {
                int id = map[row][col];
                BoxComponent box = createBoxComponent(id, row, col, map);
                if (box != null) {
                    box.setLayoutX(col * GRID_SIZE + 2);
                    box.setLayoutY(row * GRID_SIZE + 2);
                    boxes.add(box);
                    this.getChildren().add(box);

                    // 添加鼠标点击事件，使得每个棋子可以被点击
                    box.setOnMouseClicked(e -> handleBoxClick(box));
                }
            }
        }
    }
//todo:应该就是这里让block消失,一时间不知道打墙会不会是一种好方法

    private BoxComponent createBoxComponent(int id, int row, int col, int[][] map) {
        BoxComponent box = null;//创建新的box并返回
        switch (id) {
            case 1:
                box = new BoxComponent(Color.ORANGE, row, col, GRID_SIZE, GRID_SIZE);
                break;
            case 2:
                if (col + 1 < map[row].length) { // 检查右侧格子是否越界
                    box = new BoxComponent(Color.PINK, row, col, GRID_SIZE * 2, GRID_SIZE);
                    map[row][col + 1] = -1; // 占用右侧格子
                }
                break;
            case 3:
                if (row + 1 < map.length) { // 检查下方格子是否越界
                    box = new BoxComponent(Color.BLUE, row, col, GRID_SIZE, GRID_SIZE * 2);
                    map[row + 1][col] = -1; // 占用下方格子
                }
                break;
            case 4:
                if (row + 1 < map.length && col + 1 < map[row].length) { // 检查右下角格子是否越界
                    box = new BoxComponent(Color.GREEN, row, col, GRID_SIZE * 2, GRID_SIZE * 2);
                    map[row][col + 1] = -1; // 占用右侧格子
                    map[row + 1][col] = -1; // 占用下方格子
                    map[row + 1][col + 1] = -1; // 占用右下角格子
                }
                break;
            case 0:
                break;//空地方无块
            case -1:
                break;//被覆盖已占用
        }
        if (box != null) {
            System.out.printf("%d taken: %s%n", id, box);
        }else {
            System.out.printf("Failed: id=%d, row=%d, col=%d%n", id, row, col);
        }
        return box;
    }





    private void handleBoxClick(BoxComponent clickedComponent) {
        if (selectedBox == null) {
            selectedBox = clickedComponent;
            selectedBox.setSelected(true);
        } else if (selectedBox != clickedComponent) {
            selectedBox.setSelected(false);
            clickedComponent.setSelected(true);
            selectedBox = clickedComponent;
        } else {
            clickedComponent.setSelected(false);
            selectedBox = null;
        }
    }


    public boolean doMove(int row, int col, Direction direction) {
        System.out.printf("Attempting move: row=%d, col=%d, direction=%s%n", row, col, direction);
        if (controller != null && controller.doMove(row, col, direction)) {
            afterMove(); // 更新全局步数
            System.out.println("Move successful");
            return true;
        }
        System.out.println("Move failed");
        return false;
    }
    public void afterMove() {
        steps++;
        updateStepLabel(); // 更新步数标签
    }

    private void updateStepLabel() {
        stepLabel.setText(String.format("Step: %d", steps));
    }

    public void removeAllBoxes() {
        this.getChildren().removeAll(boxes);
        boxes.clear();
    }

    public void resetGame(MapModel newmapModel) {
        this.mapModel = newmapModel;
        this.selectedBox = null;
        initialGame();
    }

    public void setSelectedBox(BoxComponent box) {
        if (this.selectedBox != null) {
            this.selectedBox.setSelected(false); // 取消之前选中的方块
        }
        this.selectedBox = box;
        if (this.selectedBox != null) {
            this.selectedBox.setSelected(true); // 设置新选中的方块
        }
    }



}
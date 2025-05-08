package view.game;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.Direction;
import model.MapModel;

import java.util.ArrayList;
import java.util.List;

public class GamePanel extends Pane {
    private List<BoxComponent> boxes;
    private MapModel model;
    private GameController controller;
    private int steps;
    private final int GRID_SIZE = 50;
    private BoxComponent selectedBox;

    public GamePanel(MapModel model) {
        boxes = new ArrayList<>();
        this.model = model;
        this.selectedBox = null;

        // 设置背景和边框
        Rectangle background = new Rectangle(
                model.getWidth() * GRID_SIZE + 4,
                model.getHeight() * GRID_SIZE + 4,
                Color.LIGHTGRAY
        );
        background.setStroke(Color.DARKGRAY);
        background.setStrokeWidth(2);
        this.getChildren().add(background);

        initialGame();
    }

    public void initialGame() {
        this.steps = 0;
        boxes.clear();
        this.getChildren().removeIf(node -> node instanceof BoxComponent);

        // 复制地图数据
        int[][] map = new int[model.getHeight()][model.getWidth()];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                map[i][j] = model.getId(i, j);
            }
        }

        // 创建BoxComponent
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] == 0) continue;

                BoxComponent box = null;
                if (map[i][j] == 1) {
                    box = new BoxComponent(Color.ORANGE, i, j, GRID_SIZE, GRID_SIZE);
                } else if (map[i][j] == 2) {
                    box = new BoxComponent(Color.PINK, i, j, GRID_SIZE * 2, GRID_SIZE);
                    map[i][j + 1] = 0;
                } else if (map[i][j] == 3) {
                    box = new BoxComponent(Color.BLUE, i, j, GRID_SIZE, GRID_SIZE * 2);
                    map[i + 1][j] = 0;
                } else if (map[i][j] == 4) {
                    box = new BoxComponent(Color.GREEN, i, j, GRID_SIZE * 2, GRID_SIZE * 2);
                    map[i][j + 1] = 0;
                    map[i + 1][j] = 0;
                    map[i + 1][j + 1] = 0;
                }

                if (box != null) {
                    box.setLayoutX(j * GRID_SIZE + 2);
                    box.setLayoutY(i * GRID_SIZE + 2);
                    boxes.add(box);
                    this.getChildren().add(box);
                    map[i][j] = 0;

                    // 添加鼠标点击事件
                    box.setOnMouseClicked(e -> handleBoxClick(box));
                }
            }
        }
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

    // 移动方法（键盘事件将在GameFrame中处理）
    public void doMove(Direction direction) {
        if (selectedBox != null) {
            if (controller.doMove(selectedBox.getRow(), selectedBox.getCol(), direction)) {
                afterMove();
            }
        }
    }

    public void afterMove() {
        this.steps++;
        // 更新stepLabel的逻辑将在GameFrame中处理
    }

    // 保留其他必要的方法...
}
    public void setStepLabel(JLabel stepLabel) {
        this.stepLabel = stepLabel;
    }


    public void setController(GameController controller) {
        this.controller = controller;
    }

    public BoxComponent getSelectedBox() {
        return selectedBox;
    }

    public int getGRID_SIZE() {
        return GRID_SIZE;
    }

    public void removeAllBoxes() {
        for (BoxComponent box : boxes) {
            this.remove(box);
        }
        boxes.clear();
    }

    public List<BoxComponent> getBoxes() {
        return boxes;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
        this.stepLabel.setText(String.format("Step: %d", this.steps));
    }

    public void resetGame(MapModel newModel) {
        this.model = newModel;
        this.removeAllBoxes();
        initialGame();
        this.selectedBox = null;
        this.repaint();
    }

    public void setSelectedBox(BoxComponent box) {
        if (this.selectedBox != null) {
            this.selectedBox.setSelected(false);
        }
        this.selectedBox = box;
        if (box != null) {
            box.setSelected(true);
        }
    }

    public BoxComponent getBoxAt(int row, int col) {
        for (BoxComponent box : boxes) {
            if (box.getRow() == row && box.getCol() == col) {
                return box;
            }
        }
        return null;
    }














}

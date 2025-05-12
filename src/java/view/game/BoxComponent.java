package view.game;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.effect.DropShadow;
import javafx.animation.Animation;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.util.Duration;
import javafx.animation.TranslateTransition;

public class BoxComponent extends StackPane {
    private Color color;
    private int row;
    private int col;
    private boolean isSelected;
    private Rectangle rectangle;
    private DropShadow shadowEffect;

    public BoxComponent(Color color, int row, int col, double width, double height) {
        this.color = color;
        this.row = row;
        this.col = col;
        this.isSelected = false;

        rectangle = new Rectangle(width, height, color);
        rectangle.setStroke(Color.DARKGRAY);
        rectangle.setStrokeWidth(1);
        // 设置圆角矩形, 显示清晰轮廓

        this.getChildren().add(rectangle);
        // 将新创建的BoxComponent添加到StackPane中,即成为子节点
        this.setPrefSize(width, height);
        //设置 prefferSize,用于Box默认显示大小

        // 添加鼠标悬停效果
        this.setOnMouseEntered(e -> rectangle.setStroke(Color.LIGHTBLUE)); // 鼠标进入时改变边框颜色
        this.setOnMouseExited(e -> {
            if (!isSelected) {
                rectangle.setStroke(Color.DARKGRAY); // 鼠标离开时恢复默认边框颜色
            }
        });
        //todo:使用css文件定义悬停效果
        //getStyleClass().add()通过css文件定义悬停效果.

        // 初始化阴影效果
        shadowEffect = new DropShadow();
        shadowEffect.setColor(Color.GRAY);
        shadowEffect.setRadius(10);

        // 鼠标悬停时添加阴影效果
        //todo:原来是这两种二选一吗?
        this.setOnMouseEntered(e -> this.setEffect(shadowEffect));
        this.setOnMouseExited(e -> this.setEffect(null));

    }

    public void setSelected(boolean isSelected) {
        if (isSelected) {
            rectangle.setStroke(Color.RED);
            rectangle.setStrokeWidth(3); // 边框宽度
            rectangle.setArcWidth(10); // 圆角宽度
            rectangle.setArcHeight(10); // 圆角高度
        } else {
            rectangle.setStroke(Color.DARKGRAY);
            rectangle.setStrokeWidth(1);
            rectangle.setArcWidth(5);
            rectangle.setArcHeight(5);
        }
    }

    // todo:改造这个方法和gamecontroller里面的domove保持一致,或者整体简化.
    public void animateMove(int targetRow, int targetCol, double gridSize) {
        double newX = targetCol * gridSize + 2; // 根据列计算目标X坐标
        double newY = targetRow * gridSize + 2; // 根据行计算目标Y坐标

        TranslateTransition transition = new TranslateTransition(Duration.millis(200), this);
        transition.setToX(newX - this.getLayoutX());
        transition.setToY(newY - this.getLayoutY());
        transition.play();
    }


    // 保留原有的getter/setter方法
    public int getRow() { return row; }
    public void setRow(int row) { this.row = row; }
    public int getCol() { return col; }
    public void setCol(int col) { this.col = col; }
}

//todo:等改完bug后再改成SceneBuilder格式


package view.game;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class BoxComponent extends StackPane {
    private Color color;
    private int row;
    private int col;
    private boolean isSelected;
    private Rectangle rectangle;

    public BoxComponent(Color color, int row, int col, double width, double height) {
        this.color = color;
        this.row = row;
        this.col = col;
        this.isSelected = false;

        rectangle = new Rectangle(width, height, color);
        rectangle.setStroke(Color.DARK_GRAY);
        rectangle.setStrokeWidth(1);

        this.getChildren().add(rectangle);
        this.setPrefSize(width, height);
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
        if (isSelected) {
            rectangle.setStroke(Color.RED);
            rectangle.setStrokeWidth(3);
        } else {
            rectangle.setStroke(Color.DARK_GRAY);
            rectangle.setStrokeWidth(1);
        }
    }
    // 在BoxComponent中添加移动动画
    public void animateMove(double newX, double newY) {
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
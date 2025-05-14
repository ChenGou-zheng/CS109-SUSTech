package view.game;

import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public abstract class ListenerPanel extends Pane {
    public ListenerPanel() {
        // 设置焦点以接收键盘事件
        this.setFocusTraversable(true);

        // 添加键盘事件监听
        this.addEventHandler(KeyEvent.KEY_PRESSED, this::handleKeyEvent);

        // 添加鼠标事件监听
        this.addEventHandler(MouseEvent.MOUSE_CLICKED, this::handleMouseEvent);
    }

    private void handleKeyEvent(KeyEvent e) {
        switch (e.getCode()) {
            case RIGHT, D -> doMoveRight();
            case LEFT, A -> doMoveLeft();
            case UP, W -> doMoveUp();
            case DOWN, S -> doMoveDown();
        }
    }

    private void handleMouseEvent(MouseEvent e) {
        doMouseClick(e.getX(), e.getY());
    }

    public abstract void doMouseClick(double x, double y);
    public abstract void doMoveRight();
    public abstract void doMoveLeft();
    public abstract void doMoveUp();
    public abstract void doMoveDown();
}
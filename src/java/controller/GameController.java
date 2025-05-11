package controller;

import model.Direction;
import model.MapModel;
import view.game.GamePanel;

public class GameController {
    private final GamePanel view;
    private final MapModel model;
    private final MoveHandler moveHandler;
    private final WinConditionChecker winChecker;
    private final GameStateManager stateManager;

    public GameController(GamePanel view, MapModel model) {
        this.view = view;
        this.model = model;
        this.view.setController(this);

        this.moveHandler = new MoveHandler(model);
        this.winChecker = new WinConditionChecker(model);
        this.stateManager = new GameStateManager(view);
    }

    public void restartGame() {
        stateManager.restartGame(model);
    }

    public void loadMap(MapModel newModel) {
        stateManager.loadMap(newModel);
    }

    public boolean doMove(int row, int col, Direction direction) {
        int blockId = model.getId(row, col);
        if (blockId == 0 || !moveHandler.canMove(row, col, direction, blockId)) {
            return false;
        }

        moveHandler.moveBlock(row, col, direction, blockId);
        updateBoxPositions();
        return true;
    }

    private void updateBoxPositions() {
        view.removeAllBoxes();
        view.initialGame();
        if (winChecker.checkWinCondition()) {
            showWinMessage();
        }
    }

    private void showWinMessage() {
        // 胜利消息逻辑
    }
}
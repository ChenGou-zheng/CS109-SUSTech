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

    public MoveHandler getMoveHandler() {
        return moveHandler;
    }



    public void restartGame() {
        model.resetOriginalMatrix();
        stateManager.restartGame(model);
    }

    public void loadMap(MapModel newModel) {
        stateManager.loadMap(newModel);
    }

    public boolean doMove(int row, int col, Direction direction) {
        int blockId = model.getId(row, col);
        if (moveHandler.canMove(row, col, direction, blockId)) {
            moveHandler.moveBlock(row, col, direction, blockId);
            updateBoxPositions();
            return true;
        }
        return false;
    }

    private void updateBoxPositions() {
        view.removeAllBoxes();
        view.initialGame();
        if (winChecker.checkWinCondition()) {
            showWinMessage();
        }
    }

    private void showWinMessage() {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setTitle("Victory");
        alert.setHeaderText(null);
        alert.setContentText("Congratulations! You have won the game!");
        alert.showAndWait();
    }
    private void showLoseMessage() {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
        alert.setTitle("Game Over");
        alert.setHeaderText(null);
        alert.setContentText("You have lost the game. Better luck next time!");
        alert.showAndWait();
    }



}
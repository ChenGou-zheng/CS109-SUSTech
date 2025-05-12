package controller;

import model.Direction;
import model.MapModel;
import view.game.BoxComponent;
import view.game.GamePanel;
import controller.ConditionChecker;

public class GameController {
    private final GamePanel view;
    private final MapModel mapModel;
    private final MoveHandler moveHandler;
    private final ConditionChecker conditionChecker;
    private final GameStateManager stateManager;

    public GameController(GamePanel view, MapModel mapModel) {
        this.view = view;
        this.mapModel = mapModel;
        this.view.setController(this);
        this.moveHandler = new MoveHandler(mapModel);
        this.conditionChecker = new ConditionChecker(mapModel);
        this.stateManager = new GameStateManager(view);
    }
    public MoveHandler getMoveHandler() {return moveHandler;}

    public void loadMap(MapModel newModel) {
        stateManager.loadMap(newModel);
    }

    public boolean doMove(int row, int col, Direction direction) {
        int blockId = mapModel.getId(row, col);
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
        if (conditionChecker.checkWinCondition()) {
            conditionChecker.showWinMessage();
        }if (conditionChecker.checkLoseCondition()){
            conditionChecker.showLoseMessage();
        }
    }
//Message可以放到Condition,但是电脑怎么一直在加载中呢。卡死了。坏了主机好热。。
    public void restartGame() {
        mapModel.resetOriginalMatrix();
        stateManager.restartGame(mapModel);
    }

}
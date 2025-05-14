package controller;

import model.Direction;
import model.MapModel;
import view.game.BoxComponent;
import view.game.GameFrame;
import view.game.GamePanel;
import view.game.BoxComponent;
import controller.ConditionChecker;

public class GameController {
    private final GamePanel view;
    private final MapModel mapModel;
    private final MoveHandler moveHandler;
    private final ConditionChecker conditionChecker;
    private final GameStateManager stateManager;

    private GameFrame gameFrame; // 添加对 GameFrame 的引用

    public void setGameFrame(GameFrame gameFrame) {
        this.gameFrame = gameFrame;
    }

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

// 获取目标位置
            int targetRow = row + direction.getRow();
            int targetCol = col + direction.getCol();

// 获取对应的 BoxComponent
            BoxComponent box = view.getBoxAt(row, col);
            if (box != null) {
                box.animateMove(targetRow, targetCol, view.getGRID_SIZE());
            }

// 更新视图
//            updateBoxPositions();


            return true;
        }
        return false;
    }

    public void updateBoxPositions() {
        view.removeAllBoxes();
        view.initialGame();
        if (conditionChecker.checkWinCondition()) {
            conditionChecker.showWinMessage();
            restartGame();//获胜后重置
        }if (conditionChecker.checkLoseCondition()){
            conditionChecker.showLoseMessage();
        }
    }





    public void restartGame() {
        mapModel.resetOriginalMatrix();
        stateManager.restartGame(mapModel);
        if (gameFrame != null) {
            gameFrame.setFrameSteps(0); // 重置 GameFrame 的步数
        }
    }

}
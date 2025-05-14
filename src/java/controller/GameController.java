package controller;

import model.Direction;
import model.map.MapModel;
import javafx.scene.control.Label;
import view.game.BoxComponent;
import view.game.GamePanel;
import model.timer.TimerManager;
import model.GameSaveManager;

 // 定时器实例

import java.util.Timer;
import java.util.TimerTask;

public class GameController {
    private final GamePanel view;
    private final MapModel mapModel;
    private final MoveHandler moveHandler;
    private final ConditionChecker conditionChecker;
    private final GameStateManager stateManager;
    private final TimerManager timerManager;
    private final GameSaveManager gameSaveManager;


    public GameController(GamePanel view, MapModel mapModel, TimerManager timerManager, GameSaveManager gameSaveManager) {
        this.view = view;
        this.mapModel = mapModel;
        this.view.setController(this);
        this.moveHandler = new MoveHandler(mapModel);
        this.conditionChecker = new ConditionChecker(mapModel);
        this.stateManager = new GameStateManager(view);
        this.timerManager = new TimerManager(30000);// 30秒倒计时//todo:这里还没想好在什么地方设置并更新
        this.gameSaveManager = gameSaveManager;
    }
    public MoveHandler getMoveHandler() {return moveHandler;}

    public void loadMap(MapModel newModel) {
        stateManager.loadMap(newModel);
    }

    public boolean doMove(int row, int col, Direction direction) {
        int blockId = mapModel.getId(row, col);
        if (moveHandler.canMove(row, col, direction, blockId)) {
// 获取目标位置
            int targetRow = row + direction.getRow();
            int targetCol = col + direction.getCol();
// 获取对应的 BoxComponent
            BoxComponent box = view.getBoxAt(row, col);
            box.animateMove(targetRow, targetCol, view.getGRID_SIZE());
            moveHandler.moveBlock(row, col, direction, blockId);
// 更新视图
            updateBoxPositions();
            // 自动保存
            gameSaveManager.autoSave("autosave.json", mapModel, view, timerManager);

            return true;
        }
        return false;
    }

    //todo:这里steps更新慢一步,最后胜利和成功的时候有延迟一步的情况
    //todo:或者改写逻辑尝试使用animatedMove效果
    //todo:改写以下,不要removeAllBoxes并且重新初始化,1是方法不规范,2是游戏逻辑不当
    private void updateBoxPositions() {
        view.removeAllBoxes();
        view.initialGame();
        if (conditionChecker.checkWinCondition()) {
            conditionChecker.playWinAnimation(view);
            conditionChecker.showWinMessage();
        }if (conditionChecker.checkLoseCondition(view.getSteps(), timerManager)) {
            conditionChecker.playLoseAnimation(view);
            conditionChecker.showLoseMessage();
        }
    }





    public void restartGame() {
        mapModel.resetOriginalMatrix();
        stateManager.restartGame(mapModel);

        //移除动画显示效果
        view.getChildren().removeIf(node -> node instanceof Label && "Game Over".equals(((Label) node).getText()));
    }


    public void saveGameProgress(String filePath, MapModel mapModel) {
        gameSaveManager.saveGame(filePath, mapModel, view.getSteps(), timerManager.getRemainingTime());
    }

    public void loadGameProgress(String filePath) {
        Object[] gameData = GameSaveManager.loadGame(filePath);
        if (gameData != null) {
            MapModel loadedMapModel = (MapModel) gameData[0];
            int steps = (int) gameData[1];
            long remainingTime = (long) gameData[2];

            // 更新游戏状态
            mapModel.setMatrix(loadedMapModel.getMatrix());
            view.setSteps(steps);
            timerManager.setRemainingTime(remainingTime);

            // 重新加载地图
            stateManager.loadMap(mapModel);
            System.out.println("游戏进度已恢复！");
        } else {
            System.out.println("加载游戏进度失败！");
        }
    }
}
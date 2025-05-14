package controller;

import model.map.MapModel;
import view.game.GamePanel;

//解耦功能，集成接口，但是暂时不知道怎么使用跳过


public class GameStateManager {
    private final GamePanel view;
    public GameStateManager(GamePanel view) {
        this.view = view;
    }

    public void restartGame(MapModel model) {
        view.resetGame(model);
        view.setSteps(0);
        view.setSelectedBox(null);
    }

    public void loadMap(MapModel newModel) {
        view.resetGame(newModel);
        view.setSteps(0);
    }
}
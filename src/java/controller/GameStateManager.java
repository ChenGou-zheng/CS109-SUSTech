package controller;

import model.MapModel;
import view.game.GamePanel;

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
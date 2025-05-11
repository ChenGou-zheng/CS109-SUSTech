package model.log;

public interface GameOperation {
    void moveUp();
    void moveDown();
    void moveLeft();
    void moveRight();
    void restart();
    void saveGame(String username, int[][] mapModel);
    void loadGame(String username);
}
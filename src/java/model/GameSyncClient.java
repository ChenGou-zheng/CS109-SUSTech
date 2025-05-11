package model;

public interface GameSyncClient {
    void sendAction(String action, long timestamp, String username);
}
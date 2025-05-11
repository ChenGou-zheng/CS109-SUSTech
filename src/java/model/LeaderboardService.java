package model;
import java.util.List;

public interface LeaderboardService {
    void submitRecord(String username, int steps, int timeUsed);
    List<RankEntry> getTopPlayers(int limit);
}

class RankEntry {
    private String username;
    private int steps;
    private int timeUsed;
}
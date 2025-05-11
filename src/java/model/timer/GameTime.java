package model.timer;

public class GameTime {
    private long startTime;
    private long lastActionTime;
    private int totalSeconds;
    private boolean isRunning;

    public void start() {
        this.startTime = System.currentTimeMillis();
        this.lastActionTime = startTime;
        this.isRunning = true;
    }

    public void recordAction() {
        if (isRunning) {
            this.lastActionTime = System.currentTimeMillis();
            this.totalSeconds = (int) ((lastActionTime - startTime) / 1000);
        }
    }

    public long getStartTime() {
        return startTime;
    }

    public long getLastActionTime() {
        return lastActionTime;
    }

    public int getTotalSeconds() {
        return totalSeconds;
    }

    public String getFormattedTotalTime() {
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    public void stop() {
        this.isRunning = false;
    }
}
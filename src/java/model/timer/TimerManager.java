package model.timer;

import java.util.Timer;
import java.util.TimerTask;

public class TimerManager {
    private GameTime gameTime;
    private long timeLimitMillis;
    private boolean hasTimedOut = false;
    private Timer timer;

    public interface TimeLimitListener {
        void onTimeUp();
    }

    public TimerManager(long timeLimitMillis) {
        this.gameTime = new GameTime();
        this.timeLimitMillis = timeLimitMillis;
    }

    public void startTimer(TimeLimitListener listener) {
        gameTime.start();
        hasTimedOut = false;

        timer = new Timer(true); // daemon thread
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                hasTimedOut = true;
                listener.onTimeUp();
            }
        }, timeLimitMillis);
    }

    public void stopTimer() {
        if (timer != null) {
            timer.cancel();
            timer.purge();
            gameTime.stop();
        }
    }

    public void recordAction() {
        gameTime.recordAction();
    }

    public GameTime getGameTime() {
        return gameTime;
    }

    public boolean isTimedOut() {
        return hasTimedOut;
    }
}
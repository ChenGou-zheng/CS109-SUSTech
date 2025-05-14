package model.timer;

import java.util.Timer;
import java.util.TimerTask;

public class TimerManager {
    private GameTime gameTime;
    private long timeLimitMillis;
    private long lastActionTime;
    private boolean hasTimedOut = false;
    private Timer timer;

    public interface TimeLimitListener {
        void onTimeUp();
    }

    public TimerManager(long timeLimitMillis) {
        this.gameTime = new GameTime();
        this.timeLimitMillis = timeLimitMillis;
    }

    // 启动计时器
    public void startTimer(TimeLimitListener listener) {
        stopTimer(); // 确保之前的计时器被清理
        gameTime.start();
        hasTimedOut = false;

        timer = new Timer(true); // 守护线程
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                hasTimedOut = true;
                listener.onTimeUp();
            }
        }, timeLimitMillis);
    }

    // 停止计时器
    public void stopTimer() {
        if (timer != null) {
            timer.cancel();
            timer.purge();
            timer = null;
        }
        gameTime.stop();
    }

    // 重置计时器
    public void resetTimer(TimeLimitListener listener) {
        stopTimer();
        startTimer(listener);
    }

    // 动态调整时间限制
    public void setTimeLimit(long newTimeLimitMillis) {
        this.timeLimitMillis = newTimeLimitMillis;
    }

    // 获取剩余时间
    public long getRemainingTime() {
        if (hasTimedOut || timer == null) {
            return 0;
        }
        return timeLimitMillis - (System.currentTimeMillis() - gameTime.getStartTime());
    }

    // 记录操作时间
    public void recordAction() {
        gameTime.recordAction();
        lastActionTime = System.currentTimeMillis();
    }

    public void setRemainingTime(long newTimeLimitMillis) {
        this.timeLimitMillis = newTimeLimitMillis;
        if (timer != null) {
            timer.cancel();
            timer.purge();
            timer = null;
        }
        startTimer(() -> {
            // 计时器到期时的处理逻辑
            hasTimedOut = true;
        });
    }

    // 获取游戏时间
    public GameTime getGameTime() {
        return gameTime;
    }

    // 检查是否超时
    public boolean isTimedOut() {
        return hasTimedOut;
    }
}
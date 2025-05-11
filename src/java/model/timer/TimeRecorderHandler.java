package model.timer;

import model.log.TimeLogger;
import model.timer.TimerManager;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class TimeRecorderHandler implements InvocationHandler {

    private final Object target;             // 被代理的真实对象（如 Board）
    private final TimerManager timerManager; // 时间管理器
    private final TimeLogger timeLogger;     // 日志记录器
    private final String currentUser;        // 当前用户（用于日志）

    public TimeRecorderHandler(Object target, TimerManager timerManager, TimeLogger timeLogger, String currentUser) {
        this.target = target;
        this.timerManager = timerManager;
        this.timeLogger = timeLogger;
        this.currentUser = currentUser;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 1. 执行原始方法（如 moveUp）
        Object result = method.invoke(target, args);

        // 2. 自动记录时间点
        if (shouldRecord(method.getName())) {
            timerManager.recordAction();  // 更新最后一次操作时间
            timeLogger.logAction(currentUser, method.getName());  // 写入日志
        }

        return result;
    }

    // 判断哪些方法需要记录时间
    private boolean shouldRecord(String methodName) {
        return methodName.startsWith("move") ||
                methodName.equals("restart");
    }
}
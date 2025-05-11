package model.log;

import model.LogModel;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class GameOperationHandler implements InvocationHandler {

    private final Object target;           // 被代理的真实对象（例如 Board）
    private final LogModel logModel;       // 日志记录器
    private String currentUser;            // 当前用户

    public GameOperationHandler(Object target, LogModel logModel, String currentUser) {
        this.target = target;
        this.logModel = logModel;
        this.currentUser = currentUser;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 1. 执行原始方法
        Object result = method.invoke(target, args);

        // 2. 只对特定方法添加日志
        if (shouldLog(method.getName())) {
            recordLog(method.getName(), args);
        }

        return result;
    }

    // 判断哪些方法需要记录日志
    private boolean shouldLog(String methodName) {
        return methodName.startsWith("move") ||
                methodName.equals("restart") ||
                methodName.equals("saveGame");
    }

    // 记录日志
    private void recordLog(String action, Object[] args) {
        int[][] mapModel = null;

        // 如果是 saveGame，从参数中提取地图信息
        if (action.equals("saveGame") && args != null && args.length >= 2) {
            mapModel = (int[][]) args[1];
        } else {
            // 假设 target 对象提供 getMapModel 方法
            try {
                Method getMapMethod = target.getClass().getMethod("getMapModel");
                mapModel = (int[][]) getMapMethod.invoke(target);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // 添加日志（默认密码为空或传入其他方式获取）
        logModel.addLog(currentUser, "", mapModel, action);
    }
}
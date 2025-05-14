package model.log;

import java.lang.reflect.Proxy;

public class ProxyFactory {

    public static <T> T createProxy(T target, Class<T> interfaceType, LogModel logModel, String currentUser) {
        return (T) Proxy.newProxyInstance(
                interfaceType.getClassLoader(),
                new Class<?>[]{interfaceType},
                new GameOperationHandler(target, logModel, currentUser)
        );
    }
}

/*
public class Board implements GameOperation {
    private int[][] mapModel;

    public int[][] getMapModel() {
        return mapModel;
    }

    // 实现 moveUp, moveDown 等方法
}
以下是使用示例。
LogModel logModel = new LogModel();
String currentUser = "user123";

Board originalBoard = new Board(...);
Board board = ProxyFactory.createProxy(originalBoard, GameOperation.class, logModel, currentUser);
 */
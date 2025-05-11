package model.timer;

import model.log.TimeLogger;

import java.lang.reflect.Proxy;

public class ProxyFactory {

    public static <T> T createProxy(T target, Class<T> interfaceType,
                                    TimerManager timerManager,
                                    TimeLogger timeLogger,
                                    String currentUser) {
        return (T) Proxy.newProxyInstance(
                interfaceType.getClassLoader(),
                new Class<?>[]{interfaceType},
                new TimeRecorderHandler(target, timerManager, timeLogger, currentUser)
        );
    }
}
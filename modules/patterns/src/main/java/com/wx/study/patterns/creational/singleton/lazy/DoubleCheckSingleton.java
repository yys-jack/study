package com.wx.study.patterns.creational.singleton.lazy;

/**
 * @author wxli
 * @date 2021/7/2 15:28
 */
public class DoubleCheckSingleton {
    private DoubleCheckSingleton() {

    }

    private static volatile DoubleCheckSingleton instance = null;

    public static DoubleCheckSingleton getInstance() {
        if (instance == null) {
            synchronized (DoubleCheckSingleton.class) {
                if (instance == null) {
                    instance = new DoubleCheckSingleton();
                }
            }
        }
        return instance;
    }


}

package com.wx.study.creationmode.singleton.lazy;

/**
 * @author wxli
 * @date 2021/7/2 15:28
 */
public class Singleton {
    private Singleton() {

    }

    public static Singleton getInstance() {
        return inner.singleton;
    }

    private static class inner {
        private static final Singleton singleton = new Singleton();
    }

}

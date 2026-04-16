package com.wx.study.patterns.creational.singleton.hungry;

/**
 * @author wxli
 * @date 2021/7/2 15:40
 */
public class Singleton {
    //私有构造器，避免直接构造出对象
    private Singleton() {

    }
    private static final Singleton SINGLETON =new Singleton();
    //类加载时执行，不会存在线程不安全情况
    public static Singleton getInstance(){
        return SINGLETON;
    }
}

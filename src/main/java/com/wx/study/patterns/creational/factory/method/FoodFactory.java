package com.wx.study.patterns.creational.factory.method;

/**
 * @author wxli
 * @date 2021/7/2 14:42
 */
public interface  FoodFactory {
    //工厂方法模式，
    //可以将工厂实例注入给其他需要使用工厂的类使用
    Food make(String type);
}

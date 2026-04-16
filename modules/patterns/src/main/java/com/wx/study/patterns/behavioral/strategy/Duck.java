package com.wx.study.strategy;

/**
 * 鸭子类
 *
 * @author wxli
 * @date 2021/7/20 21:16
 */
public abstract class Duck {

    FlyBehavior flyBehavior;
    QuackBehavior quackBehavior;

    public Duck() {
    }

    /**
     * 设置外观
     */
    public abstract void display();

    public void performQuack(){
        quackBehavior.quack();
    }
    public void performFly(){
        flyBehavior.fly();
    }
}

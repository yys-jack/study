package com.wx.study.adapter;

/**
 * @author wxli
 * @date 2021/7/8 22:40
 */
public class MallardDuck implements Duck{
    @Override
    public void quack() {
        System.out.println("quack  quack  quack");
    }

    @Override
    public void fly() {
        System.out.println("fly  fly   fly");
    }
}

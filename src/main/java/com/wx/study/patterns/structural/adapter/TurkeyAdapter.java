package com.wx.study.adapter;

/**
 * 首先，需要实现想转换的接口
 * @author wxli
 * @date 2021/7/8 22:43
 */
public class TurkeyAdapter implements Duck {
    Turkey turkey;

    //接着  需要取到要适配的对象引用
    public TurkeyAdapter(Turkey turkey) {
        this.turkey = turkey;
    }

    @Override
    public void quack() {
        turkey.gobble();
    }

    @Override
    public void fly() {
        turkey.fly();
        turkey.fly();
        turkey.fly();
    }
}

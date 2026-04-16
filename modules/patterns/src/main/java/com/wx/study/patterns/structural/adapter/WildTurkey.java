package com.wx.study.adapter;

/**
 * @author wxli
 * @date 2021/7/8 22:42
 */
public class WildTurkey implements Turkey{
    @Override
    public void gobble() {
        System.out.println("gobble gobble gobble");
    }

    @Override
    public void fly() {
        System.out.println("WildTurkey   fly()");
    }
}

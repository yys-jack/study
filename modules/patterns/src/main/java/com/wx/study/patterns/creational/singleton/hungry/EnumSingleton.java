package com.wx.study.patterns.creational.singleton.hungry;

/**
 * @author wxli
 * @date 2021/7/2 15:39
 */
public enum EnumSingleton {
    SINGLETON;
    public EnumSingleton getSingleton(){
        return SINGLETON;
    }
}

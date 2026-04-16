package com.wx.study.creationmode.factory.simple;

/**
 * @author wxli
 * @date 2021/7/1 17:50
 */
public class SimpleFactory {
    //封装变化的内容，通过某个类型，创建某种对象
    //单一职责，一个类只负责提供一种功能。
    //该类只生产String对象
    public String factory(String name) {
        if (name.equals("xiaowang")) {
            return new String("xiaowang");
        } else if (name.equals("xiaoli")) {
            return new String("xiaoli");
        } else if (name.equals("xiaozhang")) {
            return new String("xiaozhang");
        } else {
            return null;
        }
    }

}

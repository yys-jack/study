package com.wx.study.reference;

/**
 *
 * 常量在编译阶段会存入调用类的常量池种，
 * 本质上没有直接引用到定义常量的类，
 * 因此不会触发定义常量类的初始化
 * @author wxli
 * @date 2021/9/18 22:05
 */
public class ConstClass{
    static {
        System.out.println("ConstClass init !");
    }
    public static final String HELLO_WORLD="hello world";
}

class NotInitialization3 {
    public static void main(String[] args) {
        System.out.println(ConstClass.HELLO_WORLD);
    }

}

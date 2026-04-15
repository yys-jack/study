package com.wx.study.reference;

/**
 * 被动引用
 * 通过子类引用父类静态字段，不会导致子类初始化
 *
 * @author wxli
 * @date 2021/9/18 17:38
 */
public class SuperClass {
    static {
        System.out.println("superClass init!");
    }

    public static int value = 123;

}

class SubClass extends SuperClass {
    static {
        System.out.println("SubClass init!");
    }
}

class NotInitialization {
    public static void main(String[] args) {
        System.out.println(SubClass.value);
    }
}

class NotInitialization2 {
    public static void main(String[] args) {
        SuperClass[] sca=new SuperClass[10];
    }
}


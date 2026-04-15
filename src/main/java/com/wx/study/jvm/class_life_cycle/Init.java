package com.wx.study.class_life_cycle;

/**
 * <clinit>执行顺序
 *
 * <clinit>（）方法与类的构造函数不同，它不需要显示地调用父类构造器</>
 * Java虚拟机会保证在子类的<clinit>（）方法执行前，父类的<clinit>（）方法已经执行完毕
 * 因此在Java虚拟机中第一个被执行的<clinit>（）方法的类型肯定是java.lang.Object
 * <p>
 * 由于父类的<clinit>（）方法先执行，也就意味着父类中定义的静态语句块优先于子类的变量赋值操作。
 *
 * @author wxli
 * @date 2021/9/18 22:41
 */

public class Init {
    static class Parent {
        public static int A = 1;

        static {
            A = 2;
        }
    }

    static class Sub extends Parent {
        public static int B = A;
    }

    public static void main(String[] args) {
        System.out.println(Sub.B);

    }

}

package com.wx.study.class_life_cycle;

/**
 * 字段解析
 *
 * java虚拟机必须保证一个类的<clinit>()方法在多线程环境中被正确的加锁同步，
 * 如果多个线程同时去初始化一个类，那么指挥有其中一个线程去执行这个类的<clinit>()方法，
 * 其他线程都被阻塞
 *
 * 如果执行了<clinit>()方法的那条线程退出了<clinit>()方法，其他线程唤醒后则不会再次进入<clinit>()方法
 * 同一个类加载器下，一个类型只会被初始化一次
 * @author wxli
 * @date 2021/9/18 22:48
 */
public class FieldParsing {
    static class DeadLoopClass{
        static {
            //如果不加上这个if语句,编译器将提示“Initializer does not complete normally”
            //并拒绝编译
            if (true){
                System.out.println(Thread.currentThread() + "init DeadLoopClass");
                while (true) {

                }
            }
        }
    }

    public static void main(String[] args) {
        Runnable script = new Runnable(){
            @Override
            public void run() {
                System.out.println(Thread.currentThread() + "start");
                DeadLoopClass dlc = new DeadLoopClass();
                System.out.println(Thread.currentThread() + "run over");
            }
        };
        Thread thread1 = new Thread(script);
        Thread thread2 = new Thread(script);
        thread1.start();
        thread2.start();
    }
}

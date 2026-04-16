package com.wx.study.concurrent.waitnotify;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionDemo {
    private ReentrantLock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    void method1() throws InterruptedException {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "条件不满足，开始await");
            condition.await();
            System.out.println(Thread.currentThread().getName() + "条件满足，开始执行");
        } finally {
            lock.unlock();
        }
    }

    void method2() throws InterruptedException {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "需要5秒准备时间");
            Thread.sleep(5000);
            System.out.println(Thread.currentThread().getName() + "准备完毕，唤醒其他线程");
            condition.signal();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ConditionDemo conditionDemo=new ConditionDemo();
        new Thread(()->{
            try {
                conditionDemo.method2();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        conditionDemo.method1();
    }

}

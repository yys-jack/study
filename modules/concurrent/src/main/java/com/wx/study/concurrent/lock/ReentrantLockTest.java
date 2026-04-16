package com.wx.study.concurrent.lock;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockTest {
    private final static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    lock.lockInterruptibly();
                    System.out.println(Thread.currentThread().getName() + "start sleep");
                    Thread.sleep(100000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.out.println(Thread.currentThread().getName() + "stop sleep");
                    lock.unlock();
                }

            }
        });
        thread1.start();

        //主线程睡眠，让线程1先获取到锁
        Thread.sleep(100);
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("111");
                    Thread.sleep(100);
                    thread1.join();
                    System.out.println("222");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (!lock.isLocked()) {
                    lock.lock();
                    System.out.println(Thread.currentThread().getName() + "successfully acquired lock");
                }
            }
        });
        thread2.start();
//        thread1.join();

        thread1.interrupt();

        Thread.sleep(10000);


    }
}

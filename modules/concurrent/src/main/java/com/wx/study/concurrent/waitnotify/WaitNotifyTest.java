package com.wx.study.concurrent.waitnotify;

import org.junit.Test;

public class WaitNotifyTest {

    Object lock = new Object();
    Object lock2 = new Object();

    /**
     * 为什么 wait/notify/notifyAll 被定义在 Object 类中，而 sleep 定义在 Thread 类中？
     * 1.每个对象都有monitor监视器锁，存储在对象头中。
     * 这说明每个对象都可以上锁，锁是对象级别的wait/notify/notifyAll也都是对象级别的操作。
     * 又Object是所有对象的父类，那么把这些操作定义在Object中最合适
     * <p>
     * 2.一个线程能够持有多把锁，如果把wait方法定义在Thread类中，如何让一个线程持有多把锁
     * 如何明确线程等待的是哪把锁。既然我们是让线程等待某个对象的锁，那么自然应该通过操作对象来实现
     * 而不是操作线程
     */
    @Test
    public void Test1() throws InterruptedException {
        Runnable runnable = () -> {
            synchronized (lock) {
                try {
                    Thread.sleep(100);
                    System.out.println(Thread.currentThread().getName() + "等待");
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        Thread thread = new Thread(runnable);
//        thread.setDaemon(true);
        thread.start();
        Thread.sleep(10000);

    }

    @Test
    public void Test2() throws InterruptedException {

        Runnable runnable = () -> {
            synchronized (lock) {
                try {
                    Thread.sleep(100);
                    System.out.println(Thread.currentThread().getName() + "等待");
                    lock2.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
        Thread.sleep(10000);

    }

    /**
     * wait/notify 和 sleep 方法的异同？
     * 相同点
     * 1.都让线程阻塞
     * 2.都能够响应中断，在等待的过程中如果收到中断信号，都可以进行响应，并抛出InterruptedException 异常
     * 不同点
     * 1.wait方法必须在synchronized保护的代码中使用，sleep不需要
     * 2.同步代码块中使用sleep不会释放锁，wait方法会释放锁
     * 3.sleep方法中会被要求必须定义一个时间，时间到期后会主动恢复，而对于没有参数的wait方法，意味着永远等待，直到被中断或被唤醒才能恢复，它并不会主动恢复
     * 4.wait/notify是Object类的方法，而sleep是Thread类的方法
     */
    @Test
    public void Test3() throws InterruptedException {

        Runnable runnable = () -> {
            synchronized (lock) {
                try {
                    Thread.sleep(100);
                    System.out.println(Thread.currentThread().getName() + "等待");
                    lock.wait();
                    System.out.println(Thread.currentThread().getName() + "重新执行");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Runnable runnable2 = () -> {
            synchronized (lock2) {
                try {
                    Thread.sleep(100);
//                    lock.notify();              //比较区别
//                    lock2.notifyAll();
                    System.out.println(Thread.currentThread().getName() + "通知");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        Thread thread = new Thread(runnable);
        Thread thread2 = new Thread(runnable2);
        thread.start();
        thread2.start();
        Thread.sleep(10000);
    }


}

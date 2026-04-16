package com.wx.study.concurrent.interview.A1B2C3;

import java.util.concurrent.CountDownLatch;

public class SyncWaitNotify {

    private static volatile boolean t2Started = false;

    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    public static void main(String[] arg) {
        String nums = "123";
        String strs = "abc";
        char[] numsArg = nums.toCharArray();
        char[] strsArg = strs.toCharArray();
        final Object lock = new Object();
        Thread thread1 = new Thread(() -> {
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (lock) {


                //如果想先打印数字，则可以先让打印字母的线程先阻塞。
//                while (!t2Started) {
//                    try {
//                        lock.wait();
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }

                for (char c : numsArg) {
                    System.out.println(c);
                    try {
                        lock.notify();
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                lock.notify();
            }
        });

        Thread thread2 = new Thread(() -> {
            synchronized (lock) {
                for (char c : strsArg) {
                    System.out.println(c);
                    countDownLatch.countDown();
//                    t2Started = true;
                    try {
                        lock.notify();
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                lock.notify();
            }
        });
        thread1.start();
        thread2.start();
    }
}
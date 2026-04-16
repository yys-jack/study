package com.wx.study.concurrent.interview.A1B2C3;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchWaitNotify {

    public static void main(String[] arg) {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        String nums = "123";
        String strs = "abc";
        char[] numsArg = nums.toCharArray();
        char[] strsArg = strs.toCharArray();
        final Object lock = new Object();

        Thread thread1 = new Thread(() -> {
            for (char c : numsArg) {
                try {
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(c);
            }
        });

        Thread thread2 = new Thread(() -> {
            for (char c : strsArg) {
                System.out.println(c);
                countDownLatch.countDown();
            }
        });
        thread1.start();
        thread2.start();
    }
}
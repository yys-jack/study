package com.wx.study.interview.A1B2C3;

import java.util.concurrent.locks.LockSupport;

public class LockSupportWaitNotify {
    static Thread thread1;

    static Thread thread2 = null;

    public static void main(String[] args) {
        String nums = "123";
        String strs = "abc";
        char[] numsArg = nums.toCharArray();
        char[] strsArg = strs.toCharArray();
        thread1 = new Thread(() -> {
            for (char c : strsArg) {
                System.out.println(c);
                LockSupport.unpark(thread2);
                LockSupport.park();
            }
        });

        thread2 = new Thread(() -> {
            for (char c : numsArg) {
                LockSupport.park();
                System.out.println(c);
                LockSupport.unpark(thread1);
            }

        });
        thread1.start();
        thread2.start();
    }
}

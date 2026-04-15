package com.wx.study.interview.A1B2C3;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class LockWaitNotify {
    public static void main(String[] args) {
        String nums = "123";
        String strs = "abc";
        char[] numsArg = nums.toCharArray();
        char[] strsArg = strs.toCharArray();
        ReentrantLock lock = new ReentrantLock();
        Condition threadCondition1 = lock.newCondition();
        Condition threadCondition2 = lock.newCondition();
        Thread thread1 = new Thread(() -> {
            lock.lock();
            try {
                for (char c : numsArg) {
                    System.out.println(c);
                    threadCondition2.signal();
                    threadCondition1.await();
                }
                threadCondition2.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        });

        Thread thread2 = new Thread(() -> {
            lock.lock();
            try {
                for (char c : strsArg) {
                    System.out.println(c);
                    threadCondition1.signal();
                    threadCondition2.await();
                }
                threadCondition1.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        });
        thread1.start();
        thread2.start();
    }

}

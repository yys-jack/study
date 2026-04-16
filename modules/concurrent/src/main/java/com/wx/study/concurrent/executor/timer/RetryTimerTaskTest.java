package com.wx.study.executor.timer;

import io.netty.util.HashedWheelTimer;
import io.netty.util.Timer;

import java.util.concurrent.TimeUnit;

/**
 * @author wxli
 * @date 2021/9/27 21:20
 */
public class RetryTimerTaskTest {
    public static final Timer timer = new HashedWheelTimer();

    public static void main(String[] args) throws InterruptedException {
        RetryTimerTask task = new RetryTimerTask(1, 5);
        timer.newTimeout(task, task.getTick(), TimeUnit.SECONDS);
        System.out.println(Thread.currentThread().getName() + "hello world");
        Thread.sleep(1000);
    }
}


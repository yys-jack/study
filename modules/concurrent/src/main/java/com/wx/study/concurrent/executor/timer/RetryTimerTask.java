package com.wx.study.concurrent.executor.timer;

import io.netty.util.Timeout;
import io.netty.util.Timer;
import io.netty.util.TimerTask;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author wxli
 * @date 2021/9/27 21:20
 */
public class RetryTimerTask implements TimerTask {

    //每隔几秒执行
    private final long tick;

    //最大重试次数
    private final int retries;

    private int retryTimes = 0;

    public RetryTimerTask(long tick, int retries) {
        this.tick = tick;
        this.retries = retries;
    }

    public long getTick() {
        return tick;
    }

    public int getRetries() {
        return retries;
    }

    @Override
    public void run(Timeout timeout) throws Exception {
        try {
            Thread.sleep(2000);
            throw new RuntimeException("执行出错");
        } catch (Throwable e) {
            if (++retryTimes > retries) {
                //不在重试
                System.out.println(Thread.currentThread().getName() + "程序已经执行 " + retries + "次数，不再重试" + new Date());
            } else {
                Timer timer = timeout.timer();
                timer.newTimeout(timeout.task(), tick, TimeUnit.SECONDS);
                System.out.println(Thread.currentThread().getName() + "重新执行程序 retryTimes " + retryTimes + new Date());
            }
        }
    }
}


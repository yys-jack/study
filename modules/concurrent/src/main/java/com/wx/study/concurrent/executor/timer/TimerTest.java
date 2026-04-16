package com.wx.study.concurrent.executor.timer;

import org.junit.Test;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 定时器
 * https://www.baeldung.com/java-timer-and-timertask
 *
 * @author wxli
 * @date 2021/9/27 13:25
 */
public class TimerTest {
    @Test
    public void givenUsingTimer_whenSchedulingTaskOnce_thenCorrect() throws InterruptedException {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + "执行任务" + new Date());
            }
        };
        Timer timer = new Timer("timer");
        long delay = 1000L;
        timer.schedule(task, delay);
        Thread.sleep(delay * 2);
    }

    /**
     * schedule
     * 每个任务间隔1秒,如任务执行超过1秒，那么下面一个任务会立即执行
     * @throws InterruptedException
     */
    @Test
    public void NewsletterTaskTest() throws InterruptedException {
        new Timer().schedule(new NewsletterTask(), 0, 1000);

        for (int i = 0; i < 10; i++) {
            Thread.sleep(1000);
        }
    }

    /**
     * scheduleAtFixedRate
     * 一个任务执行超时之后，后续会起多个任务一起执行
     * @throws InterruptedException
     */
    @Test
    public void NewsletterTaskTest2() throws InterruptedException {
        new Timer().scheduleAtFixedRate(new NewsletterTask(), 0, 1000);

        for (int i = 0; i < 10; i++) {
            Thread.sleep(1000);
        }
    }

    /**
     * 每天执行
     */
    @Test
    public void givenUsingTimer_whenSchedulingDailyTask_thenCorrect() {
        TimerTask repeatedTask = new TimerTask() {
            public void run() {
                System.out.println("Task performed on " + new Date());
            }
        };
        Timer timer = new Timer("Timer");

        long delay = 1000L;
        long period = 1000L * 60L * 60L * 24L;
        timer.scheduleAtFixedRate(repeatedTask, delay, period);
    }


    /**
     * 通过在TimerTask本身的run()方法实现中调用TimerTask.cancel()方法：
     * @throws InterruptedException
     */
    @Test
    public void givenUsingTimer_whenCancelingTimerTask_thenCorrect()
            throws InterruptedException {
        TimerTask task = new TimerTask() {
            public void run() {
                System.out.println("Task performed on " + new Date());
                cancel();
            }
        };
        Timer timer = new Timer("Timer");

        timer.scheduleAtFixedRate(task, 1000L, 1000L);

        Thread.sleep(1000L * 2);
    }

}

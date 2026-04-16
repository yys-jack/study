package com.wx.study.concurrent.executor;

import org.junit.Test;

import java.util.concurrent.*;

/**
 * https://www.baeldung.com/java-executor-service-tutorial
 *
 * https://www.cnblogs.com/wxgblogs/p/5471315.html    ScheduledFuture 类解答
 *
 * 结论：   1. 使未使用的ExecutorService保持活动状态
 *         2. 在任务取消后调用Future的get()方法：尝试获取已取消任务的结果会触发CancellationException。
 *         3. 使用固定长度线程池时错误的线程池容量：
 *            确定应用程序需要多少线程才能有效运行任务非常重要。
 *            过大的线程池会导致不必要的开销，只是为了创建主要处于等待模式的线程。
 *            由于队列中任务的等待时间太长，太少会使应用程序看起来没有响应。
 *         4. Future的get()方法出乎意料的长时间阻塞
 *
 *
 * @author wxli
 * @date 2021/7/28 21:32
 */
public class ScheduledExecutorServiceTest {
    ScheduledExecutorService executorService = Executors
            .newSingleThreadScheduledExecutor();


    Callable<String> callableTask = () -> {
        TimeUnit.MILLISECONDS.sleep(300);
        return "Task's execution";
    };

    Runnable runnableTask = () -> {
        try {
            System.out.println(Thread.currentThread().getName() + "start");
            TimeUnit.MILLISECONDS.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    };

    /**
     * schedule(),延迟1秒执行
     */
    public void test() {
        Future<String> resultFuture =
                executorService.schedule(callableTask, 1, TimeUnit.SECONDS);
    }

    /**
     * 以下代码块将在 100 毫秒的初始延迟后运行任务。之后，它将每 450 毫秒运行一次相同的任务：
     * 450秒没有完成当前任务，下一个任务会被延迟，且立即执行，
     * 永远不会并发执行
     */
    @Test
    public void test2() throws InterruptedException {
        executorService.scheduleAtFixedRate(task, 100, 2000, TimeUnit.MILLISECONDS);
        Thread.sleep(10000);
    }
    Runnable task = () -> {
        System.out.println("开始执行");
        try {
            Thread.sleep(3000);
            System.out.println("执行完毕");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    };

    /**
     * 如果需要在任务的迭代之间有固定长度的延迟，
     * 则应使用scheduleWithFixedDelay()。
     */
    @Test
    public void test3() throws InterruptedException {

        executorService.scheduleWithFixedDelay(task, 100, 1500, TimeUnit.MILLISECONDS);
        Thread.sleep(10000);
    }
}

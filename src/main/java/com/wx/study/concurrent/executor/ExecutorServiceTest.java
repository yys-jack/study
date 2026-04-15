package com.wx.study.executor;


import lombok.SneakyThrows;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * https://www.baeldung.com/java-executor-service-tutorial
 *
 * @author wxli
 * @date 2021/7/28 20:36
 */
public class ExecutorServiceTest {

    //10 个线程的线程池：
    ExecutorService executor = Executors.newFixedThreadPool(10);

    ExecutorService executorService =
            new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS,
                    new LinkedBlockingQueue<Runnable>());

    Runnable runnableTask = () -> {
        try {
            System.out.println(Thread.currentThread().getName() + "start");
            TimeUnit.MILLISECONDS.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    };

    Callable<String> callableTask = () -> {
        TimeUnit.MILLISECONDS.sleep(300);
        return "Task's execution";
    };
    List<Callable<String>> callableTasks = new ArrayList<>();

    /**
     * 执行任务没有返回值
     */
    @SneakyThrows
    @Test
    public void execute() {
        executorService.execute(runnableTask);
    }

    /**
     * 执行submit  会得到一个future  ，
     * future 可以知道提交的任务是否完成，和获取任务完成之后的结果
     */
    @SneakyThrows
    @Test
    public void submit() {
        Future<String> future = executorService.submit(callableTask);
        System.out.println(future.get());
    }

    /**
     * 在任务仍在运行时调用get()方法将导致执行阻塞，直到任务正确执行并且结果可用。
     * <p>
     * 由于get()方法导致长时间阻塞，应用程序的性能可能会下降。
     * <p>
     * 如果结果数据并不重要，则可以通过使用超时来避免此类问题：
     * String result = future.get(200, TimeUnit.MILLISECONDS);
     * <p>
     */
    @SneakyThrows
    @Test
    public void futureTest() {
        Future<String> future = executorService.submit(callableTask);
        System.out.println(future.get());

        //取消和检查取消方法。
        //调用取消方法之后，会立即终止线程
        boolean canceled = future.cancel(true);
        boolean isCancelled = future.isCancelled();
    }

    /**
     * invokeAny()将一组任务分配给ExecutorService，
     * 使每个任务都运行，并返回一个任务成功执行的结果（如果有成功执行）
     */
    @Test
    public void invokeAllTest() throws InterruptedException {
        callableTasks.add(callableTask);
        callableTasks.add(callableTask);
        callableTasks.add(callableTask);
        executorService.invokeAll(callableTasks).forEach(item -> {
            try {
                System.out.println(item.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 不会导致的立即销毁的ExecutorService。
     * 它会使ExecutorService停止接受新任务并在所有正在运行的线程完成当前工作后关闭：
     * executorService.shutdown();
     * 一调用shutdown()
     * 那么就会出现isShutdown（）   返回ture
     * <p>
     * <p>
     * 该shutdownNow时（）方法试图破坏ExecutorService的马上，但它并不能保证所有正在运行的线程将在同一时间停止：
     * 关闭ExecutorService（Oracle也推荐）的一种好方法是将这两种方法与awaitTermination()方法结合使用：
     */

    Runnable runnable = () -> {
        try {
            System.out.println(Thread.currentThread().getName() + "start");
            Thread.sleep(10000);
            System.out.println(Thread.currentThread().getName() + "stop");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    };

    @Test
    public void shutdownTest() {
        executorService.submit(runnable);
        //可尝试
//        executorService.shutdown();
        while (!executorService.isShutdown()) {
            System.out.println("线程池没有销毁");
            executorService.shutdown();
        }
        System.out.println("任务执行完毕，销毁线程池");
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 该shutdownNow时（）方法试图破坏ExecutorService
     * 但它并不能保证所有正在运行的线程将在同一时间停止：
     * 推荐： 关闭ExecutorService的一种好方法是将这两种方法与awaitTermination()方法结合使用：
     */
    @SneakyThrows
    @Test
    public void shutdownNowTest() {
        executorService.invokeAll(callableTasks);
        //停止接收新的任务
        executorService.shutdown();
        try {
            //阻塞线程，自旋等待。超时返回false，
            //已终止则返回ture，不需要调用shutdownNow()
            if (!executorService.awaitTermination(800, TimeUnit.MILLISECONDS)) {
                //立即销毁实例
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }
    }

}

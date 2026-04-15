package com.wx.study.threadtest;

import com.wx.study.threadtest.callable.FactorialCallableTask;
import com.wx.study.threadtest.callable.FactorialTask;
import com.wx.study.threadtest.runnalbe.EventLoggingTask;
import lombok.SneakyThrows;
import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.junit.Assert.assertEquals;

/**
 * https://www.baeldung.com/java-runnable-callable
 * <p>
 * Runnable任务可以使用Thread类或ExecutorService运行，而Callables只能使用后者运行。
 *
 * @author wxli
 * @date 2021/7/27 11:47
 */
public class ThreadTest {
    //不带返回值
    @Test
    public void executeTask() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future future = executorService.submit(new EventLoggingTask());
        executorService.shutdown();
    }

    //带返回值
    @SneakyThrows
    @Test
    public void whenTaskSubmitted_ThenFutureResultObtained() {
        FactorialTask task = new FactorialTask(5);
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<Integer> future = executorService.submit(task);

        assertEquals(120, future.get().intValue());
    }

    //异常
    //在运行的情况下使用可赎回的ExecutorService的，
    //例外是在所收集的未来对象，这可以通过进行呼叫到被检查的Future.get（）方法。
    //这将抛出一个ExecutionException -它包装了原始异常：
    @SneakyThrows
    @Test(expected = ExecutionException.class)
    public void whenException_ThenCallableThrowsIt() {
        FactorialCallableTask task = new FactorialCallableTask(-5);
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<Integer> future = executorService.submit(task);
        Integer result = future.get();
    }

    @Test
    public void whenException_ThenCallableDoesntThrowsItIfGetIsNotCalled() {
        FactorialCallableTask task = new FactorialCallableTask(-5);
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<Integer> future = executorService.submit(task);

        assertEquals(false, future.isDone());
    }
}

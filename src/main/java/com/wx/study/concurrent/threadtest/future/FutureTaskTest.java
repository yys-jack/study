package com.wx.study.threadtest.future;

import lombok.SneakyThrows;
import org.junit.Test;

import java.util.concurrent.Future;

/**
 * @author wxli
 * @date 2021/7/27 13:35
 */
public class FutureTaskTest {
    @Test
    @SneakyThrows
    public void test() {
        SquareCalculator squareCalculator = new SquareCalculator();
        Future<Integer> future = squareCalculator.calculate(5);
        //如果任务完成，则返回true ，否则返回false。
        while (!future.isDone()) {
            System.out.println("Calculating...");
            Thread.sleep(300);
        }
        //get()方法将阻止执行，直到任务完成。
        //但是我们不必担心这一点，因为我们的示例只会在确保任务完成后调用get()。
        future.get();
    }

    //取消任务
    @Test
    @SneakyThrows
    public void test2() {
        Future<Integer> future = new SquareCalculator().calculate(4);

        boolean canceled = future.cancel(true);

        //        future.get();
        //任务被取消之后会会销毁任务线程，调用future.get();
        //会抛出异常CancellationException
        //Future.isCancelled()会告诉我们一个Future是否已经被取消。
        //避免获得CancellationException
        boolean cancelled = future.isCancelled();
    }
}

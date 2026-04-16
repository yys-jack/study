package com.wx.study.threadtest.forkjoinpooltest.forkjointask;

import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;

/**
 * https://www.baeldung.com/java-future
 * 第五点
 *
 * @author wxli
 * @date 2021/7/27 14:22
 */
public class ForkJoinTaskTest {
    @Test
    public void test() throws InterruptedException, ExecutionException {
        ForkJoinPool forkJoinPool = new ForkJoinPool();

        FactorialSquareCalculator calculator = new FactorialSquareCalculator(10);

        forkJoinPool.execute(calculator);
        while (!calculator.isDone()) {
            System.out.println("calculating task");
            Thread.sleep(100);
        }
        System.out.println(calculator.get());

    }

}

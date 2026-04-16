package com.wx.study.concurrent.threadtest.future;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * https://www.baeldung.com/java-future
 * <p>
 * java 1.5
 * Future类代表了一个异步计算的未来结果——处理完成后最终会出现在Future 中的结果。
 * <p>
 * 异步调用和并发处理
 */
public class SquareCalculator {

    private ExecutorService executor
            = Executors.newSingleThreadExecutor();

    public Future<Integer> calculate(Integer input) {
        //将callable传递给线程池，调用submit方法可返回一个future对象。
        //future对象是记录提交给线程池任务的结果
        return executor.submit(() -> {
            Thread.sleep(1000);
            return input * input;
        });
    }


}
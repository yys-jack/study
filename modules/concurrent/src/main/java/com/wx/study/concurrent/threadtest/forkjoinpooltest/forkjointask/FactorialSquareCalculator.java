package com.wx.study.threadtest.forkjoinpooltest.forkjointask;

import java.util.concurrent.RecursiveTask;

/**
 * ForkJoinTask的主要特征是它通常会产生新的子任务作为完成其主要任务所需的工作的一部分。
 * 它通过调用fork()生成新任务，并使用join()收集所有结果，因此是类的名称。
 * <p>
 * ForkJoinTask是一个抽象类，
 * 它实现了Future并且能够运行由ForkJoinPool 中的少量实际线程托管的大量任务。
 * 递归任务
 */
public class FactorialSquareCalculator extends RecursiveTask<Integer> {

    private Integer n;

    public FactorialSquareCalculator(Integer n) {
        this.n = n;
    }

    //计算任务
    @Override
    protected Integer compute() {
        if (n <= 1) {
            return n;
        }

        FactorialSquareCalculator calculator = new FactorialSquareCalculator(n - 1);

        //生成新任务fork，非阻塞
        calculator.fork();

        //收集所有结果join,计算平方和
        return n * n + calculator.join();
    }
}
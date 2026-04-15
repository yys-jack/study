package com.wx.study.threadtest.callable;

import java.util.concurrent.Callable;

/**
 * 带返回值的线程
 */
public class FactorialCallableTask implements Callable<Integer> {
    int number;

    public FactorialCallableTask(int number) {
        this.number = number;
    }

    //计算一个数的阶乘
    public Integer call() throws RuntimeException {
        if(number < 0) {
            throw new RuntimeException("Number should be positive");
        }
        int fact = 1;
        for (int count = number; count > 1; count--) {
            fact = fact * count;
        }
        return fact;
    }
}
package com.wx.study.concurrent.threadtest.callable;

import java.util.concurrent.Callable;

/**
 * 带返回值的线程
 */
public class FactorialTask implements Callable<Integer> {
    int number;

    public FactorialTask(int number) {
        this.number = number;
    }

    //计算一个数的阶乘
    public Integer call() {
        int fact = 1;
        for (int count = number; count > 1; count--) {
            fact = fact * count;
        }
        return fact;
    }
}
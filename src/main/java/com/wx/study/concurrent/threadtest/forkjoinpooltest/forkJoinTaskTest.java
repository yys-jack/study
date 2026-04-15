package com.wx.study.threadtest.forkjoinpooltest;

import org.junit.Test;

import java.util.concurrent.ForkJoinPool;

/**
 * @author wxli
 * @date 2021/7/28 20:20
 */
public class forkJoinTaskTest {

    @Test
    public void test(){
        ForkJoinPool forkJoinPool=ForkJoinPool.commonPool();
        CustomRecursiveAction customRecursiveAction = new CustomRecursiveAction("helloWorld");
        forkJoinPool.execute(customRecursiveAction);
        customRecursiveAction.join();
    }
    @Test
    public void test2(){
        ForkJoinPool forkJoinPool=ForkJoinPool.commonPool();
        CustomRecursiveTask customRecursiveTask = new CustomRecursiveTask(new int[]{1,2,3,4,5,11});
        forkJoinPool.execute(customRecursiveTask);
        Integer join = customRecursiveTask.join();
        System.out.println(join);
    }


}

package com.wx.study.threadtest.forkjoinpooltest.forkjointask;

import java.util.concurrent.ForkJoinPool;

/**
 * https://www.baeldung.com/java-fork-join
 * <p>
 * https://www.baeldung.com/java-work-stealing     工作窃取算法
 * <p>
 * https://blog.csdn.net/pange1991/article/details/80944797    介绍
 * <p>
 * ForkJoinPool  是 ExecutorService的一个实现
 * ExecutorService管理工作线程并为我们提供工具来获取有关线程池状态和性能的信息。
 * <p>
 * 工作线程一次只能执行一个任务，但ForkJoinPool不会为每个子任务创建一个单独的线程。
 * 相反，池中的每个线程都有自己的双端队列（或deque，发音为deck），用于存储任务。
 *
 * @author wxli
 * @date 2021/7/27 16:27
 */
public class ForkJoinPoolTest {
    //默认线程池
    ForkJoinPool commonPool = ForkJoinPool.commonPool();
    //使用ForkJoinPool 的构造函数，可以创建具有特定并行度、线程工厂和异常处理程序级别的自定义线程池。
    //池的并行度级别为 2。
    ForkJoinPool forkJoinPool = new ForkJoinPool(2);

    //    ForkJoinTask是在ForkJoinPool 中执行的任务的基本类型。
    //在RecursiveAction为空隙任务和RecursiveTask <V>该返回值的任务。
}

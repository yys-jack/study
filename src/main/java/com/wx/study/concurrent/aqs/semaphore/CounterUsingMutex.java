package com.wx.study.aqs.semaphore;

import java.util.concurrent.Semaphore;

/**
 * Mutex 的作用类似于二进制信号量，我们可以用它来实现互斥。
 *
 * 在下面的例子中，我们将使用一个简单的二进制信号量来构建一个计数器：
 */
public class CounterUsingMutex {

    private Semaphore mutex;
    private int count;

    CounterUsingMutex() {
        mutex = new Semaphore(1);
        count = 0;
    }

    void increase() throws InterruptedException {
        mutex.acquire();
        this.count = this.count + 1;
        Thread.sleep(1000);
        mutex.release();

    }

    int getCount() {
        return this.count;
    }

    boolean hasQueuedThreads() {
        return mutex.hasQueuedThreads();
    }
}
package com.wx.study.aqs.semaphore;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import static org.junit.Assert.*;

/**
 * @author wxli
 * @date 2021/7/15 22:47
 */
public class MutexTest {
    /**
     * 当许多线程尝试同时访问计数器时，它们只会被阻塞在队列中：
     * @throws InterruptedException
     */
    @Test
    public void whenMutexAndMultipleThreads_thenBlocked()
            throws InterruptedException {
        int count = 5;
        ExecutorService executorService
                = Executors.newFixedThreadPool(count);
        CounterUsingMutex counter = new CounterUsingMutex();
        IntStream.range(0, count)
                .forEach(user -> executorService.execute(() -> {
                    try {
                        counter.increase();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }));
        executorService.shutdown();

        assertTrue(counter.hasQueuedThreads());
    }

    @Test
    public void givenMutexAndMultipleThreads_ThenDelay_thenCorrectCount()
            throws InterruptedException {
        int count = 5;
        ExecutorService executorService
                = Executors.newFixedThreadPool(count);
        CounterUsingMutex counter = new CounterUsingMutex();
        IntStream.range(0, count)
                .forEach(user -> executorService.execute(() -> {
                    try {
                        counter.increase();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }));
        executorService.shutdown();

        assertTrue(counter.hasQueuedThreads());
        Thread.sleep(5000);
        assertFalse(counter.hasQueuedThreads());
        assertEquals(count, counter.getCount());
    }
}

package com.wx.study.producerconsumermodel;

import org.junit.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * http://learn.lianglianglee.com/
 * 阻塞队列实现生产者消费者模式
 */
public class QueueTest {
    /**
     * 创建一个ArrayBlockingQueue类型的阻塞队列，设置容量10
     * 创建一个简单的生产者往队列中添加数据
     * 创建一个简单的生产者在队列中取出数据
     * 阻塞队列，队列满了就去阻塞生产者线程，队列有空就去唤醒生产者线程
     */
    @Test
    public void queueTest() {
        BlockingQueue<Object> queue = new ArrayBlockingQueue<>(10);
        Runnable product = () -> {
            while (true) {
                try {
                    queue.put(new Object());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(product).start();
        new Thread(product).start();
        Runnable consumer = () -> {
            while (true) {
                try {
                    queue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(consumer).start();
        new Thread(consumer).start();
    }
}

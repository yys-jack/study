package com.wx.study.concurrent.interview.model;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConsumerAndProduct {
    private static BlockingQueue<String> queue=new ArrayBlockingQueue<>(10);

    public static void main(String[] args) {
        Thread consumer = new Thread(()->{
            try {
                while (true){
                    System.out.println(queue.take());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread product = new Thread(()->{
            try {
                while (true){
                    queue.put("123");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        consumer.start();
        product.start();
    }
}

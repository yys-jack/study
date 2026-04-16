package com.wx.study.concurrent.aqs.countdownlatch;

import java.util.concurrent.CountDownLatch;

/**
 * 减法计数器
 * https://www.baeldung.com/java-countdown-latch
 * <p>
 * 等待多个线程全部完成之后才进行下一步操作
 *
 * @author wxli
 * @date 2021/7/15 22:51
 */
public class CountDownLatchTest {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(2);
        new Thread(()->{
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(Thread.currentThread().getName()+"A 开始执行 ");

        }).start();
        new Thread(()->{
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

//            countDownLatch.countDown();
            countDownLatch.countDown();
            System.out.println(Thread.currentThread().getName()+"B 开始执行");
        }).start();

        Thread.sleep(4000);
        countDownLatch.countDown();

    }

}

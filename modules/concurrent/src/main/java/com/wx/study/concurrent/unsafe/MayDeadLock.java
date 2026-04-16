package com.wx.study.concurrent.unsafe;

/**
 * 死锁
 * 比如两个线程都需要获取两把锁，一个线程拿一把。
 * <p>
 * 活锁
 * 第二种活跃性问题是活锁，活锁与死锁非常相似，也是程序一直等不到结果，但对比于死锁，活锁是活的，什么意思呢？
 * 因为正在运行的线程并没有阻塞，它始终在运行中，却一直得不到结果。
 * <p>
 * 举一个例子，假设有一个消息队列，队列里放着各种各样需要被处理的消息，
 * 而某个消息由于自身被写错了导致不能被正确处理，执行时会报错，可是队列的重试机制会重新把它放在队列头进行优先重试处理，
 * 但这个消息本身无论被执行多少次，都无法被正确处理，每次报错后又会被放到队列头进行重试，周而复始，最终导致线程一直处于忙碌状态，但程序始终得不到结果，便发生了活锁问题。
 * <p>
 * 饥饿
 * 第三个典型的活跃性问题是饥饿，饥饿是指线程需要某些资源时始终得不到，尤其是CPU 资源，就会导致线程一直不能运行而产生的问题。
 * 在 Java 中有线程优先级的概念，Java 中优先级分为 1 到 10，1 最低，10 最高。如果我们把某个线程的优先级设置为1，这是最低的优先级，
 * 在这种情况下，这个线程就有可能始终分配不到 CPU 资源，而导致长时间无法运行。
 * 或者是某个线程始终持有某个文件的锁，而其他线程想要修改文件就必须先获取锁，这样想要修改文件的线程就会陷入饥饿，长时间不能运行。
 */
public class MayDeadLock {

    Object o1 = new Object();

    Object o2 = new Object();

    public void thread1() throws InterruptedException {

        synchronized (o1) {

            Thread.sleep(500);

            synchronized (o2) {

                System.out.println("线程1成功拿到两把锁");

            }

        }

    }

    public void thread2() throws InterruptedException {

        synchronized (o2) {

            Thread.sleep(500);

            synchronized (o1) {

                System.out.println("线程2成功拿到两把锁");

            }

        }

    }

    public static void main(String[] args) {

        MayDeadLock mayDeadLock = new MayDeadLock();

        new Thread(new Runnable() {

            @Override

            public void run() {

                try {

                    mayDeadLock.thread1();

                } catch (InterruptedException e) {

                    e.printStackTrace();

                }

            }

        }).start();

        new Thread(new Runnable() {

            @Override

            public void run() {

                try {

                    mayDeadLock.thread2();

                } catch (InterruptedException e) {

                    e.printStackTrace();

                }

            }

        }).start();

    }

}
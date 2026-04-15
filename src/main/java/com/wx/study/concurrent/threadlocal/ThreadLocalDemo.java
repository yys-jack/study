package com.wx.study.threadlocal;

public class ThreadLocalDemo {
    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            ThreadLocal threadLocal = new ThreadLocal();
            threadLocal.set("hello");
            Object o = threadLocal.get();
            System.out.println(Thread.currentThread().getName() + o);
        });
        thread.start();
        ThreadLocal threadLocal = new ThreadLocal();
        threadLocal.set(123);
        Object o = threadLocal.get();
        System.out.println(Thread.currentThread().getName() + o);
    }
}

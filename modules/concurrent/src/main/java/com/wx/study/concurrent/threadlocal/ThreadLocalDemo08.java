package com.wx.study.threadlocal;


import java.lang.management.ManagementFactory;
import java.lang.ref.Reference;
import java.lang.reflect.Array;
import java.lang.reflect.Field;


public class ThreadLocalDemo08 {
    public static ThreadLocal<Integer> holder = new ThreadLocal<>();


    public static void main(String[] args) throws NoSuchFieldException {

        Thread thread = new Thread(() -> {
            holder.set(123);
            try {
                Thread.sleep(20000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            cleanThreadLocals();
            System.out.println(Thread.currentThread().getName() + holder.get());
        });
        thread.start();
        System.gc();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println(thread);
    }

    private static void cleanThreadLocals() {
        try {
            // Get a reference to the thread locals table of the current thread
            Thread thread = Thread.currentThread();
            System.out.println(thread);
            Field threadLocalsField = Thread.class.getDeclaredField("threadLocals");
            threadLocalsField.setAccessible(true);
            Object threadLocalTable = threadLocalsField.get(thread);

            // Get a reference to the array holding the thread local variables inside the
            // ThreadLocalMap of the current thread
            Class threadLocalMapClass = Class.forName("java.lang.ThreadLocal$ThreadLocalMap");
            Field tableField = threadLocalMapClass.getDeclaredField("table");
            tableField.setAccessible(true);
            Object table = tableField.get(threadLocalTable);

            // The key to the ThreadLocalMap is a WeakReference object. The referent field of this object
            // is a reference to the actual ThreadLocal variable
            Field referentField = Reference.class.getDeclaredField("referent");
            referentField.setAccessible(true);

            for (int i = 0; i < Array.getLength(table); i++) {
                // Each entry in the table array of ThreadLocalMap is an Entry object
                // representing the thread local reference and its value
                Object entry = Array.get(table, i);
                if (entry != null) {
                    // Get a reference to the thread local object and remove it from the table
                    ThreadLocal threadLocal = (ThreadLocal) referentField.get(entry);
//                    threadLocal.remove();
                }
            }
        } catch (Exception e) {
            // We will tolerate an exception here and just log it
            throw new IllegalStateException(e);
        }
    }
}




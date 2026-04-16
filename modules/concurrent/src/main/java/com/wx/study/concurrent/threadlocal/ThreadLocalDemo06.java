package com.wx.study.threadlocal;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadLocalDemo06 {

    public static ExecutorService threadPool = Executors.newFixedThreadPool(16);

    public static void main(String[] args) throws InterruptedException {

        for (int i = 0; i < 1000; i++) {

            int finalI = i;

            threadPool.submit(() -> {

                String date = new ThreadLocalDemo06().date(finalI);


                System.out.println(date);

            });

        }

        threadPool.shutdown();

    }

    public String date(int seconds) {

        try{
            Date date = new Date(1000 * seconds);

            SimpleDateFormat dateFormat = ThreadSafeFormatter.dateFormatThreadLocal.get();
            return dateFormat.format(date);
        }finally {
            ThreadSafeFormatter.dateFormatThreadLocal.remove();
        }

    }

}

class ThreadSafeFormatter {

    public static ThreadLocal<SimpleDateFormat> dateFormatThreadLocal = ThreadLocal.withInitial(() -> new SimpleDateFormat("mm:ss"));

}
package com.wx.study.concurrent.executor.timer;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.TimerTask;

public class NewsletterTask extends TimerTask {
    @Override
    public void run() {
        try {
            System.out.println("Email sent at: "
              + LocalDateTime.ofInstant(Instant.ofEpochMilli(scheduledExecutionTime()),
              ZoneId.systemDefault()));

            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }
}
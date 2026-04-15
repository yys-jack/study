package com.wx.study.executor.timer;

import lombok.SneakyThrows;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.TimerTask;

public class NewsletterTask extends TimerTask {
    @SneakyThrows
    @Override
    public void run() {
        System.out.println("Email sent at: " 
          + LocalDateTime.ofInstant(Instant.ofEpochMilli(scheduledExecutionTime()),
          ZoneId.systemDefault()));

        Thread.sleep(2000);
    }
}
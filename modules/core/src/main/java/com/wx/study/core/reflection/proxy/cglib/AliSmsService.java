package com.wx.study.core.reflection.proxy.cglib;

public class AliSmsService {
    public String send(String message) {
        System.out.println("send message:" + message);
        return message;
    }
}

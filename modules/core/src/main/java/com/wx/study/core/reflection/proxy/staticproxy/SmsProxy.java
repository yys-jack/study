package com.wx.study.core.proxy.staticproxy;

import com.wx.study.core.proxy.SmsService;
import com.wx.study.core.proxy.SmsServiceImpl;

public class SmsProxy implements SmsService {
    private final SmsService smsService;

    public SmsProxy(SmsService smsService) {
        this.smsService = smsService;
    }

    @Override
    public String send(String message) {
        System.out.println("before method send()");
        smsService.send(message);
        System.out.println("after method send()");
        return message;
    }

    public static void main(String[] args) {
        SmsProxy smsProxy=new SmsProxy(new SmsServiceImpl());
        smsProxy.send("hello world");
    }
}

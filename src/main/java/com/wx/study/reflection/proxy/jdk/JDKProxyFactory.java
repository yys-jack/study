package com.wx.study.proxy.jdk;

import com.wx.study.proxy.SmsService;
import com.wx.study.proxy.SmsServiceImpl;

import java.lang.reflect.Proxy;

/**
 * https://javaguide.cn/java/basis/%E4%BB%A3%E7%90%86%E6%A8%A1%E5%BC%8F%E8%AF%A6%E8%A7%A3/#_3-2-cglib-%E5%8A%A8%E6%80%81%E4%BB%A3%E7%90%86%E6%9C%BA%E5%88%B6
 * <p>
 * <p>
 * jdk动态代理    cglib动态代理区别
 * 接口          类
 */
public class JDKProxyFactory {
    public static Object getProxy(Object target) {
        return Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                new DebugInvocationHandler(target)
        );
    }

    public static void main(String[] args) {
        SmsService smsService = (SmsService) JDKProxyFactory.getProxy(new SmsServiceImpl());
        smsService.send("hello");
    }
}

package com.wx.study.proxy.cglib;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class DebugMethodInterceptor implements MethodInterceptor {
    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        System.out.println("before method " + method.getName() + "");
        Object o = proxy.invokeSuper(obj, args);
        System.out.println("after method " + method.getName() + "");
        return o;
    }
}

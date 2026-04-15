package com.wx.study.jvm;

import java.lang.management.ManagementFactory;
import java.net.URL;
import java.net.URLClassLoader;

public class JvmClassLoaderPrintPath {

    public static void main(String[] args) {
        // Java 21+ 使用 ManagementFactory 获取类加载器信息
        System.out.println("Java 21+ 类加载器信息:");

        // 获取当前类加载器层级
        ClassLoader current = JvmClassLoaderPrintPath.class.getClassLoader();
        System.out.println("应用类加载器：" + current.getClass().getName());

        ClassLoader ext = current.getParent();
        System.out.println("平台类加载器：" + (ext != null ? ext.getClass().getName() : "null"));

        ClassLoader bootstrap = ext != null ? ext.getParent() : null;
        System.out.println("启动类加载器：" + (bootstrap != null ? bootstrap.getClass().getName() : "null (bootstrap)"));

        // 打印类加载器路径
        if (current instanceof URLClassLoader) {
            URL[] urls = ((URLClassLoader) current).getURLs();
            for (URL url : urls) {
                System.out.println("  -> " + url.toExternalForm());
            }
        }
    }
}

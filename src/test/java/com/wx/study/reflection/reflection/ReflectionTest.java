package com.wx.study.reflection;

import org.junit.jupiter.api.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

class ReflectionTest {
    /**
     * 获取类上的注解
     */
    @Test
    public void test() {
        Annotation[] all = Parent.class.getAnnotations();
        Arrays.stream(all).forEach(System.out::println);
    }

    /**
     * 获取方法上的注解
     * 步骤   1   获取类的方法，   2   获取方法上所有注解
     */
    @Test
    public void test2() {
        Method[] methods = Parent.class.getMethods();
        Arrays.stream(methods).forEach(System.out::println);
        for (Method method : methods) {
            Annotation[] annotations = method.getAnnotations();
            Arrays.stream(annotations).forEach(System.out::println);
        }
    }

    /**
     * 获取类字段上的注解
     * 步骤    1   获取类的字段    2   获取字段上所有注解
     */
    @Test
    public void test3() {
        Field[] fields = Parent.class.getFields();
        Arrays.stream(fields).forEach(System.out::println);
        for (Field field : fields) {
            Annotation[] annotations = field.getAnnotations();
            Arrays.stream(annotations).forEach(System.out::println);
        }
    }

    /**
     * 获取运行时对象，      获取获取类上注解
     */
    @Test
    public void test4() {
        Parent parent = new Parent();
        Annotation[] annotations = parent.getClass().getAnnotations();
        Arrays.stream(annotations).forEach(System.out::println);
    }

}
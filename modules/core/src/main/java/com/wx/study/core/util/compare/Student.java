package com.wx.study.core.util.compare;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wxli
 * @date 2021/7/21 10:24
 */
public class Student {
    private String name;
    private Integer age;

    public Student(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }
}
class MaxAge{
    public static void main(String[] args) {


        List<Student> list=new ArrayList<>();
        list.add(new Student("zhangsan",19));
        list.add(new Student("lisi",20));
        list.add(new Student("wangmazi",-123));
        System.out.println(list.stream().max(MaxAge::compare).get());
    }

    private static int compare(Student o1, Student o2) {
        return o1.getAge().compareTo(o2.getAge());
    }
}
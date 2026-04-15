package com.wx.study.introduction4.chapter1_1.exercise;

import org.junit.Test;

/**
 * @author wxli
 * @date 2021/7/24 17:44
 */
public class Example1_1 {

    @Test
    public void test1_1_1(){
        //a
        int i = (0 + 15)/2;
        System.out.println(i);

        //b
        System.out.println(2.0e-6 * 10000000.1);

        //c   true && false || true && true

        System.out.println(true && false);

        System.out.println(true && false||true);

        System.out.println(true && false || true && true);
    }

    @Test
    public void test1_1_2(){
        //a
        System.out.println((1 + 2.362) / 2);
        //b
        System.out.println(1 + 2 + 3 + 4.0);
        //c
        System.out.println(4.1 >= 4);
        //d
        System.out.println(1 + 2 + "3");
    }


}

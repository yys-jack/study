package com.wx.study.introduction4.chapter1_1.exercise;

import java.util.Random;

/**
 * @author wxli
 * @date 2021/7/24 18:05
 */
public class Example1_1_5 {

    public static void main(String[] args) {
        double x, y;
        Random random = new Random();
        x = 1 + random.nextDouble();
        y = random.nextDouble();
        if ((x > 0 && x < 1) && (y > 0 && y < 1)) {
            System.out.println(x + "\n" + y);
            System.out.println(true);
        } else {
            System.out.println(false);
        }
    }
}

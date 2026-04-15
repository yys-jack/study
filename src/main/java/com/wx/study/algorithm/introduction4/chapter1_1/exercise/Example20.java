package com.wx.study.introduction4.chapter1_1.exercise;

public class Example20 {
    public static int factorial(int n) {
        if (n == 0 || n == 1) {
            return 1;
        }
        return factorial(n - 1) * n;
    }

    public static void main(String[] args) {
        System.out.println(Math.log(factorial(5)));
    }
}

package com.wx.study.introduction4.chapter1_1.exercise;

public class Example19 {
    public static long f(int n) {
        if (n == 0) return n;
        if (n == 1) return n;
        return f(n - 1) + f(n - 2);
    }

    public static long[] f2(int n) {
        long[] fibonacci = new long[n + 1];
        if (n == 0) return fibonacci;
        fibonacci[1] = 1;
        if (n == 1) return fibonacci;
        for (int i = 2; i <= n; i++) {
            fibonacci[i] = fibonacci[i - 1] + fibonacci[i - 2];
        }
        return fibonacci;
    }
}

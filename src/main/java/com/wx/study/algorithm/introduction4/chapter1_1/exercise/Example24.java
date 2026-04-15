package com.wx.study.introduction4.chapter1_1.exercise;

import java.util.Scanner;

public class Example24 {
    public static int gcd(int p, int q) {
        System.out.println(p + " " + q);
        if (q == 0)
            return p;
        int r = p % q;
        return gcd(q, r);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int p = scanner.nextInt();
        int q = scanner.nextInt();
        System.out.println(gcd(p, q));
    }
}

package com.wx.study.niuke.demo;

import java.util.Arrays;
import java.util.Scanner;


public class StringOrder_8 {
    public static void main(String[] args) {
        //输入
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        String[] strs = new String[n];
        for (int i = 0; i < n; i++) {
            strs[i] = scanner.next();
        }
        Arrays.sort(strs);
        for (int i = 0; i < n; i++) {
            if (i == n - 1) {
                System.out.println(strs[i]);
            } else {
                System.out.print(strs[i] + " ");
            }
        }
    }
}

package com.wx.study.niuke.demo;

import java.util.Arrays;
import java.util.Scanner;


public class StringOrder_9 {
    public static void main(String[] args) {
        //输入
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()){
            String[] strs = scanner.nextLine().split(" ");
            Arrays.sort(strs);
            for (int i = 0; i < strs.length; i++) {
                if (i == strs.length - 1) {
                    System.out.println(strs[i]);
                } else {
                    System.out.print(strs[i] + " ");
                }
            }
        }
    }
}

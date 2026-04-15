package com.wx.study.niuke.demo;

import java.util.Arrays;
import java.util.Scanner;


public class StringOrder_11 {
    public static void main(String[] args) {
        //输入
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String[] str = scanner.nextLine().split(" ");
            int length = str.length;
            long[] nums = new long[length];
            for (int i = 0; i < length; i++) {
                nums[i] = Long.parseLong(str[i]);
            }
            System.out.println(Arrays.stream(nums).sum());
        }
    }
}

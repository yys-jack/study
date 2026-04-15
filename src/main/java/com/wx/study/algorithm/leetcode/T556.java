package com.wx.study.leetcode;

import java.util.Arrays;

public class T556 {
    public int nextGreaterElement(int n) {
        // 1. toString
        // 2. char 降序
        // 3. 转long  然后比较integer_max   超过   -1    不超过  return
        String str = n + "";
        char[] chars = str.toCharArray();
        long num = Long.parseLong(new String(chars));
        if (num < Integer.MIN_VALUE || num > Integer.MAX_VALUE) return -1;
        if (n == num) return -1;
        return (int) num;
    }



    public static void main(String[] args) {

    }
}

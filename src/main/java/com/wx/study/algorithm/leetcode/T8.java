package com.wx.study.leetcode;

/**
 * https://leetcode.cn/problems/string-to-integer-atoi/
 */
public class T8 {
    public int myAtoi(String s) {
        //1 修剪，去除前后空格
        s = s.trim();
        if (s.isEmpty()) return 0;

        //有符号
        int index = 0;
        int sign = 1;
        if (s.charAt(0) == '-') {
            index++;
            sign = -1;
        }
        if (s.charAt(0) == '+') {
            index++;
            sign = 1;
        }

        int res = 0;
        while (index < s.length()) {
            char currChar = s.charAt(index);
            // 4.1 先判断不合法的情况
            if (currChar > '9' || currChar < '0') {
                break;
            }

            // 题目中说：环境只能存储 32 位大小的有符号整数，因此，需要提前判：断乘以 10 以后是否越界
            if (res > Integer.MAX_VALUE / 10 || (res == Integer.MAX_VALUE / 10 && (currChar - '0') > Integer.MAX_VALUE % 10)) {
                return Integer.MAX_VALUE;
            }
            if (res < Integer.MIN_VALUE / 10 || (res == Integer.MIN_VALUE / 10 && (currChar - '0') > -(Integer.MIN_VALUE % 10))) {
                return Integer.MIN_VALUE;
            }

            // 4.2 合法的情况下，才考虑转换，每一步都把符号位乘进去
            res = res * 10 + sign * (currChar - '0');
            index++;
        }
        return res;
    }

    public static void main(String[] args) {
        System.out.println(new T8().myAtoi(
                "20000000000000000000"));
    }
}

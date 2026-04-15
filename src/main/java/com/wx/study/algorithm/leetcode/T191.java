package com.wx.study.leetcode;

public class T191 {
    public int hammingWeight(int n) {
        int res = 0;
        //无符号向右移动 i 位 每次都和1进行与运算
        for (int i = 0; i < 32; i++) {
            if (((n >>> i) & 1) == 1) res++;
        }
        return res;
    }
}

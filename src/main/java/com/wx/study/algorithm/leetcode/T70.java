package com.wx.study.leetcode;

/**
 * https://leetcode.cn/problems/climbing-stairs/
 */
public class T70 {
    // p  q  r
    // 0  0  1
    // 0  1  1
    // q  r  q+r
    // 滚动数组
    public int climbStairs(int n) {
        int p = 0, q = 0, r = 1;
        for (int i = 1; i <= n; i++) {
            p = q;
            q = r;
            r = p + q;
        }
        return r;
    }

    //递归
    public int climbStairs_2(int n) {
        if (n == 1) {
            return 1;
        }
        if (n < 3) {
            return n;
        }
        return climbStairs_2(n - 1) + climbStairs_2(n - 2);
    }
}

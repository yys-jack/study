package com.wx.study.leetcode;

/**
 * https://leetcode.cn/problems/hamming-distance/
 */
public class T461 {
    public int hammingDistance(int x, int y) {
        //先异或
        int xor = x ^ y;
        //在统计1
        int res = 0;
        while (xor != 0) {
            res += xor & 1;
            xor = xor >>> 1;
        }
        return res;
    }
}

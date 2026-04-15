package com.wx.study.leetcode.slidingwindow;

import java.util.Arrays;

/**
 * https://leetcode.cn/problems/missing-number/
 */
public class T268 {
    public int missingNumber(int[] nums) {
        // 范围[0,nums.length]
        // 构造 int[nums.length+1]
        int res = 0;
        int n = nums.length;
        int m = n + 1;
        int[] temp = new int[m];
        for (int i = 0; i < n; i++) {
            temp[nums[i]]++;
        }
        for (int i = 0; i < m; i++) {
            if (temp[i] == 0) res = i;
        }
        return res;
    }

    public static void main(String[] args) {
        System.out.println(new T268().missingNumber(new int[]{3, 0, 1}));
    }
}

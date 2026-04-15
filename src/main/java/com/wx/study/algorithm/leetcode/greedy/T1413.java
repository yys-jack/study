package com.wx.study.leetcode.greedy;

/**
 * https://leetcode.cn/problems/minimum-value-to-get-positive-step-by-step-sum/
 */
public class T1413 {
    public int minStartValue(int[] nums) {
        int sum = 0,sumMin = 0;
        for(int num:nums){
            sum += num;
            sumMin = Math.min(sum,sumMin);
        }
        return -sumMin+1;
    }
}

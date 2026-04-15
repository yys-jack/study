package com.wx.study.leetcode;

/**
 * https://leetcode.cn/problems/best-time-to-buy-and-sell-stock-ii/
 */
public class T122 {
    public int maxProfit(int[] prices) {
        int n = prices.length;
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                max = Math.max(max, prices[j] - prices[i]);
            }
        }
        return max;
    }

}

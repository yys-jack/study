package com.wx.study.leetcode;

public class T121 {
    //    public int maxProfit(int[] prices) {
//        int maxProfit = 0;
//        int pre;
//        for (int i = 0; i < prices.length - 1; i++) {
//            for (int j = i + 1; j < prices.length; j++) {
//                pre = prices[j] - prices[i];
//                if (pre > maxProfit) {
//                    maxProfit = pre;
//                }
//            }
//        }
//        return maxProfit;
//    }
    public int maxProfit(int[] prices) {
        int minPrice = Integer.MAX_VALUE;     //历史低点
        int maxProfit = 0;                    //最大利润
        for (int i = 0; i < prices.length; i++) {
            if (minPrice > prices[i]) {             //找到历史低点
                minPrice = prices[i];
            } else if (prices[i] - minPrice > maxProfit) {      //第i天利润 即 prices[i] - minPrice 与前面最大利润比较 找出最大。
                maxProfit = prices[i] - minPrice;
            }
        }
        return maxProfit;
    }
}

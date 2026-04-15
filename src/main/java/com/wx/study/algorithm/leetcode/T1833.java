package com.wx.study.leetcode;

import java.util.Arrays;

/**
 * @author wxli
 * @date 2021/7/2 17:41
 */
public class T1833 {
    public static int maxIceCream(int[] costs, int coins) {
        Arrays.sort(costs);
        int i = 0;
        for (int cost : costs) {
            if (coins < cost) {
                return i;
            } else {
                if (coins > 0) {
                    coins = coins - cost;
                    i++;
                }
            }
        }

        return i;
    }

    public static void main(String[] args) {
        int[] costs = {1, 3, 2, 4, 1};
        int coins = 7;
        int i = maxIceCream(costs, coins);
        System.out.println(i);
    }
}

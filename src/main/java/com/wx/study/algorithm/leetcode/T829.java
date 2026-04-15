package com.wx.study.leetcode;

/**
 * https://leetcode.cn/problems/consecutive-numbers-sum/
 */
public class T829 {
    public int consecutiveNumbersSum(int n) {
        //1->n  项数
        //an = min*i +
        //sn = (a1+an)*n/2
        //
        int curr = 0;
        for (int i = 1; i <= n; i++) {   //项数
            int min = (n * 2 / i - i + 1) / 2;        //最小min
            if (min > 0 && n == min * i + (i * (i - 1) / 2)) {     //min要大于0且满足等差数列公式
                curr++;
            }
        }
        return curr;
    }
}

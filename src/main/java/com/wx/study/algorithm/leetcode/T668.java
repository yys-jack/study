package com.wx.study.leetcode;

/**
 * https://leetcode.cn/problems/kth-smallest-number-in-multiplication-table/
 *
 * 出错次数     1
 */
public class T668 {

    public int findKthNumber(int m, int n, int k) {
        int left = 0;
        int right = m * n;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (getCount(mid, m, n) >= k) {    //取mid看其是否有K个数满足小于mid
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }

    private int getCount(int x, int m, int n) {
        //寻找多少数小于x
        int i = m, j = 1;
        int ret = 0;
        while (i >= 1 && j <= n) {
            if (i * j <= x) {
                ret += i;     //加整列
                j++;
            } else {
                i--;          //加不了，向下移
            }
        }
        return ret;
    }

    public static void main(String[] args) {
        new T668().findKthNumber(3,3,5);
    }
}

package com.wx.study.leetcode;

import java.util.Arrays;

/**
 * https://leetcode.cn/problems/merge-sorted-array/
 */
public class T88 {
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        //定义双指针
        int p = 0, q = 0;
        int[] sorted = new int[m + n];
        int cur;
        while (p < m || q < n) {
            if (p == m) {                  //边界条件
                cur = nums2[q++];
            } else if (q == n) {
                cur = nums1[p++];
            } else if (nums1[p] < nums2[q]) {
                cur = nums1[p++];
            } else {
                cur = nums2[q++];
            }
            sorted[p + q - 1] = cur;
        }
        for (int i = 0; i < m + n; i++) {
            nums1[i] = sorted[i];
        }
    }

    //合并后排序
    public void merge_2(int[] nums1, int m, int[] nums2, int n) {
        for (int i = 0; i < n; i++) {
            nums1[m + i] = nums2[i];
        }
        Arrays.sort(nums1);
    }
}

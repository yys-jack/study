package com.wx.study.leetcode;

import java.util.*;

class T350 {
    public int[] intersect(int[] nums1, int[] nums2) {

        int m = nums1.length;
        int n = nums2.length;
        //将数组长度短的放入hash表
        if (m > n) {
            return intersect(nums2, nums1);
        }
        Map<Integer, Integer> map = new HashMap<>();
        int[] ints = new int[m];
        int index = 0;
        for (int j : nums2) {
            if (map.containsKey(j)) {
                map.put(j, map.get(j) + 1);
            } else {
                map.put(j, 1);
            }
        }
        for (int i : nums1) {
            if (map.get(i) != null && map.get(i) > 0) {
                map.put(i, map.get(i) - 1);
                ints[index++] = i;
            }
        }
        return Arrays.copyOfRange(ints, 0, index);

    }
}
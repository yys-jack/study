package com.wx.study.leetcode;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * https://leetcode.cn/problems/n-repeated-element-in-size-2n-array/submissions/
 */
public class T961 {
    public int repeatedNTimes(int[] nums) {
        int m = nums.length;
        int n = m / 2;
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < m; i++) {
            if (map.containsKey(nums[i])) {
                map.put(nums[i], map.get(nums[i]) + 1);
                if (map.get(nums[i]) == n) {
                    return nums[i];
                }
            } else {
                map.put(nums[i], 1);
            }
        }
        return -1;
    }

    //hash表
    public int repeatedNTimes_2(int[] nums) {
        int m = nums.length;
        int n = m / 2;
        Set<Integer> set = new HashSet<>();
        //set 添加成功返回ture，添加失败返回false
        for (Integer item : nums) {
            if (!set.add(item)) {
                return item;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        new T961().repeatedNTimes(new int[]{1, 2, 3, 3});
    }
}

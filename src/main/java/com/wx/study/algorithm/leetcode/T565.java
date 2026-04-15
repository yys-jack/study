package com.wx.study.leetcode;

import java.util.HashSet;
import java.util.Set;

public class T565 {
    public int arrayNesting(int[] nums) {
        //数组为1 直接返回
        if (nums.length == 1) {
            return 1;
        }
        Set<Integer> set = new HashSet<>();
        int N = nums.length;
        //记录上一个数
        int pre = nums[0];
        set.add(pre);
        for (int i = 0; i < N; i++) {
            if (!set.contains(nums[pre])) {
                set.add(nums[pre]);
            } else {
                pre = nums[pre];
            }
        }
        return set.size();
    }
}

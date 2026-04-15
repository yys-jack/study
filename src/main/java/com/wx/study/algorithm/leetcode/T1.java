package com.wx.study.leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * https://leetcode-cn.com/leetbook/read/top-interview-questions-easy/x2jrse/
 */
public class T1 {
    public int[] twoSum(int[] nums, int target) {
        for (int i = 0; i < nums.length - 1; i++) {
            int temp = nums[i];
            for (int j = i + 1; j < nums.length; j++) {
                if (target - temp == nums[j]) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{-1, -1};
    }

    public int[] twoSum_hash(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        //map   key nums[i],val   i
        //nums = {3,3}   target = 6
        //i = 0  put
        //i = 1  6 - 3 = 3   get(3) = 0;
        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(target - nums[i])) {
                return new int[]{map.get(target - nums[i]), i};
            }
            map.put(nums[i], i);
        }
        return new int[]{-1, -1};
    }
}

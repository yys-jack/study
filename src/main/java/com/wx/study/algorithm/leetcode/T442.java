package com.wx.study.leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * https://leetcode-cn.com/problems/find-all-duplicates-in-an-array/
 */
public class T442 {
    public List<Integer> findDuplicates(int[] nums) {
        List<Integer> list = new ArrayList<>();
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            if (map.containsKey(num)) {
                list.add(num);
            } else {
                map.put(num, 1);
            }
        }
        return list;
    }

    public List<Integer> findDuplicates_2(int[] nums) {
        List<Integer> list = new ArrayList<>();
        for (int num : nums) {
            if (nums[Math.abs(num) - 1] < 0) {       //判断 nums[|nums[i]|-1]是否小于0
                list.add(Math.abs(num));             //说明该位置数已经被为相反数，可添加到list中
            } else {
                nums[Math.abs(num) - 1] *= -1;       //将nums[|nums[i]|-1]，取反
            }
        }
        return list;
    }

}

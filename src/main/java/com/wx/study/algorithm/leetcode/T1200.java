package com.wx.study.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * https://leetcode.cn/problems/minimum-absolute-difference/
 */
public class T1200 {
    public List<List<Integer>> minimumAbsDifference(int[] arr) {
        List<List<Integer>> res = new ArrayList<>();
        //先排序
        Arrays.sort(arr);
        //比较俩相邻的数，找出最小绝对值
        int minAbsValue = Integer.MAX_VALUE;
        for (int fast = 1, slow = 0; fast < arr.length; slow++, fast++) {
            minAbsValue = Math.min(minAbsValue, Math.abs(arr[fast] - arr[slow]));
        }
        //定义双指针，fast ，slow
        for (int fast = 1, slow = 0; fast < arr.length; slow++, fast++) {
            if (Math.abs(arr[fast] - arr[slow]) == minAbsValue) {
                List<Integer> list = new ArrayList<>();
                list.add(arr[slow]);
                list.add(arr[fast]);
                res.add(list);
            }
        }
        return res;
    }
}

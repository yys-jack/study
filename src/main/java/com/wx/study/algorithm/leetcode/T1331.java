package com.wx.study.leetcode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * https://leetcode.cn/problems/rank-transform-of-an-array/
 */
public class T1331 {
    public static int[] arrayRankTransform(int[] arr) {
        int m = arr.length;
        int[] res = new int[m];
        Map<Integer, Integer> map = new HashMap<>();
        int[] temp = Arrays.stream(arr).sorted().distinct().toArray();
        for (int i = 0; i < temp.length; i++) {
            map.put(temp[i], i);
        }
        for (int i = 0; i < m; i++) {
            res[i] = map.get(arr[i]) + 1;
        }
        return res;
    }

    public static void main(String[] args) {
        int[] arr = {37, 12, 28, 9, 100, 56, 80, 5, 12};
        System.out.println(Arrays.toString(arrayRankTransform(arr)));
    }
}

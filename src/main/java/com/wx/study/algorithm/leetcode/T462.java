package com.wx.study.leetcode;

import java.util.Arrays;

/**
 * https://leetcode.cn/problems/minimum-moves-to-equal-array-elements-ii/
 */
public class T462 {
    public int minMoves2(int[] nums) {
        Arrays.sort(nums);
        int ans = 0, i = 0, j = nums.length - 1;
        while (i < j) {
            ans += nums[j--] - nums[i++];
        }
        return ans;
    }

    public static void main(String[] args) {
        System.out.println(new T462().minMoves2(new int[]{203125577,-349566234,230332704,48321315,66379082,386516853,50986744,-250908656,-425653504,-212123143}));
        System.out.println(0x7fffffff);
    }
}

package com.wx.study.core.util.regex;

import java.util.Stack;

public class RegexMatches {
    public static int minSubArrayLen(int sum, int[] nums) {

        int min = 0;
        int left = 0;
        int right = 0;
        int cur = 0;

        while (right < nums.length && left <= right) {
            cur = cur + nums[right];
            if (cur > sum) {
                left++;
                right = left;
                cur = 0;
            } else if (cur < sum) {
                right++;
            } else {
                min = Math.min(min, right - left + 1);
            }
        }
        return min;
    }

    public static void main(String[] args) {
        minSubArrayLen(7, new int[]{2, 3, 1, 2, 4, 3});

    }
}

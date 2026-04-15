package com.wx.study.leetcode.slidingwindow;

public class HWT44 {
    /**
     * 1、有N个正整数组成的一个序列。给定整数sum，求长度最长的连续子序列，使他们的和等于sum，返回此子序列的长度，如果没有满足要求的序列，返回-1。
     * <p>
     * 输入描述:
     * 序列：1,2,3,4,2
     * sum：6
     * 输出描述:
     * 序列长度：3
     * <p>
     * 示例1
     * 输入
     * 1,2,3,4,2
     * 6
     * 输出
     * 3
     * 说明
     * 解释：1,2,3和4,2两个序列均能满足要求，所以最长的连续序列为1,2,3，因此结果为3
     * 示例2
     * 输入
     * 1,2,3,4,2
     * 20
     * 输出
     * -1
     * 说明
     * 解释：没有满足要求的子序列，返回-1
     * 备注:
     * 输入序列仅由数字和英文逗号构成，数字之间采用英文逗号分隔；
     * 序列长度：1 <= N <= 200；
     * 输入序列不考虑异常情况，由题目保证输入序列满足要求。
     */
    public static int fun(int sum, int[] nums) {
        if (nums == null) return -1;
        int max = -1;
        int left = 0;
        int right = 0;
        int cur = 0;
        while (right < nums.length) {
            cur += nums[right];
            if (cur > sum) {
                left++;
                right = left;
                cur = 0;
            } else if (cur < sum) {
                right++;
            } else {
                max = Math.max(max, right - left + 1);
            }
        }
        return max;
    }

    public static void main(String[] args) {
        System.out.println(fun(6, new int[]{1, 2, 3, 4, 2}));
        System.out.println(fun(20, new int[]{1, 2, 3, 4, 2}));
        System.out.println(fun(0, new int[]{1}));
        System.out.println(fun(1, new int[]{}));
        System.out.println(fun(1, null));
    }
}

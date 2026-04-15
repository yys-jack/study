package com.wx.study.leetcode;

public class T53 {
//    /**
//     * 若前一个元素大于0    则将其加到当前元素
//     * 若前一个元素小于等于0 则保持当前元素不变
//     * 动态规划方程
//     * f(i) = max(f(i-1)+nums[i])
//     *
//     * @param nums
//     * @return
//     */
//    public int maxSubArray(int[] nums) {
//        int pre = 0, maxAns = nums[0];
//        for (int x : nums) {
//            //pre来维护对于当前f(i)的f(i−1)的值是多少
//            pre = Math.max(pre + x, x);          //判断f(i-1)是否要加到当前数上
//            maxAns = Math.max(maxAns, pre);      //获取最大值
//        }
//        return maxAns;
//    }
    /**
     * 贪心算法
     *
     * @param nums
     * @return
     */
    public int maxSubArray(int[] nums) {
        int max = nums[0];
        int sum = 0;
        for (int j = 0; j < nums.length; j++) {
            sum += nums[j];
            max = Math.max(max, nums[j]);
            if (sum < 0) {
                sum = 0;
            }
        }

        return max;
    }

}

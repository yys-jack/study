package com.wx.study.leetcode;

public class T189 {

//    public void rotate(int[] nums, int k) {
//        int n = nums.length;
//        int[] newArr = new int[n];
//        for (int i = 0; i < n; ++i) {
//            newArr[(i + k) % n] = nums[i];
//        }
//        System.arraycopy(newArr, 0, nums, 0, n);
//    }


    public void rotate(int[] nums, int k) {
        k %= nums.length;                 //尾部 k mod n 个元素会移动至数组头部
        flip(nums, 0, nums.length - 1);
        flip(nums, 0, k - 1);
        flip(nums, k, nums.length - 1);
    }

    /**
     * 翻转数组
     *
     * @param nums
     * @param start 起始位置
     * @param end   结束位置
     */
    public void flip(int[] nums, int start, int end) {
        while (start < end) {
            int temp = nums[start];
            nums[start] = nums[end];
            nums[end] = temp;
            start++;
            end--;
        }
    }
}

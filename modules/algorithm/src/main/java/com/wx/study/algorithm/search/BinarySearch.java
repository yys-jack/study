package com.wx.study.algorithm.search;


/**
 * 二分搜索
 * 标记左，右，中间位置。
 * 拿key和中间位置比较。
 */
public class BinarySearch {
    public int binarySearch(int[] nums, int key) {
        int lo = 0;
        int hi = nums.length - 1;
        while (lo <= hi) {
            int mid = lo + (lo + hi) / 2;
            if (nums[mid] > key) hi = mid - 1;
            else if (nums[mid] < key) lo = mid + 1;
            else return mid;
        }
        return -1;
    }

    /**
     * 递归
     * @param nums
     * @param key
     * @param lo    左标记
     * @param hi    右标记
     * @return
     */
    public int binarySearch(int[] nums, int key, int lo, int hi) {
        if (lo > hi) return -1;
        int mid = lo + (lo + hi) / 2;
        if (nums[mid] > key) return binarySearch(nums, key, lo, mid - 1);
        else if (nums[mid] < key) return binarySearch(nums, key, mid + 1, hi);
        else return mid;
    }
}

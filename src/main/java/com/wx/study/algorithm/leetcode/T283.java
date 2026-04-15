package com.wx.study.leetcode;

import java.util.Arrays;

/**
 * https://leetcode-cn.com/problems/move-zeroes/solution/
 */
public class T283 {
    public static void moveZeroes(int[] nums) {
        Arrays.sort(nums);
        //全为0 直接return
        int sum = Arrays.stream(nums).sum();
        if (sum == 0) {
            return;
        }
        //找到第一个不为0的位置    x
        int x = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                //记 i 为 x
                x = i;
                break;
            }
        }
        //移动次数即   length-x-1
        int moveNumber = nums.length - x;
        int curr = x;
        for (int j = 0; j < moveNumber && curr < nums.length; j++) {
            nums[j] = nums[curr++];
        }
        //后一段置为0
        int zero = moveNumber;
        while (zero < nums.length) {
            nums[zero++] = 0;
        }
        System.out.println(Arrays.toString(nums));
    }

    public void moveZeroes_2(int[] nums) {
        if (nums == null || nums.length == 0)
            return;
        int index = 0;
        //一次遍历，把非零的都往前挪
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0)
                nums[index++] = nums[i];
        }
        //后面的都是0,
        while (index < nums.length) {
            nums[index++] = 0;
        }
    }

    public void moveZeroes_3(int[] nums) {
        int left = 0;       //指向不为0的末尾
        int right = 0;      //一直往右移动
        int n = nums.length;
        while (right < n) {
            if (nums[right] != 0) {
                //交换
                swp(nums, left, right);
                left++;
            }
            right++;
        }
    }

    public void swp(int[] nums, int left, int right) {
        int temp = nums[left];
        nums[left] = nums[right];
        nums[right] = temp;
    }


    public static void main(String[] args) {
        moveZeroes(new int[]{0, 1});
    }
}

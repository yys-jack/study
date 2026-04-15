package com.wx.study.algorithm.huawei.0315;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Solution_0318_3 {
    static int[] handleInput() {
        Scanner scan = new Scanner(System.in);
        List<Integer> nums = new ArrayList<>();
        while (scan.hasNext()) {
            nums.add(scan.nextInt());
        }
        int[] input = new int[nums.size()];
        for (int i = 0; i < nums.size(); i++) {
            input[i] = nums.get(i);
        }
        return input;
    }

    static int func3(int[] nums) {
        if (nums == null) {
            return -1;
        }
        int n = nums.length;

        // -3 -1 5 7 11 15
        // 查找算法 ->  找到nums[i]与nums[j]，使得abs(nums[i] + nums[j]) 尽量为0
        // 二分适用于查找单个元素，该题不适用
        // 题目要求找两个元素  ->   双指针
        // 双指针 i j，i指向头，j指向尾  ->  i往右移动，结果变大；j往左移动，结果变小
        // 若当前结果 < target(0) ，i++；
        // 若当前结果 > target，j--
        int ret = Integer.MAX_VALUE;
        int i = 0, j = n - 1;
        while (i < j) {
            int sum = nums[i] + nums[j]; // 递增，nums[i]应为负数，nums[j]应为正数
            if (Math.abs(sum) == 0) {
                return 0;
            }
            ret = Math.min(ret, Math.abs(sum));

            if (Math.abs(nums[i]) > Math.abs(nums[j])) {
                // 若当前结果 < target(0) ，i++；
                i++;
            } else {
                // 若当前结果 > target，j--
                j--;
            }
        }
        return ret;
    }

    static void handleOutput(int ret) {
        System.out.println(ret);
    }

    public static void main(String[] args) {
        // 查找算法 ->  找到nums[i]与nums[j]，使得abs(nums[i] + nums[j]) 尽量为0
        int[] nums = handleInput();
        int ret = func3(nums);
        handleOutput(ret);

        // -15 -10 -4 -2 -1 0
    }
}

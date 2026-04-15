package com.wx.study.algorithm.huawei.0315;
import java.util.Scanner;
import java.util.Stack;

public class Solution_0318_2 {
    static int[] handleInput() {
        // 处理输入
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        int[] nums = new int[n];
        for (int i = 0; i < n; i++) {
            nums[i] = scan.nextInt();
        }
        return nums;
    }

    static int[] func2(int[] nums) {
        // 实现方法
        if (nums == null) {
            return null;
        }
        int n = nums.length;
        int[] ret = new int[n];

        // 3  2  1  4  5
        // 维护单调栈，栈内元素是递减的，栈内元素代表下标
        // 100 99 98 97 96 95 94 ... 0
        // O(N)
        Stack<Integer> minStk = new Stack<>();
        for (int i = 0; i < n; i++) {
            int value = nums[i];
            if (minStk.empty() || value <= nums[minStk.peek()]) {
                minStk.push(i);
                continue;
            }

            // 当前元素大于栈顶元素
            while (!minStk.empty() && value > nums[minStk.peek()]) {
                ret[minStk.peek()] = i;
                minStk.pop();
            }
            minStk.push(i);
        }

        // 暴力双层循环
        // 123 124 125 121 119 122 126 123
        // 1   1   4   2   1   1   1   0    sum
        //  100 99 98 97 96 95 94 ... 0
        // 100 * 99 * 98 * 97  ~  100!   ->  O(N^2)
        /*for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                if (nums[j] > nums[i]) {
                    ret[i] = j;
                    break;
                }
            }
        }*/

        return ret;
    }

    static void handleOutput(int[] ret) {
        // 处理输出
        for (int x : ret) {
            System.out.print(x + " ");
        }
    }

    public static void main(String[] args) {
        // 2.找到右边第一个比当前元素大的数的下标
        int[] nums = handleInput();
        int[] ret = func2(nums);
        handleOutput(ret);
    }
}

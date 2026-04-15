package com.wx.study.algorithm.huawei.0319;
import java.util.Scanner;

public class Solution_0322_1 {
    static final int LEN = 10;

    static int hexToInt(String hexVal) {
        // hexVal.length()  1, 2
        int intVal = 0;
        int len = hexVal.length();
        for (int i = len - 1, j = 1; i >= 0; i--, j *= 16) {
            intVal += (hexVal.charAt(i) - '0') * j;
        }
        return intVal;
    }

    static int func1(int b, int c, int[] nums) {
        // type = sum(nums) % b    ->  type < c
        int[] map = new int[c];
        int ret = 0;
        for (int a : nums) {
            // 0x00101 -> 101
            String hex = Integer.toHexString(a);
            int sum = 0;
            for (int i = hex.length() - 1; i >= 0; i -= 2) {
                String val = hex.substring(Math.max(0, i - 1), i + 1);
                sum += hexToInt(val);
            }
            int remain = sum % b;
            if (remain < c) {
                map[remain]++;
                ret = Math.max(ret, map[remain]);
            }
        }
        return ret;
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int c = scan.nextInt(), b = scan.nextInt();
        int[] nums = new int[LEN];
        for (int i = 0; i < nums.length; i++) {
            nums[i] = scan.nextInt();
        }
        int ret = func1(b, c, nums);
        System.out.println(ret);
    }
}

package com.wx.study.leetcode;

/**
 * https://leetcode.cn/problems/di-string-match/
 */
public class T942 {
    public int[] diStringMatch(String s) {
        int length = s.length();
        int[] result = new int[length + 1];
        int num = 0;

        for (int i = 0; i < length; i++) {
            if (s.charAt(i) == 'I') {
                result[i] = num++;
            }
        }
        result[length] = num++;
        for (int j = length - 1; j >= 0; j--) {
            if (s.charAt(j) == 'D') {
                result[j] = num++;
            }
        }
        return result;
    }

    public int[] diStringMatch_2(String s) {
        int length = s.length();
        int[] result = new int[length + 1];
        int left = 0;
        int right = length;
        for (int i = 0; i < length; i++) {
            result[i] = s.charAt(i) == 'I' ? left++ : right--;
        }
        result[length] = left;
        return result;
    }
}

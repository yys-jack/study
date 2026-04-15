package com.wx.study.leetcode;

/**
 * https://leetcode.cn/problems/reverse-string/
 */
public class T344 {
    //遍历
    public void reverseString(char[] s) {
        for (int i = 0; i < s.length / 2; i++) {
            char temp = s[i];
            s[i] = s[s.length - 1 - i];
            s[s.length - 1 - i] = temp;
        }
    }

    //双指针
    public void reverseString_2(char[] s) {
        int left = 0;
        int right = s.length - 1;
        while (left < right) {
            char temp = s[left];
            s[left] = s[right];
            s[right] = temp;
            left++;
            right--;
        }
    }

    //递归
    public void reverseString_3(char[] s) {
        if (s == null || s.length == 0) return;
        reverseStringHelper(s, 0, s.length - 1);
    }

    public void reverseStringHelper(char[] s, int left, int right) {
        if (left >= right) return;
        swap(s, left, right);
        reverseStringHelper(s, ++left, --right);
    }

    private void swap(char[] array, int i, int j) {
        char temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}

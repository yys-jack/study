package com.wx.study.leetcode;

/**
 * https://leetcode.cn/problems/implement-strstr/
 */
public class T28 {
    public int strStr(String haystack, String needle) {
        return haystack.indexOf(needle);
    }

    //暴力破解
    public int strStr_2(String haystack, String needle) {
        int m = haystack.length();
        int n = needle.length();
        //needle和haystack子串均匹配一次。
        for (int i = 0; i + n <= m; i++) {
            //匹配标志
            boolean flag = true;
            for (int j = 0; j < n; j++) {
                if (haystack.charAt(i + j) != needle.charAt(j)) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                return i;
            }
        }
        return -1;
    }

    //kmp
    //todo
    public int strStr_3(String haystack, String needle) {
        return -1;
    }
}

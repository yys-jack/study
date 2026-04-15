package com.wx.study.leetcode;

/**
 * https://leetcode.cn/problems/longest-common-prefix/comments/
 */
public class T14 {
    public String longestCommonPrefix(String[] strs) {
        String first = strs[0];
        int index = 1;
        int len = strs.length;
        while (index < len) {
            first = commonPreFix(first, strs[index]);
            index++;
        }
        return first;
    }

    private String commonPreFix(String first, String str) {

        int m = first.length();
        int n = str.length();
        int len = Math.min(m, n);
        int count = 0;
        for (int i = 0; i < len; i++) {
            if (first.charAt(i) == str.charAt(i)) {
                count++;
            } else break;
        }
        return first.substring(0, count);
    }


}

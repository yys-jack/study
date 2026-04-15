package com.wx.study.leetcode;

/**
 * https://leetcode.cn/problems/unique-substrings-in-wraparound-string/
 */
public class T467 {
    public int findSubstringInWraproundString(String p) {
        int[] strs = new int[26];
        for (int i = 0; i < p.length(); i++) {
            if (strs[p.charAt(i) - 'a'] == 0) {
                strs[p.charAt(i) - 'a']++;
            }
        }
        int curr = 0;
        for (int i = 0; i < 26; i++) {
            if (strs[i] > 0) {
                curr++;
            }
        }
        int res = 1;
        for (int i = 1; i <= curr; i++) {
            res = i * res;
        }
        return res;
    }

    public static void main(String[] args) {
        System.out.println(new T467().findSubstringInWraproundString("cac"));
    }
}

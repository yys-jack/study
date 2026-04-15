package com.wx.study.leetcode.slidingwindow;

import java.util.HashSet;
import java.util.Set;


public class T3 {
    public static int lengthOfLongestSubstring(String s) {
        // 哈希集合，记录每个字符是否出现过
        Set<Character> occ = new HashSet<>();
        int n = s.length();
        // 右指针，初始值为 -1，相当于我们在字符串的左边界的左侧，还没有开始移动
        int rk = -1, ans = 0;
        for (int i = 0; i < n; ++i) {
            if (i != 0) {
                // 左指针向右移动一格，移除一个字符
                occ.remove(s.charAt(i - 1));
            }
            while (rk + 1 < n && !occ.contains(s.charAt(rk + 1))) {
                // 不断地移动右指针
                occ.add(s.charAt(rk + 1));
                ++rk;
            }
            // 第 i 到 rk 个字符是一个极长的无重复字符子串
            ans = Math.max(ans, rk - i + 1);
        }
        return ans;
    }

    public static int lengthOfLongestSubstring_2(String s) {
        int left = 0;
        int right = 0;
        int maxLen = 0;
        Set<Character> set = new HashSet<>();
        while (true) {
            int n = s.length();
            if (!(left < n)) break;
            //左指针不断向右移动，每移动一次，即删除前面一个字符
            if (left != 0) {
                set.remove(s.charAt(left - 1));
            }
            //右指针一直向右移动
            while (right < n && !set.contains(s.charAt(right))) {
                set.add(s.charAt(right));
                right++;
            }
            maxLen = Math.max(maxLen, right - left);
            left++;
        }
        return maxLen;
    }


    public static void main(String[] args) {
        lengthOfLongestSubstring("abcabcbb");
    }
}

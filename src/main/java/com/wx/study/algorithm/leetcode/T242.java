package com.wx.study.leetcode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * https://leetcode.cn/problems/valid-anagram/
 */
public class T242 {
    //hash
    public boolean isAnagram(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            map.put(s.charAt(i), map.getOrDefault(s.charAt(i), 0) + 1);
        }
        for (int i = 0; i < t.length(); i++) {
            Integer count = map.get(t.charAt(i));
            if (count == null) {
                return false;
            }
            if (count == 1) {
                map.remove(t.charAt(i));
            } else {
                map.put(t.charAt(i), --count);
            }
        }
        return true;
    }

    //数组
    public boolean isAnagram_2(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }
        int[] letterCounts = new int[26];
        for (int i = 0; i < s.length(); i++) {
            letterCounts[s.charAt(i) - 'a']++;
        }
        for (int i = 0; i < t.length(); i++) {
            if (letterCounts[t.charAt(i) - 'a'] == 0) {
                return false;
            }
            letterCounts[t.charAt(i) - 'a']--;
        }
        return true;
    }

    //先排序，再比较
    public boolean isAnagram_3(String s, String t) {
        char[] sChar = s.toCharArray();
        char[] tChar = t.toCharArray();
        //对两个字符串中的字符进行排序
        Arrays.sort(sChar);
        Arrays.sort(tChar);
        return Arrays.equals(sChar, tChar);
    }
}
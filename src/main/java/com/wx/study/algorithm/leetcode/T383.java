package com.wx.study.leetcode;

import java.util.HashMap;
import java.util.Map;

public class T383 {
    public boolean canConstruct(String ransomNote, String magazine) {
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < magazine.length(); i++) {
            map.put(magazine.charAt(i), map.getOrDefault(magazine.charAt(i), 0) + 1);
        }
        for (int i = 0; i < ransomNote.length(); i++) {
            if (map.get(ransomNote.charAt(i)) == null) {
                return false;
            } else if (map.get(ransomNote.charAt(i)) < 0) {
                return false;
            }
        }
        return true;
    }
}

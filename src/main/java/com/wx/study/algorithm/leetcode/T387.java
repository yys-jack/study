package com.wx.study.leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * https://leetcode.cn/problems/first-unique-character-in-a-string/solution/
 */
public class T387 {
    //hash
    public int firstUniqChar(String s) {
        //map  key s[i]  val count
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            map.put(s.charAt(i), map.getOrDefault(s.charAt(i), 0) + 1);
        }
        for (int j = 0; j < s.length(); j++) {
            if (map.get(s.charAt(j)) == 1) {
                return j;
            }
        }
        return -1;
    }

    //java api
    public int firstUniqChar_2(String s) {
        for (int i = 0; i < s.length(); i++)
            if (s.indexOf(s.charAt(i)) == s.lastIndexOf(s.charAt(i)))
                return i;
        return -1;
    }

}

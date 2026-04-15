package com.wx.study.leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * https://leetcode.cn/problems/roman-to-integer/
 */
public class T13 {
    public int romanToInt(String s) {
        int sum = 0;
        Map<String, Integer> map = new HashMap<>(8);
        map.put("I", 1);
        map.put("V", 5);
        map.put("X", 10);
        map.put("L", 50);
        map.put("C", 100);
        map.put("D", 500);
        map.put("M", 1000);
        for (int i = 0; i < s.length() - 1; i++) {
            //比较当前字符和下一个字符的大小
            if (map.get(s.charAt(i) + "") < map.get(s.charAt(i + 1) + "")) {
                //如果第二个字符比第一个字符大，则IV  =  5 - 1
                sum = sum - map.get(s.charAt(i) + "");
            } else {
                //如果第二个字符不大于第一个字符  有  II = 1 + 1
                sum = sum + map.get(s.charAt(i) + "");
            }
        }
        return sum + map.get(s.charAt(s.length() - 1) + "");
    }
}
package com.wx.study.algorithm.huawei.0319;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Solution_0322_2 {
    static class myQueue extends PriorityQueue<String> {
        /**
         * ① 取长度最长的单词
         * ② 如果长度也相等，取字典序最小的单词；
         */
        myQueue() {
            super((s1, s2) -> {
                if (s1.length() != s2.length()) {
                    return s2.length() - s1.length();
                }
                return s1.compareTo(s2);
            });
        }
    }

    static String func2(String[] strs, int index) {
        // key为char, value为 PriorityQueue<String>：表示以char为头字符的字符串集合
        Map<Character, PriorityQueue<String>> map = new HashMap<>();
        int n = strs.length;
        for (int i = 0; i < n; i++) {
            if (i == index) {
                continue;
            }
            String s = strs[i];
            char key = s.charAt(0);
            if (!map.containsKey(key)) {
                map.put(key, new myQueue());
            }
            map.get(key).offer(s);
        }

        // debug print
        System.out.println(map.toString());

        StringBuilder ret = new StringBuilder(strs[index]);
        char lastCh = ret.charAt(ret.length() - 1);
        while (map.containsKey(lastCh) && !map.get(lastCh).isEmpty()) {
            String s = map.get(lastCh).poll();
            ret.append(s);
            lastCh = ret.charAt(ret.length() - 1);
        }
        return ret.toString();
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int index = scan.nextInt();
        int n = scan.nextInt();
        String[] strs = new String[n];
        for (int i = 0; i < n; i++) {
            strs[i] = scan.next();
        }
        String ret = func2(strs, index);
        System.out.println(ret);
    }
}
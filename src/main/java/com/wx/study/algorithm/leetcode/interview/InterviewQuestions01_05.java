package com.wx.study.leetcode.interview;

import java.util.Objects;

/**
 * https://leetcode.cn/problems/one-away-lcci/
 */
public class InterviewQuestions01_05 {
    public boolean oneEditAway(String first, String second) {
        if (first.equals(second)) {
            return true;
        }
        int m = first.length();
        int n = second.length();
        //比较字符串长度相差超过2位则  false
        if (Math.abs(m - n) > 2) {
            return false;
        }
        //字符串长度相差1位，则考虑删除长字符串中的一个字符
        if (Math.abs(m - n) == 1) {
            int index = 0;
            if (m > n) {
                //删first
                if (oneDelete(first, second, m, index)) return true;
            } else {
                //删second
                if (oneDelete(second, first, n, index)) return true;
            }
        }

        //字符串长度相等，则按位比较字符是否相等。且维护一个计数器，不相等字符个数  计数器个数大于1则false
        if (m == n) {
            int count = 0;
            for (int i = 0; i < m; i++) {
                if (first.charAt(i) != second.charAt(i)) {
                    count++;
                }
            }
            if (count > 1) {
                return false;
            }else {
                return true;
            }
        }
        return false;
    }

    private boolean oneDelete(String first, String second, int m, int index) {
        while (index < m) {
            StringBuilder stringBuilder = new StringBuilder(first);
            String tempFirst = stringBuilder.deleteCharAt(index).toString();
            index++;
            //比较两字符串
            if (Objects.equals(tempFirst, second)) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println(new InterviewQuestions01_05().oneEditAway("mart", "karma"));
    }

}

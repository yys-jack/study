package com.wx.study.leetcode;

import java.util.Stack;

/**
 * https://leetcode.cn/problems/remove-outermost-parentheses/
 */
public class T1021 {
    public String removeOuterParentheses(String s) {
        if (s.isEmpty()) return "";
        Stack<Character> stack = new Stack<>();
        StringBuilder stringBuilder = new StringBuilder();
        int left = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                stack.push('(');
            } else {
                stack.pop();
            }
            if (stack.isEmpty()) {
                stringBuilder.append(s, left + 1, i );
                left = i + 1;
            }
        }
        return stringBuilder.toString();
    }
}

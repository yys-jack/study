package com.wx.study.leetcode;

import java.util.Stack;

public class T20 {
    public boolean isValid(String s) {
        //将左括号压栈
        //遇到右括号出栈，判断括号类型是否一致。
        //判断栈是否为空
        if (s.length() % 2 != 0) {
            return false;
        }
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            Character c = s.charAt(i);
            if (c == '(' || c == '{' || c == '[') {
                stack.push(c);
            } else if (stack.isEmpty() || c == ')' && stack.pop() != '(' || c == ']' && stack.pop() != '[' || c == '}' && stack.pop() != '{') {
                return false;
            }
        }
        return stack.isEmpty();
    }
}

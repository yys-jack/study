package com.wx.study.introduction4.chapter1_3.stack;

import java.util.Stack;

/**
 * 用例 (1+(2+3))
 *
 * 将操作数压栈
 * 将运算符压栈
 * 忽略左括号
 * 在遇到右括号时，弹出一个运算符，弹出所需要数量的操作数，并运算，后将结果压栈
 */
public class Evaluate {

    public void calculate(String str){
        //符号栈
        Stack<String> ops = new Stack<>();
        //操作数栈
        Stack<Double> vals = new Stack<>();
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '(') ;
            else if (str.charAt(i) == '+') {
                ops.push(str.charAt(i) + "");
            } else if (str.charAt(i) == '-') {
                ops.push(str.charAt(i) + "");
            } else if (str.charAt(i) == '*') {
                ops.push(str.charAt(i) + "");
            } else if (str.charAt(i) == '/') {
                ops.push(str.charAt(i) + "");
            } else if (str.charAt(i) == ')') {
                //右括号，需要弹出栈中内容，运算符和操作数，计算结果并压栈
                String op = ops.pop();
                Double v = vals.pop();
                if (op.equals("+")) v = vals.pop() + v;
                else if (op.equals("-")) v = vals.pop() - v;
                else if (op.equals("*")) v = vals.pop() * v;
                else if (op.equals("/")) v = vals.pop() / v;
                vals.push(v);
            } else vals.push(Double.parseDouble(str.charAt(i) + ""));
        }
        System.out.println(vals.pop());
    }

}

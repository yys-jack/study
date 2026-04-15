package com.wx.study.leetcode;

import java.util.Arrays;
import java.util.Objects;
import java.util.Stack;

/**
 * https://leetcode-cn.com/problems/baseball-game/
 */
public class T682 {
    public int calPoints(String[] ops) {
        int score = 0;
        int[] record = new int[ops.length];
        int index = 0;
        //符号为+
        for (int i = 0; i < ops.length; i++) {
            if (Objects.equals(ops[i], "+")) {
                if (index >= 1) {
                    record[index] = record[index - 1] + record[index - 2];
                    index++;
                }
            } else if (ops[i].equals("D")) {
                record[index] = record[index-1] + record[index-1];
                index++;
            } else if (ops[i].equals("C")) {
                record[--index] = 0;
            } else {
                record[index++] = Integer.parseInt(ops[i]);
            }
        }
        score= Arrays.stream(record).sum();
        return score;
    }

    public static void main(String[] args) {
        new T682().calPoints(new String[] {"5","2","C","D","+"});
    }
}

package com.wx.study.leetcode;

import java.util.Set;
import java.util.TreeSet;

/**
 * todo
 */
public class T726 {
    public static String countOfAtoms(String formula) {
        char[] chars = formula.toCharArray();
        Set<String> set = new TreeSet<>();
        for (int i = 0; i < chars.length; i++) {
            //找括号位置，
            if (formula.contains("(")) {
                //解开一次
                if (chars[i] == '(') {
                    //得到左括号索引
                    if (formula.contains(")")) {

                    }


                } else {

                }
            } else {


            }
            //判断第二位是小写还是大写，还是数字
            //首先查看set是否有key，有取key+新key数，完毕之后存入set
            //没有括号set.toString()连接
        }

        return null;
    }

    public static void main(String[] args) {
        String s = "Mg(OH)2";
        countOfAtoms(s);
    }
}

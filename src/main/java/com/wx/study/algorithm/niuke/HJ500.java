package com.wx.study.niuke;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class HJ500 {
    /**
     * 输入描述:
     * 一个目标整数T (1 <=T462<= 1000)
     * 输出描述:
     * 该整数的所有表达式和表达式的个数。如果有多种表达式，输出要求为：
     * 1.自然数个数最少的表达式优先输出
     * 2.每个表达式中按自然数递增的顺序输出，具体的格式参见样例。在每个测试数据结束时，输出一行”Result:X”，其中X是最终的表达式个数。
     * 示例1
     * 输入
     * 9
     * 输出
     * 9=9
     * 9=4+5
     * 9=2+3+4
     * Result:3
     * 新思路：双指针
     * 1 2 3 4 5 6 7 8 9
     */
    public void fun(int num) {
        List<String> list = new ArrayList<>();
        for (int i = 1; i <= num; i++) {
            int sum = 0;
            StringBuilder sb = new StringBuilder().append(num).append("=");
            for (int j = i; sum <= num; j++) {
                sum += j;
                sb.append(j).append("+");
                if (num == sum) {
                    sb.deleteCharAt(sb.length() - 1);
                    list.add(sb.toString());
                    break;
                }
            }
        }
        list.sort(Comparator.comparingInt(String::length));
        for (String s : list) {
            System.out.println(s);
        }
        System.out.println("Result:" + list.size());

    }

    public static void fun_2(int num) {
        int size = 0;
        for (int i = 1; i <= num; i++) {
            int min = (num * 2 / i - i + 1) / 2;
            if (min > 0 && min * i + (i * (i - 1) / 2) == num) {
                StringBuilder sb = new StringBuilder().append(num).append("=");
                for (int j = 0; j < i; j++) {
                    sb.append(min + j).append("+");
                }
                size++;
                sb.deleteCharAt(sb.length() - 1);
                System.out.println(sb);
            }
        }
        System.out.println("Result:" + size);
    }

    public static void main(String[] args) {
        fun_2(9);
    }
    //1. a1 = sum - n(n-1)/2
    //2.
    //可知
    // an = a1 + (n-1)d     d为公差
    // sum = (a1+an)n/2
    //     = (a1+ (a1+(n-1)d))n/2
    //     = a1n +n(n-1)d/2
    //
    // a1   =  sum/n - n(n-1)d/2
    // 由题知单调递增 d = 1

}

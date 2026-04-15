package com.wx.study.niuke.demo;

import java.util.Scanner;

/**
 * 计算a+b
 * 打开以下链接可以查看正确的代码
 * 1
 * https://ac.nowcoder.com/acm/contest/5657#question
 *
 * 数据范围：数据组数满足  ， 数据大小满足
 *
 * 输入描述:
 * 输入包括两个正整数a,b(1 <= a, b <= 10^9),输入数据有多组, 如果输入为0 0则结束输入
 *
 * 输出描述:
 * 输出a+b的结果
 *
 * 输入例子1:
 * 1 5
 * 10 20
 * 0 0
 *
 * 输出例子1:
 * 6
 * 30
 */
public class Sum_A_B_3 {
    public static void main(String[] args) {
        //输入
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            int a = scanner.nextInt();
            int b = scanner.nextInt();
            if (a == 0 && b == 0) {
                break;
            }
            System.out.println(a + b);
        }
    }
}

package com.wx.study.niuke.demo;

import java.util.Arrays;
import java.util.Scanner;


/**
 * 链接：https://ac.nowcoder.com/acm/contest/5657/G
 * 来源：牛客网
 *
 * 计算一系列数的和
 * 打开以下链接可以查看正确的代码
 * https://ac.nowcoder.com/acm/contest/5657#question
 *
 *
 * 输入描述:
 * 输入数据有多组, 每行表示一组输入数据。
 *
 * 每行不定有n个整数，空格隔开。(1 <= n <= 100)。
 * 输出描述:
 * 每组数据输出求和的结果
 * 示例1
 * 输入
 * 1 2 3
 * 4 5
 * 0 0 0 0 0
 * 输出
 * 6
 * 9
 * 0
 */
public class Sum_A_B_7 {
    public static void main(String[] args) {
        //输入
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String inputStr = scanner.nextLine();
            String[] strs = inputStr.split(" ");
            int length = strs.length;
            int[] sum = new int[length];
            for (int i = 0; i < length; i++) {
                sum[i] = Integer.parseInt(strs[i]);
            }
            System.out.println(Arrays.stream(sum).sum());
        }
    }
}

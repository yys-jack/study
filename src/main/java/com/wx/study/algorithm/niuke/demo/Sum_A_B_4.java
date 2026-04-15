package com.wx.study.niuke.demo;

import java.util.Arrays;
import java.util.Scanner;

/**
 * 计算一系列数的和
 * 打开以下链接可以查看正确的代码
 * 1
 * https://ac.nowcoder.com/acm/contest/5657#question
 * <p>
 * 数据范围：数据组数满足  ，每组数据中整数个数满足  ，每组数据中的值满足
 * <p>
 * 输入描述:
 * 输入数据包括多组。
 * 每组数据一行,每行的第一个整数为整数的个数n(1 <= n <= 100), n为0的时候结束输入。
 * 接下来n个正整数,即需要求和的每个正整数。
 * <p>
 * 输出描述:
 * 每组数据输出求和的结果
 * <p>
 * 输入例子1:
 * 4 1 2 3 4
 * 5 1 2 3 4 5
 * 0
 * <p>
 * 输出例子1:
 * 10
 * 15
 */
public class Sum_A_B_4 {
//    public static void main(String[] args) {
//        Scanner sc = new Scanner(System.in);
//        while (sc.hasNext()) {
//            int sum = 0;
//            int n = sc.nextInt();
//            if (0 == n)
//                break;
//            for (int i = 0; i < n; i++) {
//                sum += sc.nextInt();
//            }
//
//            System.out.println(sum);
//        }
//
//    }
    public static void main(String[] args) {
        //输入
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            int n = scanner.nextInt();
            if (n == 0) {
                return;
            }
            String inputStr = scanner.nextLine();
            String[] strs = inputStr.split(" ");
            int length = strs.length - 1;
            int[] sum = new int[length];
            for (int i = 0; i < length; i++) {
                sum[i] = Integer.parseInt(strs[i + 1]);
            }
            System.out.println(Arrays.stream(sum).sum());
        }
    }

}


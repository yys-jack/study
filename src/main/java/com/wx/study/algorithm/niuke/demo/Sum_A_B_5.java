package com.wx.study.niuke.demo;

import java.util.Arrays;
import java.util.Scanner;


/**
 * 链接：https://ac.nowcoder.com/acm/contest/5657/E
 * 来源：牛客网
 *
 * 计算一系列数的和
 * 打开以下链接可以查看正确的代码
 * https://ac.nowcoder.com/acm/contest/5657#question
 *
 * 数据范围：数据组数满足 1 \le t \le 100 \1≤t≤100  ，每组数据中的整数个数满足 1 \le n \le 100 \1≤n≤100  ，每个数据大小满足 1 \le val \le 100 \1≤val≤100
 * 输入描述:
 * 输入的第一行包括一个正整数t(1 <= t <= 100), 表示数据组数。
 * 接下来t行, 每行一组数据。
 * 每行的第一个整数为整数的个数n(1 <= n <= 100)。
 * 接下来n个正整数, 即需要求和的每个正整数。
 * 输出描述:
 * 每组数据输出求和的结果
 * 示例1
 * 输入
 * 2
 * 4 1 2 3 4
 * 5 1 2 3 4 5
 * 输出
 * 10
 * 15
 */
public class Sum_A_B_5 {

//    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//
//        int n = scanner.nextInt();
//        for (int i = 0; i < n; i++) {
//            int s = scanner.nextInt();
//            int sum = 0;
//            for (int j = 0; j < s ; j++) {
//                sum +=scanner.nextInt();
//            }
//            System.out.println(sum);
//        }
//
//    }
    public static void main(String[] args) {
        //输入
        Scanner scanner = new Scanner(System.in);
        int curr = 0;
        int n = Integer.parseInt(scanner.nextLine());
        while (scanner.hasNextLine()) {
            if (curr >= n) {
                break;
            }
            String inputStr = scanner.nextLine();
            String[] strs = inputStr.split(" ");
            int length = strs.length - 1;
            int[] sum = new int[length];
            for (int i = 0; i < length; i++) {
                sum[i] = Integer.parseInt(strs[i + 1]);
            }
            System.out.println(Arrays.stream(sum).sum());
            curr++;
        }
    }
}







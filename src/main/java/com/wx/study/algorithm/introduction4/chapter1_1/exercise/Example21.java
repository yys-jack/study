package com.wx.study.introduction4.chapter1_1.exercise;

import java.util.Scanner;

public class Example21 {
    public static void main(String[] args) {
        System.out.println("输入名字 整数 整数");
        String[] array = new String[100];
        int i = 0;
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            array[i++] = scanner.nextLine();
        }
        System.out.println();

        for (int j = 0; j < i; j++) {
            String[] strings = array[j].split(" ");
            String string1 = strings[0];
            int x = Integer.parseInt(strings[1]);
            int y = Integer.parseInt(strings[2]);
            System.out.printf("%s  %d  %d  %.3f \n", string1, x, y, 1.0 * x / y);
        }
    }
}

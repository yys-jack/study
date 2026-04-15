package com.wx.study.niuke;

import java.util.Scanner;

public class ScannerDemo {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            System.out.println(scanner.hasNext());
            System.out.println("hasNextLine: " + scanner.hasNextLine());
            System.out.println("next: " + scanner.next());       //空格或者换行停止
            System.out.println("nextInt: " + scanner.nextInt());   //空格或者换行停止
            System.out.println("nextLine: " + scanner.nextLine()); //换行停止
        }
    }
}

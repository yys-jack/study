package com.wx.study.niuke;

import java.util.Scanner;

public class HJ1 {
    public static void main(String[] args) {
        //输入str
        Scanner scanner = new Scanner(System.in);
        String inputStr = scanner.nextLine();
        if (inputStr != null && !inputStr.equals("") && inputStr.length() < 5000) {
            String[] strs = inputStr.split(" ");
            int lastWordLen = strs.length - 1;
            String lastWord = strs[lastWordLen];
            System.out.println("最后一个单词为 " + lastWord + "长度为" + lastWord.length());
        }
    }
}

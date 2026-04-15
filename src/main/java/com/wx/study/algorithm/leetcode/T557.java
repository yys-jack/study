package com.wx.study.leetcode;

public class T557 {
    public static String reverseWords(String s) {
        String[] strsArray = s.split(" ");
        StringBuilder str=new StringBuilder();
        for(int i = 0;i<strsArray.length;i++){
            str.append(reverse(strsArray[i])+" ");
        }
        return str.toString().trim();
    }
    private static String reverse(String s){
        StringBuilder stringBuilder = new StringBuilder(s);
        return stringBuilder.reverse().toString();
    }

    public static void main(String[] args) {
        reverseWords("Let's take LeetCode contest");
    }
}

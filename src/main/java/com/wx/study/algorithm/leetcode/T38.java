package com.wx.study.leetcode;

public class T38 {
    public String countAndSay(int n) {
        //终止条件 n = 1
        if (n == 1) {
            return "1";
        }
        //已知上一个结果即countAndSay(n-1)
        String str = countAndSay(n - 1);
        StringBuilder stringBuilder = new StringBuilder();
        //当前数是
        char local = str.charAt(0);
        //当前数的个数
        int count = 0;
        //遍历上一个结果
        for (int i = 0; i < str.length(); i++) {
            char curr = str.charAt(i);
            if (local == curr) {
                count++;
            } else {
                stringBuilder.append(count).append(local);
                count = 1;
                local = str.charAt(i);        //更新
            }
        }
        stringBuilder.append(count).append(local);
        return stringBuilder.toString();
    }

    public static void main(String[] args) {
        System.out.println(new T38().countAndSay(2));
    }
}

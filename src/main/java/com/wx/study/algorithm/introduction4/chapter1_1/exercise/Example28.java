package com.wx.study.introduction4.chapter1_1.exercise;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Example28 {
    public static void main(String[] args) {
        int[] whitelist = {0, 0, 1, 1, 1, 2, 3, 4, 4, 6, 6, 7, 8, 7, 5};
        Arrays.sort(whitelist);
        int repeat = 0;//计算有多少重复的
        for (int i = 1; i < whitelist.length; i++) {
            if (whitelist[i] == whitelist[i - 1])
                repeat++;
        }
        //新建一个数组存不重复的
        int[] noRepeat = new int[whitelist.length - repeat];
        noRepeat[0] = whitelist[0];
        int temp = 1;
        for (int j = 1; j < whitelist.length; j++) {
            if (whitelist[j] != whitelist[j - 1])
                noRepeat[temp++] = whitelist[j];
        }
        for (int i = 0; i < noRepeat.length; i++) {
            System.out.print(noRepeat[i] + " ");
        }
    }
    public static void distinct(int[] arr){
        System.out.println(Arrays.stream(arr).sorted().distinct().boxed().collect(Collectors.toList()));
    }

}

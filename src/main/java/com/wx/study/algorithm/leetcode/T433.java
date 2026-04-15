package com.wx.study.leetcode;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class T433 {
    public int minMutation(String start, String end, String[] bank) {
        //bank 中找不到end  那么 返回 -1
        List<String> bankList = Arrays.stream(bank).collect(Collectors.toList());
        if (!bankList.contains(end)) {
            return -1;
        }

        return 1;
    }
}

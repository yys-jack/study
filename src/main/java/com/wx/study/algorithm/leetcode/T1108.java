package com.wx.study.leetcode;

public class T1108 {
    public String defangIPaddr(String address) {
        return address.replaceAll("\\.", "\\[\\.\\]");
    }
}

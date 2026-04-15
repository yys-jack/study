package com.wx.study.leetcode;

import java.util.HashSet;

/**
 * https://leetcode.cn/problems/unique-email-addresses/
 */
public class T929 {
    public int numUniqueEmails(String[] emails) {
        HashSet<String> set = new HashSet<>();
        for (int i = 0; i < emails.length; i++) {
            int temp = emails[i].lastIndexOf("@");
            String local = emails[i].substring(0, temp).split("\\+")[0];         //取本地名，处理符号+
            local = local.replace(".", "");                          //将字符替换.
            set.add(local + emails[i].substring(temp));
        }
        return set.size();
    }
}

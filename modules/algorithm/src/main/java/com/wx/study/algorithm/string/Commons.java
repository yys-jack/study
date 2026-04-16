package com.wx.study.algorithm.string;

public class Commons {
    /**
     * 判断字符串是否是一条回文
     *
     * @param s
     * @return
     */
    public boolean isPalindrome(String s) {
        int N = s.length();
        for (int i = 0; i < N / 2; i++)
            if (s.charAt(i) != s.charAt(N - 1 - i)) return false;
        return true;
    }

    /**
     * 检查字符串数组中的元素是否按照字符表顺序排序
     */
    public boolean isSorted(String[] a) {
        for (int i = 1; i < a.length; i++)
            if (a[i - 1].compareTo(a[i]) > 0) return false;
        return true;
    }

    /**
     * 以空白字符为分隔符，从str中创建一个字符串数组
     * @param str
     */
    public String[] splitStr(String str) {
        String[] split = str.split("\\s+");
        return split;
    }

}

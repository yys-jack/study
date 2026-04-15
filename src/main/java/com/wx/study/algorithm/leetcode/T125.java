package com.wx.study.leetcode;

/**
 * https://leetcode.cn/problems/valid-palindrome/
 */
public class T125 {
    //正则
    public boolean isPalindrome(String s) {
        //过滤掉特殊字符只留字母和数字，然后转小写
        String actual = s.replaceAll("[^A-Za-z0-9]", "").toLowerCase();
        String rev = new StringBuffer(actual).reverse().toString();
        return actual.equals(rev);
    }

    //双指针
    public boolean isPalindrome_2(String s) {
        s = s.toLowerCase();
        int left = 0;
        int right = s.length() - 1;
        //指针指向位置如果是特殊字符，那么指针直接移动到下一个位置
        while (left < right) {
            while (left < right && !((s.charAt(left) >= 'a' && s.charAt(left) <= 'z') || (s.charAt(left) >= '0' && s.charAt(left) <= '9')))
                left++;
            while (left < right && !((s.charAt(right) >= 'a' && s.charAt(right) <= 'z') || (s.charAt(right) >= '0' && s.charAt(right) <= '9')))
                right--;
            if (s.charAt(left) != s.charAt(right)) return false;
            left++;
            right--;
        }
        return true;
    }

    //双指针
    public boolean isPalindrome_3(String s) {
        s = s.toLowerCase();
        int left = 0;
        int right = s.length() - 1;
        //指针指向位置如果是特殊字符，那么指针直接移动到下一个位置
        while (left < right) {
            while (left < right && !Character.isLetterOrDigit(s.charAt(left))) left++;
            while (left < right && !Character.isLetterOrDigit(s.charAt(right))) right--;
            if (s.charAt(left) != s.charAt(right)) return false;
            left++;
            right--;
        }
        return true;
    }

    //递归写法
    public boolean isPalindrome_4(String s) {
        return isPalindromeHelper(s, 0, s.length() - 1);
    }

    public boolean isPalindromeHelper(String s, int left, int right) {
        if (left >= right) return true;
        while (left < right && !Character.isLetterOrDigit(s.charAt(left))) left++;
        while (left < right && !Character.isLetterOrDigit(s.charAt(right))) right--;
        return Character.toLowerCase(s.charAt(left)) == Character.toLowerCase(s.charAt(right)) && isPalindromeHelper(s, ++left, --right);
    }
}

package com.wx.study.leetcode;

/**
 * https://leetcode.cn/problems/reverse-integer/
 */
public class T7 {
    //使用字符串反转，
    //正数，直接反转，负数先去掉符号，反转之后再加上符号
    //最后判断的到的结果是否在int取值范围内
    public int reverse(int x) {
        //如果数为-2^31那么反转之后就超过了2^31-1
        if (x == Integer.MIN_VALUE) return 0;
        boolean mark = true;   //标志位x正负
        if (x < 0) {
            mark = false;
            x = Math.abs(x);
        }
        String str = Integer.toString(x);
        StringBuilder stringBuffer = new StringBuilder(str);
        long result = Long.parseLong(stringBuffer.reverse().toString());
        if (!mark) {
            result = -result;
        }
        if (result > Integer.MAX_VALUE || result < Integer.MIN_VALUE) {
            return 0;
        }
        return (int) result;
    }

}

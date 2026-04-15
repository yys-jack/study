package com.wx.study.leetcode;

import java.util.Arrays;

/**
 * 加一
 * 给定一个由 整数 组成的 非空 数组所表示的非负整数，在该数的基础上加一。
 * <p>
 * 最高位数字存放在数组的首位， 数组中每个元素只存储单个数字。
 * <p>
 * 你可以假设除了整数 0 之外，这个整数不会以零开头。
 * <p>
 *  
 * <p>
 * 示例 1：
 * <p>
 * 输入：digits = [1,2,3]
 * 输出：[1,2,4]
 * 解释：输入数组表示数字 123。
 * 示例 2：
 * <p>
 * 输入：digits = [4,3,2,1]
 * 输出：[4,3,2,2]
 * 解释：输入数组表示数字 4321。
 * 示例 3：
 * <p>
 * 输入：digits = [0]
 * 输出：[1]
 *  
 * <p>
 * 提示：
 * <p>
 * 1 <= digits.length <= 100
 * 0 <= digits[i] <= 9
 * <p>
 * 链接：https://leetcode-cn.com/leetbook/read/top-interview-questions-easy/x2cv1c/
 */
public class T66 {
    public static int[] plusOne(int[] digits) {
        int[] result = digits;
        int index = digits.length;
        digits[--index]++;      //个位加1
        while (digits[index] == 10) {      //判断当前为是否需要进位
            digits[index] = 0;
            //判断数据长度是否需要拓展
            if (index == 0) {              //判断数组长度是否需要增加
                int[] temp = new int[result.length + 1];
                for (int i = 1; i < result.length + 1; i++) {
                    temp[i] = result[i - 1];
                }
                result = temp;
                result[0]++;
            } else {
                index--;
                result[index]++;
            }
        }
        return result;
    }

    public static int[] plusOne_2(int[] digits) {
        int length = digits.length;
        //从个位开始判断
        for (int i = length - 1; i >= 0; i--) {
            //判断当前位置是否为9
            if (digits[i] != 9) {
                //当前位置+1
                digits[i]++;
                return digits;
            } else {
                //进位
                digits[i] = 0;
            }
        }
        int[] temp = new int[length + 1];
        temp[0] = 1;
        return temp;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(T66.plusOne(new int[]{9, 8, 7, 6, 5, 4, 3, 2, 1, 0})));
    }
}

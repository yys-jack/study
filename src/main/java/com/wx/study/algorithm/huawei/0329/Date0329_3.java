package com.wx.study.algorithm.huawei.0329;
package com.lyao.date;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Date0401_3 {


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String inputStr = scanner.nextLine();
        int numCanRemove = scanner.nextInt();
        System.out.println(func(inputStr, numCanRemove));

    }

    private static String func(String inputStr, int numCanRemove) {
        int inputLength = inputStr.length();
        int targetLength = inputLength - numCanRemove;

        List<Character> list = new ArrayList<>();
        for (int i = targetLength; i > 0; i--) {
            inputStr = choose(inputStr, i, targetLength, list);
        }
        StringBuffer sb = new StringBuffer();
        for (Character ch : list) {
            sb.append(ch);
        }
        return sb.toString();
    }

    private static String choose(String inputStr, int targetLen, int first, List<Character> list) {
        int numCanChoose = inputStr.length() - targetLen + 1;
        int num = 10;
        int index = Integer.MAX_VALUE;
        for (int i = 0; i < numCanChoose; i++) {
            int temp = Integer.parseInt(String.valueOf(inputStr.charAt(i)));
            if (temp == 0 && targetLen == first) {
                continue;
            }
            if (temp < num) {
                num = temp;
                index = i;
            }
        }
        list.add(inputStr.charAt(index));
        return inputStr.substring(index + 1);
    }
}

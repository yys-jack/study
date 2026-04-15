package com.wx.study.algorithm.huawei.0329;
package com.lyao.date;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

//1、有一个特殊的5键键盘，上面有a，ctrl-c，ctrl-x，ctrl-v，ctrl-a五个键。
// a键在屏幕上输出一个字母a；
// ctrl-c将当前选择的字母复制到剪贴板；
// ctrl-x将当前选择的字母复制到剪贴板，并清空选择的字母；
// ctrl-v将当前剪贴板里的字母输出到屏幕；
// ctrl-a选择当前屏幕上的所有字母。注意：
//        1 剪贴板初始为空，新的内容被复制到剪贴板时会覆盖原来的内容
//        2 当屏幕上没有字母时，ctrl-a无效
//        3 当没有选择字母时，ctrl-c和ctrl-x无效
//        4 当有字母被选择时，a和ctrl-v这两个有输出功能的键会先清空选择的字母，再进行输出
//
public class Date0401_1 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String inputStr = scanner.nextLine();
        String[] keys = inputStr.split(" ");
        scanner.close();
        System.out.println(func(keys));
    }

    private static int func(String[] keys) {
        Stack<String> stack = new Stack<>();
        List<String> clipBoard = new ArrayList<>();
        boolean isChoose = false;
        for (String key : keys) {
            switch (key) {
                case "1":  // push a
                    if (isChoose) {
                        stack.clear();
                    }
                    stack.push("a");
                    break;
                case "2":  // ctrl c
                    if (isChoose) {
                        clipBoard.clear();
                        clipBoard.addAll(stack);
                    }
                    break;
                case "3":  // ctrl x
                    if (isChoose) {
                        clipBoard.addAll(stack);
                    }
                    isChoose = false;
                    stack.clear();
                    break;
                case "4":  // ctrl v
                    if (isChoose) {
                        isChoose = false;
                        stack.clear();
                    }
                    for (String temp : clipBoard) {
                        stack.push(temp);
                    }
                    break;
                case "5":  // ctrl a
                    if (!stack.isEmpty()) {
                        isChoose = true;
                    }
                    break;
                default:
                    break;
            }
        }
        return stack.size();
    }
}

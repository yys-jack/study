package com.wx.study.leetcode;

import java.util.ArrayList;
import java.util.List;

public class T118 {
    public List<List<Integer>> generate(int numRows) {
        //结果值
        List<List<Integer>> res = new ArrayList<>();
        //每行值
        List<Integer> row = new ArrayList<>();
        for (int i = 0; i < numRows; i++) {
            //下面一行都会比上面一行多一个元素，
            row.add(0, 1);
            //遍历每行的结果，跳过第一个和最后一个元素
            for (int j = 1; j < row.size() - 1; j++) {
                //公式
                row.set(j, row.get(j) + row.get(j + 1));
            }
            res.add(new ArrayList<>(row));
        }
        return res;
    }

    public static void main(String[] args) {
        System.out.println(new T118().generate(5));
    }

}

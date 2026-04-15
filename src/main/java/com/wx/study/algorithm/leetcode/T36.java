package com.wx.study.leetcode;

/**
 * https://leetcode-cn.com/problems/valid-sudoku/
 * //todo
 * 二维数组a[i][j]表示为一维数组：
 *
 * 行优先原则：a[i*M+j] i是行下标，M是一行的元素个数，j是列下标
 * 列优先原则：a[i*M+j] i是列下标，M是一列的元素个数，j是行下标
 */
public class T36 {
    public boolean isValidSudoku(char[][] board) {
        //row同一行   col同一列    box小方框     不能有数字一样
        int[][] row = new int[9][9];
        int[][] col = new int[9][9];
        int[][] box = new int[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                //判断当前位置是否有数据
                if (board[i][j] == '.') {
                    continue;
                }
                //当前格子是数字
                int num = board[i][j] - '0' - 1;
                //k是第几个单元格，9宫格数独横着竖着都是第3个单元格
                int k = i / 3 * 3 + j / 3;
                //如果当前数字对应的行和列以及单元格，只要一个由数字，说明冲突了，直接返回false。
                //举个例子，如果line[i][num]不等于0，说明第i（i从0开始）行有num这个数字。
                if (row[i][num] != 0 || col[j][num] != 0 || box[k][num] != 0) return false;
                //表示第i行有num这个数字，第j列有num这个数字，对应的单元格内也有num这个数字
                row[i][num] = col[j][num] = box[k][num] = 1;
            }
        }
        return true;
    }

    /**
     * 数组代替hash表
     * 即
     * 行 i 取值范围  [0,9)
     * 列 j 取值范围  [0,9)
     * 值取值范围     [0,9)
     *
     * 创建二维数组 rows 和 col 分别记录数独的每一行和每一列中的每个数字的出现次数，
     * 创建三维数组 box 记录数独的每一个小九宫格中的每个数字的出现次数
     * 二维数组rows表示的是对应的行中是否有对应的数字，比如rows[0][3]
     * 表示的是第0行（实际上是第1行，因为数组的下标是从0开始的）是否有数字4
     *
     *
     * @param board
     * @return
     */
    public boolean isValidSudoku_2(char[][] board) {
        int[][] row = new int[9][9];       //行      i 取值范围 [0,9)
        int[][] col = new int[9][9];       //列      j 取值范围 [0,9)
        int[][][] box = new int[3][3][9];    //小方块   i/3 , j/3 取值范围 [0,3)
        //遍历
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                //判断是否是数字
                int curr = board[i][j];         //board[i][j] 取值[1,9]
                if (curr != '.') {
                    int index = curr - '0' - 1;      // index 取值[0,9)
                    row[i][index]++;
                    col[j][index]++;
                    box[i / 3][j / 3][index]++;
                    if (row[i][index] > 1 || col[j][index] > 1 || box[i / 3][j / 3][index] > 1) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

}

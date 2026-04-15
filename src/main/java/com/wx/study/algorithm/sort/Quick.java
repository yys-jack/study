package com.wx.study.sort;

/**
 * 快排
 * 有切分方法
 * 对于切分，使得数组满足三个条件
 * 对于某个j,a[j]已经排定
 * a[lo]到a[j-1]中所有元素都不大于a[j]
 * a[j+1]到a[hi]中所有元素都不小于a[j]
 *
 * 有左右两个指针
 * 示意图
 *                  输入      Q U I C K S O R T E X A M P L E
 *                  打乱      K R A T E L E P U I M Q C X O S
 *                  切分      E C A I E K L P U T M Q R X O S
 *
 *          将左半部分排序      A C E E I
 *                              不大于K
 *          中间                        K   --->  切分元素
 *                                              不小于K
 *          将右半部分排序                  L M O P Q R S T U X
 *          结束              A C E E I K L M O P Q R S T U X
 *
 * @author wxli
 * @date 2021/8/12 23:27
 */
public class Quick extends AbstractSort {
    @Override
    public void sort(Comparable[] a) {
        sort(a, 0, a.length - 1);
    }

    private static void sort(Comparable[] a, int lo, int hi) {
        if (hi <= lo) return;
        int j = partition(a, lo, hi); //切分
        sort(a, lo, j - 1);
        sort(a, j + 1, hi);
    }

    private static int partition(Comparable[] a, int lo, int hi) {
        //将数组切分为a[lo--j-1],a[j],a[j+1..hi]
        int i = lo, j = hi + 1;                 //左右扫描指针
        Comparable v = a[lo];
        while (true) {
            //扫描左右，检查扫描是否结束并交换元素
            while (less(a[++i], v)) if (i == hi) break;
            while (less(v, a[--j])) if (j == lo) break;
            if (i >= j) break;
            exch(a, i, j);
        }
        exch(a, lo, hi);     //将v=a[j]放入正确的位置
        return j;            //a[lo..j-1] <= a[j] <=a[j+1..hi]
    }
}

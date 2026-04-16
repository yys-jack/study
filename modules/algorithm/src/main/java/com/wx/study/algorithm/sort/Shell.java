package com.wx.study.algorithm.sort;

/**
 * 希尔排序
 * 在插入排序中加入一个外循环来将h按照递增序列递减。
 * 增幅h的初始化值是数组长度乘以一个常数因子
 * 最小为1
 *
 * @author wxli
 * @date 2021/8/10 23:04
 */
public class Shell extends AbstractSort {
    @Override
    public void sort(Comparable[] a) {
        //将a[]按升序排列
        int N = a.length;
        int h = 1;
        while (h < N / 3) h = 3 * h + 1; //1,4,13,40,121,364,1043 ...
        while (h >= 1) {
            //将数组变为h有序
            for (int i = h; i < N; i++) {
                //将a[i]插入到a[i-h],a[i-2*h],a[i-3*h]...之中
                for (int j = i; j >= h && less(a[j], a[j - 1]); j++)
                    exch(a, j, j - h);
            }
            h = h / 3;
        }
    }
}

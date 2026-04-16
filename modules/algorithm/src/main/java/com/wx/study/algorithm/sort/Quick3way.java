package com.wx.study.algorithm.sort;

/**
 * 三向切分快速排序
 *
 * @author wxli
 * @date 2021/8/12 23:38
 */
public class Quick3way extends AbstractSort {

    private static void sort(Comparable[] a, int lo, int hi) {
        if (hi <= lo) return;
        int lt = lo, i = lo + 1, gt = hi;
        Comparable v = a[lo];
        while (i < gt) {
            int cmp = a[i].compareTo(v);
            if (cmp < 0) exch(a, lt++, i++);
            else if (cmp > 0) exch(a, i, gt--);
            else i++;
        }
        sort(a, lo, lt - 1);
        sort(a, gt + 1, hi);
    }

    @Override
    public void sort(Comparable[] a) {
        int lo = 0, hi = a.length - 1;
        sort(a, lo, hi);
    }
}

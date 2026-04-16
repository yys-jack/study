package com.wx.study.algorithm.sort;

/**
 * 插入排序
 * 对于1-N-1之间的每一个i，将a[i]与a[0]到a[i-1]中比它小的所有元素一次有序地交换
 * 在索引i 由左向右变化的过程中，它左侧的元素总是有序的，所以当i到达数组的右端时
 * 排序就完成了。
 *
 * @author wxli
 * @date 2021/8/10 22:50
 */
public class Insert extends AbstractSort {

    @Override
    public void sort(Comparable[] a) {
        //将a[] 按照升序排列
        int N = a.length;
        for (int i = 0; i < N; i++) {
            for (int j = i; j > 0 && less(a[j], a[j - 1]); j--) {
                //依次交换
                exch(a, j, j - 1);
            }
            show(a, i);
        }
    }
}

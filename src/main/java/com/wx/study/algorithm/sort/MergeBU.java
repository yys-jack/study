package com.wx.study.sort;

/**
 * 自底向上的归并排序
 *
 * @author wxli
 * @date 2021/8/12 23:21
 */
public class MergeBU extends AbstractSort {
    private static Comparable[] aux;     //归并所需的辅助数组


    @Override
    public void sort(Comparable[] a) {
        int N = a.length;
        aux = new Comparable[N];
        for (int sz = 1; sz < N; sz = sz + sz) {  //sz子数组的大小
            for (int lo = 0; lo < N - sz; lo += sz + sz) { //  lo:子数组索引
                merge(a, lo, lo + sz - 1, Math.min(lo + sz + sz - 1, N - 1));


            }

        }

    }

    //原地归并的抽象方法
    public static void merge(Comparable[] a, int lo, int mid, int hi) {
        //将a[lo..mid]和a[mid+1..hi]归并
        int i = lo, j = mid + 1;
        for (int k = lo; k <= hi; k++) { //将a[lo..hi] 复制到aux[lo..hi]
            aux[k] = a[k];
        }
        for (int k = lo; k <= hi; k++) {
            if (i > mid) a[k] = aux[j++];                   //左半边用尽（取右半边的元素）
            else if (j > hi) a[k] = aux[i++];               //右半边用尽（去左半边的元素）
            else if (less(aux[j], aux[i])) a[k] = a[j++];   //右半边的当前元素小于左半边的当前元素（取右半边的元素）
            else a[k] = aux[i++];                           //右半边的当前元素大于等于左半边的当前元素（取左半边的元素）
        }
    }
}

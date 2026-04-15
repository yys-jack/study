package com.wx.study.sort;

/**
 * 归并排序，
 * 分治思想，将一个更大的数组分成两半分别排序
 * 自顶向下的归并排序
 *      ...                         a[]                                             aux[]
 *      k       0   1   2   3   4   5   6   7   8   9    i   j     0   1   2   3   4   5   6   7   8   9
 * 输入          E   E   G   N   R | A   C   E   R   T             --------------------------------------
 * 复制          E   E   G   N   R | A   C   E   R   T             E    E   G   N   R | A   C   E   R   T
 *
 *                                                        0   5
 *      0       A                                         0   6
 *      1       A   C                                     0   7
 *      2       A                                         1   7
 *      3                                                 2   7
 *      4                                                 2   8
 *      5                                                 3   8
 *      6                                                 4   8
 *      7                                                 5   8
 *      8                                                 5   9
 *      9                                                 6   10
 *
 *
 * @author wxli
 * @date 2021/8/12 22:51
 */
public class Merge extends AbstractSort {
    private static Comparable[] aux;     //归并所需的辅助数组

    @Override
    public void sort(Comparable[] a) {
        aux = new Comparable[a.length];
        sort(aux, 0, a.length - 1);
    }

    //
    private static void sort(Comparable[] a, int lo, int hi) {
        //将数组a[lo..hi]排序
        int mid = lo + (hi - lo) / 2;
        sort(a, lo, mid);                                   //将左半边排序
        sort(a, mid + 1, hi);                           //将右半边排序
        merge(a, lo, mid, hi);                             //归并结果
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

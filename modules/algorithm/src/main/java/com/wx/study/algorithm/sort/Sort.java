package com.wx.study.sort;


/**
 * 排序算法
 */
public class Sort {

    //冒泡排序
    public void bubbleSort(int[] a) {
        for (int i = 0; i < a.length - 1; i++) {
            for (int j = 0; j < a.length - 1 - i; j++) {
                //比较前后两个数
                //前面大于后面交换
                if (a[j] > a[j + 1]) {
                    //交换
                    int temp = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = temp;
                }
            }
        }
    }

    //选择排序
    public void selectSort(int[] a) {
        for (int i = 0; i < a.length - 1; i++) {
            int min = i;
            for (int j = i + 1; j < a.length; j++) {
                if (a[j] < a[min]) {
                    min = j;
                }
            }
            int temp = a[i];
            a[i] = a[min];
            a[min] = temp;
        }
    }

    //快速排序
    public void fastSort(int[] a, int low, int high) {
        if (low < high) {
            int pivot = partition(a, low, high);
            fastSort(a, low, pivot - 1);
            fastSort(a, pivot + 1, high);
        }
    }

    public int partition(int[] a, int low, int high) {
        int pivot = a[low];
        while (low < high) {
            while (low < high && a[high] >= pivot) {
                high--;
            }
            a[low] = a[high];
            while (low < high && a[low] <= pivot) {
                low++;
            }
            a[high] = a[low];
        }
        a[low] = pivot;
        return low;
    }

    //三向切分快速排序
    public void quickSort(int[] a, int low, int high) {
        if (low < high) {
            int pivot = partition(a, low, high);
            quickSort(a, low, pivot - 1);
            quickSort(a, pivot + 1, high);
        }
    }

    //归并排序
    public void mergeSort(int[] a, int low, int high) {
        if (low < high) {
            int mid = (low + high) / 2;
            mergeSort(a, low, mid);
            mergeSort(a, mid + 1, high);
            merge(a, low, mid, high);
        }
    }

    public void merge(int[] a, int low, int mid, int high) {
        int[] temp = new int[high - low + 1];
        int i = low;
        int j = mid + 1;
        int k = 0;
        while (i <= mid && j <= high) {
            if (a[i] < a[j]) {
                temp[k++] = a[i++];
            } else {
                temp[k++] = a[j++];
            }
        }
        while (i <= mid) {
            temp[k++] = a[i++];
        }
        while (j <= high) {
            temp[k++] = a[j++];
        }
        for (int l = 0; l < temp.length; l++) {
            a[low + l] = temp[l];
        }
    }

    //堆排序
    public void heapSort(int[] a) {
        for (int i = a.length / 2 - 1; i >= 0; i--) {
            adjustHeap(a, i, a.length);
        }
        for (int i = a.length - 1; i > 0; i--) {
            int temp = a[0];
            a[0] = a[i];
            a[i] = temp;
            adjustHeap(a, 0, i);
        }
    }

    public void adjustHeap(int[] a, int i, int length) {
        int temp = a[i];
        for (int k = 2 * i + 1; k < length; k = 2 * k + 1) {
            if (k + 1 < length && a[k] < a[k + 1]) {
                k++;
            }
            if (a[k] > temp) {
                a[i] = a[k];
                i = k;
            } else {
                break;
            }
        }
        a[i] = temp;
    }

    //基数排序
    public void radixSort(int[] a) {
        int max = a[0];
        for (int i = 1; i < a.length; i++) {
            if (a[i] > max) {
                max = a[i];
            }
        }
        for (int i = 1; max / i > 0; i *= 10) {
            countSort(a, i);
        }
    }

    public void countSort(int a[], int exp) {
        int[] output = new int[a.length];
        int[] count = new int[10];
        for (int i = 0; i < a.length; i++) {
            count[(a[i] / exp) % 10]++;
        }
        for (int i = 1; i < 10; i++) {
            count[i] += count[i - 1];
        }
        for (int i = a.length - 1; i >= 0; i--) {
            output[count[(a[i] / exp) % 10] - 1] = a[i];
            count[(a[i] / exp) % 10]--;
        }
        for (int i = 0; i < a.length; i++) {
            a[i] = output[i];
        }
    }

    //计数排序
    public void countingSort(int[] a) {
        int max = a[0];
        for (int i = 1; i < a.length; i++) {
            if (a[i] > max) {
                max = a[i];
            }
        }
        int[] count = new int[max + 1];
        for (int i = 0; i < a.length; i++) {
            count[a[i]]++;
        }
        int index = 0;
        for (int i = 0; i < count.length; i++) {
            while (count[i] > 0) {
                a[index++] = i;
                count[i]--;
            }
        }
    }

    //桶排序
    public void bucketSort(int[] a) {
        int max = a[0];
        for (int i = 1; i < a.length; i++) {
            if (a[i] > max) {
                max = a[i];
            }
        }
        int[] bucket = new int[max + 1];
        for (int i = 0; i < a.length; i++) {
            bucket[a[i]]++;
        }
        int index = 0;
        for (int i = 0; i < bucket.length; i++) {
            while (bucket[i] > 0) {
                a[index++] = i;
                bucket[i]--;
            }
        }
    }

    //猴子排序
    public void monkeySort(int[] a) {
        int n = a.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                if (a[i] > a[j]) {
                    int temp = a[i];
                    a[i] = a[j];
                    a[j] = temp;
                }
            }
        }
    }

    //希尔排序
    public void shellSort(int[] a) {
        int n = a.length;
        int gap = n / 2;
        while (gap > 0) {
            for (int i = gap; i < n; i++) {
                int j = i;
                int temp = a[i];
                while (j >= gap && a[j - gap] > temp) {
                    a[j] = a[j - gap];
                    j -= gap;
                }
                a[j] = temp;
            }
            gap /= 2;
        }
    }
    //自底向上的归并排序
    public void mergeSort(int[] a) {
        int n = a.length;
        for (int i = 1; i < n; i *= 2) {
            for (int j = 0; j < n - i; j += 2 * i) {
                merge(a, j, j + i - 1, Math.min(j + 2 * i - 1, n - 1));
            }
        }
    }
    //自顶向下的归并排序
    public void mergeSortBU(int[] a) {
        int n = a.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                merge(a, j, j + 1, Math.min(j + 2, n - 1));
            }
        }
    }

    public static void main(String[] args) {

        Class<Sort> sortClass = Sort.class;



    }

}


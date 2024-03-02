package datastructures.sort;
/*
* 排序算法的稳定性是指排序过程中相同元素的相对位置不改变，
* 那么该排序算法能够满足稳定性
* */

/*
* 总结
* 排序算法        时间复杂度        空间复杂度      稳定性
* 1.选择排序       O(N^2)           O(1)            SortedTopology
* 2.冒泡排序       O(N^2)           O(1)            ✔
* 3.插入排序       O(N^2)           O(1)            ✔
* 4.归并排序       O(N*logN)        O(N)            ✔
* 5.快速排序3.0    O(N*logN)        O(logN)         SortedTopology
* 6.堆排序         O(N*logN)        O(1)            SortedTopology
*
* 工程上对排序的优化
* 可以结合O(N*logN)排序在大样本情况下调度的优势和O(N^2)排序在小样本下
* 常数时间低的优势优化算法
* */
public class BubbleSort {
    /*
     * 冒泡排序算法
     * 算法复杂度O(N^2)
     * */
    public static void bubbleSort(int[] arr) {
        /*
         * 考虑特殊情况
         * 1. 数组为null 这个不做处理会报空指针异常NullPointerException
         * 2. 元素个数小于2 (0和1不需要比较）
         * */
        if (arr == null || arr.length < 2) {
            return;
        }
        for (int i = arr.length; i > 0; i--) {
            for (int j = 1; j < i; j++) {
                if (arr[j - 1] > arr[j]) {
                    swap(arr, j, j - 1);
                }
            }
        }
    }

    /*
     * 交换数组两个索引位置的值、
     * 定义一个中间变量，申请了额外的空间
     * */
    /*private static void swap(int[] arr,int i, int j) {
        int temp = arr[i];
        arr[i]=arr[j];
        arr[j]=temp;
    }*/

    /*
     * 交换数组两个索引位置的值
     * 采用异或运算的方式，前提是arr[i]和arr[b]指向不同的内存空间，否则会变成0
     * */
    private static void swap(int[] arr, int i, int j) {
        arr[i] = arr[i] ^ arr[j];
        arr[j] = arr[i] ^ arr[j];
        arr[i] = arr[i] ^ arr[j];
    }

    public static void main(String[] args) {
        int[] arr1 = {2, 27, 75, 23, 5, 2, 4, 5};
        swap(arr1, 0, 0);
        System.out.println(arr1[0] + " " + arr1[1]);
        bubbleSort(arr1);
        for (int i = 0; i < arr1.length; i++) {
            System.out.print(arr1[i] + " ");
        }
        bubbleSort(null);
        bubbleSort(new int[]{});
        bubbleSort(new int[]{2});
    }
}

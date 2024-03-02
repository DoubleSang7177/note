package datastructures.search;
/*
 * 查找中间索引不能用 (i+j)/2
 * java中二进制数值用补码表示，当i+j的值超过最大的整数值Integer.MAX_VALUE
 * 的时候变成负数，而数组的索引不能为负，报错！
 *
 * 注意！二分查找的数组必须是顺序排序！！！
 *
 * 衡量算法好坏的标准：时间复杂度和空间复杂度
 * 时间复杂度：
 * processX(n)=(floor(log2(n))+1)*5+4  用大O表示法 processX(n)=O(g(n))
 * g(n)=log2(n)---> processX(n)经过化简后得出
 * O(log2(n)) 对数时间
 *
 * 空间复杂度
 * 需要常数个指针i,j,m,因此额外占用的空间是O(1)
 *
 * */
public class BinarySearch {
    /*
     * 索引j也参与查找
     * */
    public static int binarySearch1(int[] arr, int target) {
        int i = 0;
        int j = arr.length - 1;
        while (i <= j) {
            int m = (i + j) >>> 1;
            if (target < arr[m]) {
                j = m - 1;
            } else if (arr[m] < target) {
                i = m + 1;
            } else {
                return m;
            }
        }
        return -(i + 1); //i为元素插入位置的索引
    }

    /*
     * 索引j不参与查找
     * */
    public static int binarySearch2(int[] arr, int target) {
        int i = 0;
        int j = arr.length;
        while (i < j) {
            int m = (i + j) >>> 1;
            if (target < arr[m]) {
                j = m;
            } else if (arr[m] < target) {
                i = m + 1;
            } else {
                return m;
            }
        }
        return -(i + 1);
    }

    public static int binarySearchLeftmost(int[] a, int target) {
        int i = 0;
        int j = a.length - 1;
        int candidate = -1;
        while (i <= j) {
            int m = (i + j) >>> 1;
            if (target < a[m]) {
                j = m - 1;
            } else if (a[m] < target) {
                i = m + 1;
            } else {
                candidate = m;
                j = m - 1;
            }
        }
        return candidate;
    }

    public static int binarySearchRightmost(int[] a, int target) {
        int i = 0;
        int j = a.length - 1;
        int candidate = -1;
        while (i <= j) {
            int m = (i + j) >>> 1;
            if (target < a[m]) {
                j = m - 1;
            } else if (a[m] < target) {
                i = m + 1;
            } else {
                candidate = m;
                i = m + 1;
            }
        }
        return candidate;
    }


}

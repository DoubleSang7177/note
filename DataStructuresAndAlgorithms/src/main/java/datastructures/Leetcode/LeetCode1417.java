package datastructures.Leetcode;

import java.util.Arrays;

/*
* 最小k个数
* Java 的 Arrays.sort() 方法在内部对快速排序进行了优化，包括用于处理一些特殊情况的插入排序,
* 以避免快速排序在最坏情况下的时间复杂度降至 O(n^2)。
* 如果数组已经部分排序（或接近排序状态），快速排序的性能可能会下降到 O(n^2)。
* Java 的 Arrays.sort() 方法可能会在这种情况下使用插入排序，从而避免这个问题。
* */
public class LeetCode1417 {
    public static int[] smallestK(int[] arr, int k) {
        int[] res=new int[k];
//        quickSort(arr,0,arr.length-1);
        Arrays.sort(arr);//底层就是快速排序，还进行了优化
        System.arraycopy(arr,0,res,0,k);
        return res;
    }
    private static void quickSort(int[] arr,int left,int right){
        if (left<right){
            swap(arr,left+(int) (Math.random()*(right-left+1)),right);
            int[] points=partition(arr,left,right);
            quickSort(arr,left,points[0]-1);
            quickSort(arr,points[1]+1,right);
        }
    }

    private static int[] partition(int[] arr, int left, int right) {
        int l=left;
        int r=right-1;
        while (left<=r){
            if (arr[left]<arr[right]){
                swap(arr,left++,l++);
            }else if (arr[left]>arr[right]){
                swap(arr,left,r--);
            }else{
                left++;
            }
        }
        swap(arr,++r,right);
        return new int[]{l,r};
    }

    private  static void swap(int[] result, int i, int j) {
        int tmp = result[i];
        result[i] = result[j];
        result[j] = tmp;
    }

    public static void main(String[] args) {
        int[] arr={1,4,3,2,2,2,3,3,1};//9
        for (int i : smallestK(arr, 3)) {
            System.out.print(i+" ");
        }
//        quickSort(arr,0,arr.length-1);
        System.out.println();
        for (int i : arr) {
            System.out.print(i+ " ");
        }
    }
}

package datastructures.sort;
/*
* 插入排序算法
* 算法复杂度O(N^2)
* */
public class InsertionSort {

    public static void insertionSort(int[] arr){
        if (arr==null || arr.length<2){
            return;
        }
        //0~0有序
        //0~i想有序
        for (int i = 1; i < arr.length; i++) {
            for (int j=i-1; j>=0 && arr[j]>arr[j+1] ;j--){ //如果arr[j]<=arr[j+1]，则说明j+1前面的都已经排好序了，不需要j--
                swap(arr,j,j+1);
            }
            /*for (int j=i-1; j>=0;){ //如果arr[j]<=arr[j+1]，则说明j+1前面的都已经排好序了，不需要j--
                if (arr[j]>arr[j+1]) {
                    swap(arr,j,j+1);
                    j--;
                }else {
                    break;
                }
            }*/
        }
      /*  for (int i : arr) {
            System.out.printEdge(i+" ");
        }*/
    }

    /*
     * 交换数组两个索引位置的值
     * 采用异或运算的方式，前提是arr[i]和arr[b]指向不同的内存空间，否则会变成0
     * */
    private static void swap(int[] arr,int i, int j) {
        arr[i]=arr[i] ^ arr[j];
        arr[j]=arr[i] ^ arr[j];
        arr[i]=arr[i] ^ arr[j];
    }

    public static void main(String[] args) {
        insertionSort(new int[]{2,3,1,4,7,4,6,2,1,19,5});

    }
}

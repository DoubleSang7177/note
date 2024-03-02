package datastructures.search;
/*
* 递归-插入排序
* */
public class E05InsertionSort {
    public static void sort(int[] a){
        insertion(a,1);
    }
    public static void insertion(int[] a,int low){
        if(low==a.length){
            return;
        }
        /*
        * 第一种实现方式
        * */
        int target=a[low];
        int i=low-1;//已排序区域指针
        while (i >= 0 && a[i]>target){//没有找到插入位置
            a[i+1]=a[i];// 空出插入位置
            i--;
        }
        if(i+1!=low){
            a[i+1]=target;//找到插入位置
        }
        /*
        第二种实现方式
        for (int i = low-1; i>=0; i--) {
            if(a[i+1]>=a[i]){
                break;
            }else{
                int temp=a[i+1];
                a[i+1]=a[i];
                a[i]=temp;
            }
        }*/
        insertion(a,low+1);
    }
}

package datastructures.search;
/*
*递归冒泡排序
*  */
public class E04BubbleSort {
    public static void sort(int[] a){
        bubble(a,a.length-1);
    }
    public static void bubble(int[] a,int length){
        System.out.println(length);
        if(length==0){
            return;
        }
        int x=0;
        for (int i = 1; i <= length; i++) {
            if(a[i-1]>a[i]){
                int temp=a[i];
                a[i]=a[i-1];
                a[i-1]=temp;
                x=i-1;
            }
        }
        bubble(a,x);
    }


}

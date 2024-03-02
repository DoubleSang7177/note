package datastructures.search;

import java.util.Arrays;

/*
* 合并两个有序数组
* */
public class E10Merge2SortedArray {
    public static void main(String[] args){
        int[] a1={1,3,4,5,7,2,4,6,8,12};
        int[] a2=new int[a1.length];
        merge2(a1,0,4,5,a1.length-1,a2);
        System.out.println(Arrays.toString(a2));
        System.arraycopy(a2,0,a1,0,a2.length-1);
        System.out.println(Arrays.toString(a1));
    }
    /*
    * 递归的方法
    * */
    public static void merge(int[] a1, int i, int iEnd, int j, int jEnd, int[] a2, int k){
        if(i>iEnd){
            System.arraycopy(a1,j,a2,k,jEnd-j+1);
            return;
        }
        if(j>jEnd){
            System.arraycopy(a1,i,a2,k,iEnd-i+1);
            return;
        }

        if(a1[i]<a1[j]){
            a2[k]=a1[i];
            merge(a1,i+1,iEnd,j,jEnd,a2,k+1);
        }else{
            a2[k]=a1[j];
            merge(a1,i,iEnd,j+1,jEnd,a2,k+1);
        }
    }

    /*
    * 非递归的方法
    * */
    public static void merge2(int[] a1, int i, int iEnd, int j, int jEnd, int[] a2){
        int k=0;
        while (i<=iEnd && j<=jEnd){
            if(a1[i]<a1[j]){
                a2[k]=a1[i];
                i++;
            }else{
                a2[k]=a1[j];
                j++;
            }
            k++;
        }
        if(j>jEnd){
            System.arraycopy(a1,i,a2,k,iEnd-i+1);
        }
        if(i>iEnd){
            System.arraycopy(a1,j,a2,k,jEnd-j+1);
        }
    }
}

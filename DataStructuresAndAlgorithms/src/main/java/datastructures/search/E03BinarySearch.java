package datastructures.search;
/*
* 递归二分查找
* */
public class E03BinarySearch {
    public static int search(int[] a,int target){
       return binarySearch(a,0,a.length-1,target);
    }

    /*
    *贪心算法
    * */
    public static int f(int[] a,int index,int target){
        if(index>=a.length){
            return -1;
        }
        if(target==a[index]){
            return index;
        }
        return f(a,++index,target);
    }
    public static int binarySearch(int[] a,int left,int right, int target){
        if(left>right){
            return -1;
        }
        int mid=(left+right)>>>1;
        if(a[mid]==target){
            return mid;
        }else if(a[mid]<target){
            return binarySearch(a,mid+1,right,target);
        }else{
            return binarySearch(a,left,mid-1,target);
        }
    }
}

package datastructures.linearStructure.array;
/*
* 荷兰国企
* */
public class Pointers3 {
    public static void pointers(int[] arr,int nums){
        int l=0;
        int r =arr.length-1;
        int p=0;
        while (l< r){
            if (arr[p]<nums){
                swap(arr,p++,l++);
            }else if(arr[p]>nums){
                swap(arr,p,r--);
            }else {
                p++;
            }
        }
    }
    private static void swap(int[] arr,int i, int j) {
        int tmp=arr[i];
        arr[i]=arr[j];
        arr[j]=tmp;
    }

    public static void main(String[] args) {
        int[] arr={3,4,2,5,7,6,4,2,9,12};
        pointers(arr,5);
        for (int i : arr) {
            System.out.print(i+" ");
        }
    }
}

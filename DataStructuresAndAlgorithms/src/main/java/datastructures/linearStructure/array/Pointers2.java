package datastructures.linearStructure.array;
/*
* 快慢指针
* */
public class Pointers2 {
    public static void fastAndSlowPointers(int[] arr,int nums){
        int p1=0;
        int p2=0;
        while (p2<arr.length) {
            if (arr[p2]<=nums ){
                swap(arr,p2,p1++);
            }
            p2++;
        }
        System.out.println(p1);
    }
    private static void swap(int[] arr,int i, int j) {
        int tmp=arr[i];
        arr[i]=arr[j];
        arr[j]=tmp;
    }

    public static void main(String[] args) {
        int[] arr={3,4,2,5,7,6,4,2,9,12};
        fastAndSlowPointers(arr,5);
        for (int i : arr) {
            System.out.print(i+" ");
        }
    }
}

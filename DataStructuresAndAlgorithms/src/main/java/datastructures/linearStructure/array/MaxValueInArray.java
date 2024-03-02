package datastructures.linearStructure.array;

public class MaxValueInArray {
    /*
    * 遍历数组
    * */
    public static int max(int[] arr){
        if (arr==null || arr.length==0){
            return -1;
        }
        int max=arr[0];
        for (int i : arr) {
            if (i>max){
                max=i;
            }
        }
        return max;
    }
    /*
    * 用递归方法查找数组中的最大值
    * 如果满足master公式：T(N)=a*T(N/b)+O(N^d)，可以根据以下情况得出时间复杂度
    * log(b,a)>d -> 复杂度为O(N^log(b,a))
    * log(b,a)=d -> 复杂度为O(N^d*logN)
    * log(b,a)<d -> 复杂度为O(N^d)
    * */
    public static int process(int[] arr, int left, int right){
        if (left==right){
            return arr[left];
        }
        int mid=left+((right-left)>>1);
        int leftMax= process(arr,left,mid);
        int rightMax= process(arr,mid+1,right);
        return Math.max(leftMax,rightMax);
    }

    public static int getMax(int[] arr){
        return process(arr,0,arr.length-1);
    }

    public static void main(String[] args) {
        int[] arr={1,320,23,5,430,56,75,54,34};
        System.out.println(max(null));
        System.out.println(max(arr));
        System.out.println(getMax(arr));
    }
}

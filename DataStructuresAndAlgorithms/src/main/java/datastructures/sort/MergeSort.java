package datastructures.sort;

/*
* 归并排序
*  算法复杂度O(N*logN)
* */
public class MergeSort {
    public static int count=0;//可以用来解决求小和的问题

    public static void mergeSort(int[] arr){
        if (arr==null || arr.length<2){
            return;
        }
        process(arr,0,arr.length-1);
    }

    /*
     * 用递归方法实现归并排序算法
     * 得出master公式：T(N)=2*T(N/2)+O(N)
     * a=2 b=2 d=1
     * log(b,a)=d -> 复杂度为O(N^d*logN)=O(N*logN)
     * */
    public static void process(int[] arr,int left,int right){
        if (left==right){
            return;
        }
        int mid=left+((right-left)>>1);
        process(arr,left,mid);//左边排序
        process(arr,mid+1,right);//右边排序
        merge(arr,left,mid,right);//最后把有序的左右两边合并，最开始调用merge(arr,0,1),左边是0，右边是1，单个数就是有序的
    }
    public static void merge(int[] arr,int left,int mid,int right){
        int p1=left;
        int p2=mid+1;
        int[] newArr=new int[right-left+1];
        int i = 0;
        while (p1<=mid && p2<=right){
            count+=arr[p1]<arr[p2]?(right-p2+1)*arr[p1]:0;
            newArr[i++]=arr[p1]<arr[p2]?arr[p1++]:arr[p2++];
            /*if (arr[p1]<arr[p2]){
                count+=(right-p2+1)*arr[p1];
                newArr[i++]=arr[p1++];
            }else{
                newArr[i++]=arr[p2++];
            }*/
        }
        while (p1<=mid){
            newArr[i++]=arr[p1++];
        }
        while (p2<=right){
            newArr[i++]=arr[p2++];//p2没越界newArr也不会出现数组下标越界异常
        }
        System.arraycopy(newArr,0,arr,left,newArr.length);
    }
    public static void main(String[] args) {
        int[] arr={1,3,4,2,5,12,3,4,2,34,2};
        // 1*10+3*5+4*3+2*5+5*2+12*1+3*2+4*1+2*1
        mergeSort(arr);
        for (int i : arr) {
            System.out.print(i+" ");
        }
        System.out.println("count="+count);
    }
}

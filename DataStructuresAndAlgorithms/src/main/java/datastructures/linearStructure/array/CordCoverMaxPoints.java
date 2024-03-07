package datastructures.linearStructure.array;
/*
* 长度为L的数组最多覆盖几个点
* arr为有序数组
* */
public class CordCoverMaxPoints {
    /*
     * 方法二：固定左位置找最远的满足要求的右位置
     * */
    public static int maxPoint2(int[] arr,int length){
        return process(arr,0,length);
    }
    public static int process(int[] arr,int index,int length){
        if (index==arr.length){
            return 0;
        }
//        int right=index;
        //遍历，其实可以二分查找
//        while (right<arr.length&&arr[right]-arr[index]<=length){
//            right++;
//        }
        int right = farthestRightIndex(arr, index, arr[index] + length);
        return Math.max(right-index+1,process(arr,index+1,length));
    }
    /*
     * 二分查找
     * 在arr[l...arr.length]上找出<=value的最右的位置
     * */
    private static int farthestRightIndex(int[] arr, int l, int value) {
        int r =arr.length-1;
        int index=l;
        while (l <= r){
            int mid= l +((r-l)>>1);
            if (arr[mid]<=value){
                index=mid;
                l=mid+1;
            }else{
                r =mid-1;
            }
        }
        return index;
    }
    /*
    * 方法一：固定右位置找最远的满足要求的左位置
    * */
    public static int maxPoint(int[] arr,int length){
        int res=1;//最小为1
        for (int r = 0; r < arr.length; r++) {
            //找出离当前右位置r最远的满足长度要求的位置l，当前位置r能覆盖的节点数为r-l+1
            res=Math.max(res,r- farthestIndex2(arr,r,arr[r]-length)+1);//与res比较取大者
        }
        return res;
    }
    /*
    * 遍历查找
    * 在arr[0...r]上找出>=value的最左的位置
    * */
    private static int farthestIndex2(int[] arr, int r, int value) {
        int index=r;
        while (index>=0){
            if (arr[index]>=value){
                index--;
            }else{
                break;
            }
        }
        return Math.max(index+1, 0);
    }
    /*
    * 二分查找
    * 在arr[0...r]上找出>=value的最左的位置
    * */
    private static int farthestIndex(int[] arr, int r, int value) {
        int l=0;
        int index=r;
        while (l<=r){
            int mid=l+((r-l)>>1);
            if (arr[mid]>=value){
                index=mid;
                r=mid-1;
            }else{
                l=mid+1;
            }
        }
        return index;
    }
    public static void main(String[] args) {
        System.out.println(maxPoint2(new int[]{1,2, 4,5,6,7, 8, 9, 15},15));
        System.out.println(maxPoint(new int[]{1,2, 4,5,6,7, 8, 9, 15}, 14));
        System.out.println("==");
        System.out.println(farthestIndex(new int[]{1,2, 4, 5, 6,8, 9, 15}, 6, 5));
        System.out.println(farthestIndex2(new int[]{1,2, 4, 5,6,8, 9, 15}, 6, 5));

    }
}

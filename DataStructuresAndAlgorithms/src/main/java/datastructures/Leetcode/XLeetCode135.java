package datastructures.Leetcode;

import java.util.ArrayList;
import java.util.List;
/*
* 有问题
* */
public class XLeetCode135 {
    public static List<Integer> countSmaller(int[] nums) {
        int[] copy=new int[nums.length];
        //代替record，不改变原始数组
        System.arraycopy(nums, 0, copy, 0, nums.length);
        int[] temp=new int[nums.length];
        int[] ans=new int[nums.length];
        int[] index=new int[nums.length];
        int[] tempIndex=new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            index[i]=i;
        }
        ArrayList<Integer> list= new ArrayList<>();
        sortArray(copy,0,nums.length,temp, index,ans,tempIndex);
        for (int an : ans) {
            list.add(an);
        }
        return list;
    }
    public static int sortArray(int[] nums, int left, int right, int[] temp, int[] index, int[] ans, int[] tempIndex) {
        if (left == right) {
            return 0;
        }
        int mid = left + ((right - left) >> 1);
        int s1 = sortArray(nums, left, mid, temp, index, ans, tempIndex);
        int s2 = sortArray(nums, mid + 1, right, temp, index, ans, tempIndex);
       /* if (nums[mid] <= nums[mid + 1]) {//左侧的最大值比右侧的最小值小，那么这两边就没必要进行比较了！
            //肯定找不出逆序对，所以直接返回
            return s1 + s2;
        }*/
        return s1 + s2 + merge(left, mid, right, nums,temp,index,ans,tempIndex);
    }

    public static int merge(int left, int mid, int right, int[] record, int[] temp, int[] index, int[] ans, int[] tempIndex) {
        for (int i = left; i <= right; i++) {
            temp[i]=record[i];//保存原始数组的值，因为归并过程中会发生改变
            tempIndex[i]=index[i];
        }
        int count = 0;
        int i = left;
        int j = mid + 1;
        for (int k = left; k <=right ; k++) {
            if (i==mid+1){
                index[k]=tempIndex[j];
                record[k]=temp[j++];
            }else if(j==right+1){
                index[k]=tempIndex[i];
                record[k]=temp[i++];
            }else if(temp[i]<=temp[j]){
                index[k]=tempIndex[i];
                record[k]=temp[i++];
            }else{//j<i
                count+=(mid-i+1);
                index[k]=tempIndex[j];
                record[k]=temp[j++];
                for (int x=i;i<=mid;i++){
                    ans[tempIndex[x]]+=1;
                }
            }
        }
        return count;
    }


    public static void main(String[] args) {
        for (Integer integer : countSmaller(new int[]{5,2,6,1})) {
            System.out.println(integer);
        }
    }
}

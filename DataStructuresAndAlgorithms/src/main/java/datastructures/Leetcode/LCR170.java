package datastructures.Leetcode;
/*
* 交易逆序对的总数
* */
public class LCR170 {
    public static int reversePairs(int[] record) {
        if(record.length<2){
            return 0;
        }
        int[] copy=new int[record.length];
        for (int i = 0; i < record.length; i++) {
            copy[i]=record[i];//代替record，不改变原始数组
        }
        int[] temp=new int[record.length];
        return sortArray(copy, 0, record.length - 1,temp);
    }

    public static int sortArray(int[] record, int left, int right, int[] temp) {
        if (left == right) {
            return 0;
        }
        int mid = left + ((right - left) >> 1);
        int s1 = sortArray(record, left, mid, temp);
        int s2 = sortArray(record, mid + 1, right, temp);
        if (record[mid] <= record[mid + 1]) {//左侧的最大值比右侧的最小值小，那么这两边就没必要进行比较了！
            //肯定找不出逆序对，所以直接返回
            return s1 + s2;
        }
        return s1 + s2 + merge(left, mid, right, record,temp);
    }

    public static int merge(int left, int mid, int right, int[] record, int[] temp) {
        for (int i = left; i <= right; i++) {
            temp[i]=record[i];//保存原始数组的值，因为归并过程中会发生改变
        }
        int count = 0;
        int i = left;
        int j = mid + 1;
        for (int k = left; k <=right ; k++) {
            if (i==mid+1){
                record[k]=temp[j++];
            }else if(j==right+1){
                record[k]=temp[i++];
            }else if(temp[i]<=temp[j]){
                record[k]=temp[i++];
            }else{//j<i
                count+=(mid-i+1);
//                count+=(j-mid-1);
                record[k]=temp[j++];
            }
        }
        return count;
    }

    public static void main(String[] args) {
        int[] arr = {4, 1, 2, 56, 2, 5, 4};//7
        System.out.println(reversePairs(arr));
        for (int i : arr) {
            System.out.println(i);
        }
    }
}

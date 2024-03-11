package datastructures.linearStructure.array;

import java.util.Arrays;
import java.util.HashSet;
/*
* 搬运元素 中级提升班-3
* */
public class Magic {

    public static int magic(int[] arr1,int[] arr2){
        if (arr1==null || arr2==null){
            return 0;
        }
        double sum1 = 0;
        for (int value : arr1) {
            sum1+= value;
        }
        double sum2 = 0;
        for (int value : arr2) {
            sum2+= value;
        }
        if (avg(sum1,arr1.length)==avg(sum2,arr2.length)){
            return 0;
        }
        int[] moreArray;
        int[] lessArray;
        int moreSize=0;
        int lessSize=0;
        double moreSum=0;
        double lessSum=0;
        if (avg(sum1,arr1.length)>avg(sum2,arr2.length)){
            moreArray=arr1;
            lessArray=arr2;
            moreSum=sum1;
            lessSum=sum2;
        }else{
            moreArray=arr2;
            lessArray=arr1;
            moreSum=sum2;
            lessSum=sum1;
        }
        Arrays.sort(moreArray);
        HashSet<Integer> set=new HashSet<>();
        for (int i : lessArray) {
            set.add(i);
        }
        moreSize=moreArray.length;
        lessSize=lessArray.length;
        int ops=0;
        for (int j : moreArray) {
            double cur =j;
            if (cur < avg(moreSum, moreSize)
                    && cur > avg(lessSum, lessSize)
                    && !set.contains(j)) {
                moreSum -= cur;
                lessSum += cur;
                moreSize--;
                lessSize++;
                ops++;
                set.add(j);
            }
        }
        return ops;
    }

    private static double avg(double sum, int length) {
        return sum/length;
    }
}

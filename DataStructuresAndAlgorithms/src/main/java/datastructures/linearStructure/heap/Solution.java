package datastructures.linearStructure.heap;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.TreeMap;

/*
* 滑动窗口求中位数问题
* */
public class Solution {
    public static double[] medianSlidingWindow(int[] nums, int k) {
        if (nums==null|| k<1 || k>nums.length){
            return null;
        }
        double[] res=new double[nums.length-k+1];
        DualHeap dh=new DualHeap(k);
        for (int i = 0; i < k; i++) {
            dh.insert(nums[i]);
        }
        res[0]=dh.getMedian();
        for (int i = k; i < nums.length; i++) {
            dh.insert(nums[i]);
            dh.erase(nums[i-k]);//删除过期窗口位置
            res[i-k+1]=dh.getMedian();
        }
        return res;
    }

    public static void main(String[] args) {
        for (double v : medianSlidingWindow(new int[]{-2147483648,-2147483648,2147483647,-2147483648,-2147483648,
                -2147483648,2147483647,2147483647,2147483647,2147483647,-2147483648,2147483647,-2147483648}, 3)) {
            System.out.print(v+" ");
        }
    }

    /*
    * 能出答案，但是太傻逼了
    * */
    public static double[] medianSlidingWindow2(int[] nums, int k) {
        if (nums == null || k < 1 || nums.length < k) {
            return null;
        }
        double[] res = new double[nums.length - k + 1];
        int index = 0;
        int[] target = new int[k];
        int x = 0;
        for (int i = 0; i <= nums.length; ) {
            if (x < k && i < nums.length) {
                target[x++] = nums[i];
                i++;
            } else if (x == k) {//形成窗口
                int[] copy = new int[k];
                System.arraycopy(target, 1, copy, 0, k - 1);
                Arrays.sort(target);
                res[index++] = k % 2 == 0 ? (double) target[(k - 1) / 2] /2+ (double) target[(k - 1) / 2 + 1] / 2 : target[(k - 1) / 2];
                target = copy;
                x = k - 1;
            }else {
                break;
            }
        }
        return res;//50000ms
    }


    /*
     * 能出答案，但是太傻逼了
     * */
    public double[] medianSlidingWindow3(int[] nums, int k) {//3406
        if (nums == null || k < 1 || nums.length < k) {
            return null;
        }
        double[] res = new double[nums.length - k + 1];
        int index = 0;
        TreeMap<Integer, Integer> indexMap = new TreeMap<>();
        for (int i = 0; i < nums.length; i++) {
            indexMap.put(i, nums[i]);
            if (indexMap.size() > k) {
                indexMap.remove(i - k);
            }
            PriorityQueue<Integer> queue = new PriorityQueue<>();
            queue.addAll(indexMap.values());
            if (i - k + 1 >= 0) {//形成窗口
                double[] temp = new double[k];
                int j = 0;
                while (!queue.isEmpty()) {
                    temp[j++] = queue.poll();
                }
                res[index++] = k % 2 == 0 ? (temp[(k - 1) / 2] + temp[(k - 1) / 2 + 1]) / 2 : temp[(k - 1) / 2];
            }
        }
        return res;
    }
}

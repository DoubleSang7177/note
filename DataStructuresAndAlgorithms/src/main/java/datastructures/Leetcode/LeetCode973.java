package datastructures.Leetcode;

import java.util.Comparator;
import java.util.PriorityQueue;

public class LeetCode973 {
    public int[][] kClosest(int[][] points, int k) {
        int[][] ans=new int[k][2];
        PriorityQueue<int[]> queue=new PriorityQueue<>();

        for(int[] point:points){
            queue.offer(point);
        }
        for(int i=0;i<k;i++){
            ans[i]=queue.poll();
        }
        return ans;
    }

    public static void main(String[] args){
        System.out.println(Math.pow(-2,2));
    }
}

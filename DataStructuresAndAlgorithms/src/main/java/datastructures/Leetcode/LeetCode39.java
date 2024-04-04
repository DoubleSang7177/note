package datastructures.Leetcode;

import java.util.*;

public class LeetCode39 {
    public static List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> ans=new ArrayList<>();
        process(candidates,target,0,new ArrayDeque<>(),ans);
        return ans;
    }

    private static void process(int[] candidates, int rest, int curIndex, Deque<Integer> option, List<List<Integer>> ans){
        if(rest==0){
            ans.add(new ArrayList<>(option));
            return;
        }
        if(curIndex==candidates.length){
            return;
        }
        for(int num=0;num*candidates[curIndex]<=rest;num++){
            for(int j=0;j<num;j++){
                option.addLast(candidates[curIndex]);
            }
            process(candidates,rest-num*candidates[curIndex],curIndex+1,option,ans);
            for(int j=0;j<num;j++){
                option.removeLast();
            }
        }
    }

    public static void main(String[] args){
        combinationSum(new int[]{2,3,6,7},7);
    }
}

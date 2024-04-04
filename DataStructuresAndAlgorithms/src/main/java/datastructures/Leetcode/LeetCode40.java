package datastructures.Leetcode;

import java.util.*;

public class LeetCode40 {
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> ans=new ArrayList<>();
        Arrays.sort(candidates);
        dfs(candidates,0,target,new ArrayDeque(),ans);
        return ans;
    }
    public Set<List<Integer>> set=new HashSet<>();
    private void dfs(int[] candidates,int curIndex,int rest,Deque<Integer> path,List<List<Integer>> ans){
        if(rest==0){
            List<Integer> list=new ArrayList<>(path);
            // Collections.sort(list);
            if(!set.contains(list)){
                set.add(list);
                ans.add(list);
            }
            return;
        }
        if(rest<0||curIndex==candidates.length){//路径无效的两种状态
            return;
        }
        int count=1;
        for(int i=curIndex+1;i<candidates.length;i++){
            if(candidates[i]==candidates[curIndex]){
                count++;
            }else{
                break;
            }
        }
        for(int num=0;num<=count;num++){
            for(int j=0;j<num;j++){
                path.addLast(candidates[curIndex]);
            }
            dfs(candidates,curIndex+count,rest-num*candidates[curIndex],path,ans);
            for(int j=0;j<num;j++){
                path.removeLast();
            }
        }
    }

    public static void main(String[] args){
//        combinationSum2(new int[]{10,1,2,7,6,1,5},8);
    }
}

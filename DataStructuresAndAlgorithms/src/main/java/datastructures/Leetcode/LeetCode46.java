package datastructures.Leetcode;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class LeetCode46 {
    /**
     * 2ms
     * @param nums 待排列数组
     * @return 排列结果
     */
    public static List<List<Integer>> permute2(int[] nums) {
        List<List<Integer>> ans=new ArrayList<>();
        List<Integer> rest=new ArrayList<>();
        for(int i:nums){
            rest.add(i);
        }
        dfs(nums,new ArrayDeque<>(),rest,ans);
        return ans;
    }

    private static void dfs(int[] nums,Deque<Integer> path,List<Integer> rest,List<List<Integer>> ans){
        if(rest.isEmpty()){
            ans.add(new ArrayList<>(path));
            return;
        }
        List<Integer> copy=new ArrayList<>(rest);
        for(Integer number:copy){
            path.addLast(number);
            rest.remove(number);
            dfs(nums,path,rest,ans);
            rest.add(number);
            path.removeLast();
        }
    }


    /**
     * 1ms
     * @param nums 待排列数组
     * @return 排列结果
     */
    public static List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> ans=new ArrayList<>();
        boolean[] flag=new boolean[nums.length];
        dfs(nums,new ArrayDeque<>(),flag,ans);
        return ans;
    }
    private static void dfs(int[] nums,Deque<Integer> path,boolean[] flag,List<List<Integer>> ans){
        if(path.size()==nums.length){
            ans.add(new ArrayList<>(path));
            return;
        }
        for(int i=0;i<nums.length;i++){
            if(flag[i]){
                continue;
            }
            flag[i]=true;
            path.addLast(nums[i]);
            dfs(nums,path,flag,ans);
            flag[i]=false;
            path.removeLast();
        }
    }
    public static void main(String[] args){
        permute(new int[]{1,2,3});
    }
}

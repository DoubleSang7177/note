package datastructures.Leetcode;
import java.util.*;
public class LeetCode47 {
    /**
     * 2ms
     * @param nums 待排列数组
     * @return 排列结果
     */
    public List<List<Integer>> permuteUnique(int[] nums) {
        List<List<Integer>> ans=new ArrayList<>();
        Arrays.sort(nums);
        dfs(nums,new boolean[nums.length],new ArrayDeque<>(),ans);
        return ans;
    }
    private void dfs(int[] nums,boolean[] flag,Deque<Integer> path,List<List<Integer>> ans){
        if(path.size()==nums.length){
            ans.add(new ArrayList<>(path));
            return;
        }
        for(int i=0;i<nums.length;i++){
            if(flag[i] || (i>0 && nums[i]==nums[i-1] && !flag[i-1])){
                continue;
            }
            flag[i]=true;
            path.addLast(nums[i]);
            dfs(nums,flag,path,ans);
            flag[i]=false;
            path.removeLast();
        }
    }
    public HashMap<Integer,List<Integer>> map;

    /**
     * 1ms
     * @param nums 待排列数组
     * @return 排列结果
     */
    public List<List<Integer>> permuteUnique2(int[] nums) {
        List<List<Integer>> ans=new ArrayList<>();
        map=new HashMap<>();
        Arrays.sort(nums);
        for(int i=0;i<nums.length;i++){
            List<Integer> list=map.getOrDefault(nums[i],new ArrayList<>());
            list.add(i);
            map.put(nums[i],list);
        }
        dfs2(nums,new boolean[nums.length],new ArrayDeque<>(),ans);
        return ans;
    }
    private void dfs2(int[] nums, boolean[] flag, Deque<Integer> path, List<List<Integer>> ans){
        if(path.size()==nums.length){
            ans.add(new ArrayList<>(path));
            return;
        }
        for(Integer num:map.keySet()){
            for(Integer index:map.get(num)){
                if(!flag[index]){//未使用
                    flag[index]=true;
                    path.addLast(nums[index]);
                    dfs2(nums,flag,path,ans);
                    flag[index]=false;
                    path.removeLast();
                    break;
                }
            }
        }
    }
}

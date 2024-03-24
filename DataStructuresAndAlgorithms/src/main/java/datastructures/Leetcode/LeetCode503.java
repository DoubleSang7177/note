package datastructures.Leetcode;

import java.util.HashMap;
import java.util.Stack;

public class LeetCode503 {
    public int[] nextGreaterElements(int[] nums) {
        Stack<Integer> stack=new Stack<>();
        HashMap<Integer,Integer> map=new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            while (!stack.isEmpty()&&nums[i]>nums[stack.peek()]){
                map.put(stack.pop(),i);
            }
            stack.push(i);
        }
        int x=stack.peek();
        for (int i = 0; i < x; i++) {
            while (!stack.isEmpty()&&nums[i]>nums[stack.peek()]){
                map.put(stack.pop(),nums[i]);
            }
            stack.push(i);
        }
        int[] res=new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            res[i]=map.getOrDefault(i,-1);
        }
        return res;
    }
}

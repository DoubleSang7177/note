package datastructures.Leetcode;
import java.util.HashMap;
import java.util.Stack;
/*
nums1 中数字 x 的 下一个更大元素 是指 x 在 nums2 中对应位置 右侧 的 第一个 比 x 大的元素。
给你两个 没有重复元素 的数组 nums1 和 nums2 ，下标从 0 开始计数，其中nums1 是 nums2 的子集。
对于每个 0 <= i < nums1.length ，找出满足 nums1[i] == nums2[j] 的下标 j ，并且在 nums2 确定
nums2[j] 的 下一个更大元素 。如果不存在下一个更大元素，那么本次查询的答案是 -1 。
返回一个长度为 nums1.length 的数组 ans 作为答案，满足 ans[i] 是如上所述的 下一个更大元素 。
* */
public class LeetCode496 {
    /**
     * 单调栈+哈希表
     * 时间复杂度 O(m+n)
     * 空间复杂度：O(n)，用于存储哈希表。栈呢？
     * @param nums1 数组1 (长度m)
     * @param nums2 数组2 (长度n)
     * @return 结果
     */
    public static int[] nextGreaterElement(int[] nums1, int[] nums2) {
        Stack<Integer> stack=new Stack<>();
        HashMap<Integer,Integer> nextBigger =new HashMap<>();
        for (int j = 0; j < nums2.length; j++) {
            while (!stack.isEmpty()&&nums2[j]>stack.peek()){
                nextBigger.put(stack.pop(),nums2[j]);
            }
            stack.push(nums2[j]);
        }
        int[] ans=new int[nums1.length];
        for (int i = 0; i < ans.length; i++) {
            ans[i]=nextBigger.getOrDefault(nums1[i],-1);
        }
        return ans;
    }

    /**
     * 暴力解法
     * 时间复杂度：O(mn)，其中 m是 nums1的长度，n是 nums2的长度。
     * 空间复杂度 O(1)
     * @param nums1 数组1
     * @param nums2 数组2
     * @return 结果
     */
    public static int[] nextGreaterElement2(int[] nums1, int[] nums2) {
        int[] ans=new int[nums1.length];
        for (int i = 0; i < nums1.length; i++) {
            int j=0;
            while (j<nums2.length && nums2[j]!=nums1[i]){
                j++;
            }
            int k=j+1;
            while (k<nums2.length && nums2[k]<nums2[j]){
                k++;
            }
            ans[i]=k==nums2.length?-1:nums2[k];
        }
        return ans;
    }
    public static void main(String[] args) {
        int[] num1={2,4};
        int[] num2={1,2,3,4};
        nextGreaterElement(num1,num2);
    }
}

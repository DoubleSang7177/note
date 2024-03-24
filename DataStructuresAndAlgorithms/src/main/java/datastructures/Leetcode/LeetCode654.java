package datastructures.Leetcode;
import datastructures.tree.TreeNode;
import java.util.Stack;

/*
*给定一个不重复的整数数组 nums。 最大二叉树 可以用下面的算法从 nums 递归地构建:
    创建一个根节点，其值为 nums 中的最大值。
    递归地在最大值 左边 的 子数组前缀上 构建左子树。
    递归地在最大值 右边 的 子数组后缀上 构建右子树。
 返回 nums 构建的最大二叉树 。
* */
public class LeetCode654 {
    /**
     * 单调栈 （复习看B站视频）https://www.bilibili.com/video/BV14N4y1F7tQ/?spm_id_from=333.337.search-card.all.click&vd_source=c7fe1a75eb237483fcd6fa930e7ab4eb
     * @param nums 不重复的整数数组
     * @return 构建好的二叉树根节点
     */
    public static TreeNode constructMaximumBinaryTree(int[] nums){
        Stack<TreeNode> stack=new Stack<>();
        for (int i = 0; i < nums.length; i++) {
            TreeNode cur=new TreeNode(nums[i]);
            while (!stack.isEmpty()&&nums[i]>stack.peek().val){
                cur.left=stack.pop();
            }
            if (!stack.isEmpty()){
                stack.peek().right=cur;
            }
            stack.push(cur);
        }
        return stack.get(stack.size()-1);
    }
    /**
     * 递归实现
     * @param nums 不重复的整数数组
     * @return 构建好的二叉树根节点
     */
    public TreeNode constructMaximumBinaryTree2(int[] nums){
        return process(nums,0,nums.length-1);
    }
    private TreeNode process(int[] nums,int left,int right){
        if (left>right){
            return null;
        }
        int max=left;
        for (int i = left+1; i <= right; i++) {
            max=nums[i]>nums[max]?i:max;
        }
        TreeNode head=new TreeNode(nums[max]);
        head.left=process(nums,left,max-1);
        head.right=process(nums,max+1,right);
        return head;
    }
    /**
     * 递归实现2
     * 不建议！子数组完全可以用指针去表示，不需要赋值子数组到新空间
     * @param nums 不重复的整数数组
     * @return 构建好的二叉树根节点
     */
    public TreeNode constructMaximumBinaryTree3(int[] nums) {
        if (nums==null||nums.length==0){
            return null;
        }
        int max=0;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i]>nums[max]){
                max=i;
            }
        }
        TreeNode head=new TreeNode(nums[max]);
        int[] leftNum=new int[max];
        System.arraycopy(nums,0,leftNum,0,max);
        int[] rightNum=new int[nums.length-max-1];
        if (max<nums.length-1){
            System.arraycopy(nums,max+1,rightNum,0,nums.length-max-1);
        }
        head.left=constructMaximumBinaryTree(leftNum);
        head.right=constructMaximumBinaryTree(rightNum);
        return head;
    }
    public static void main(String[] args) {
        constructMaximumBinaryTree(new int[]{3,2,1,6,0,5});
    }
}

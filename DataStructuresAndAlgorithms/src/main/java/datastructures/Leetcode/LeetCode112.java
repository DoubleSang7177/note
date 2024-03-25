package datastructures.Leetcode;
import datastructures.tree.TreeNode;
/*
* 深度优先搜索
* 给你二叉树的根节点 root 和一个表示目标和的整数 targetSum 。
* 判断该树中是否存在 根节点到叶子节点 的路径，这条路径上所有节
* 点值相加等于目标和 targetSum 。如果存在，返回 true ；否则，返回 false 。
* */
public class LeetCode112 {
    /**
     * 判断是否存在满足条件的路径
     * @param root 根节点
     * @param targetSum 路径和
     * @return 判断结果
     */
    public boolean hasPathSum(TreeNode root, int targetSum) {
        if (root==null){//空树不存在根节点到叶子节点的路径
            return false;
        }
        return process(root,0,targetSum);
    }

    /**
     * 判断是否存在满足条件的路径
     * @param root 根节点
     * @param targetSum 路径和
     * @param cur 当前节点之前的路径和
     * @return 判断结果
     */
    private boolean process(TreeNode root, int cur, int targetSum) {
        if (root==null){
            return false;
        }
        if (root.left==null && root.right==null){//叶节点
            return cur+root.val==targetSum;
        }
        return process(root.left,cur+root.val,targetSum) || process(root.right,cur+root.val,targetSum);
    }
}

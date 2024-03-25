package datastructures.Leetcode;
import datastructures.tree.TreeNode;
import java.util.Scanner;

/*
* 二叉树中的 路径 被定义为一条节点序列，
* 序列中每对相邻节点之间都存在一条边。
* 同一个节点在一条路径序列中 至多出现一次 。
* 该路径 至少包含一个 节点，且不一定经过根节点。
* 路径和 是路径中各节点值的总和。
* 给你一个二叉树的根节点 root ，返回其 最大路径和 。
* */
public class LeetCode124 {
    /**
     * @param root 根节点
     * @return 最大路径之和
     */
    public int maxPathSum(TreeNode root) {
        maxGain(root);
        return maxSum;
    }
    public int maxSum =Integer.MIN_VALUE;

    /**
     * 首先，考虑实现一个简化的函数 maxGain(node)，
     * 该函数计算二叉树中的一个节点的最大贡献值，具体而言，
     * 就是在以该节点为根节点的子树中寻找以该节点为起点的一条路径，
     * 使得该路径上的节点值之和最大。具体而言，该函数的计算如下。
     * 空节点的最大贡献值等于 0。
     * 非空节点的最大贡献值等于节点值与其子节点中的最大贡献值之和
     * （对于叶节点而言，最大贡献值等于节点值）。
     * @param node 当前节点
     * @return 最大贡献值
     */
    private int maxGain(TreeNode node){
        if (node ==null){
            return 0;
        }
        int leftGain = Math.max(maxGain(node.left),0);
        int rightGain = Math.max(maxGain(node.right),0);
        maxSum =Math.max(maxSum,leftGain+rightGain+ node.val);
        return node.val+Math.max(leftGain, rightGain);
    }

    public static void main(String[] args) {
        Scanner in=new Scanner(System.in);
        while (in.hasNext()){
            int a =in.nextInt();
            int b=in.nextInt();
            String s=in.next();
        }


    }
}

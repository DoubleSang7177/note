package datastructures.Leetcode;
import java.util.ArrayList;
import java.util.List;
/*
* 给你一个整数 n,请你生成并返回所有由 n 个节点组成且节点值从 1 到 n
* 互不相同的不同二叉搜索树 。可以按任意顺序返回答案。
* */
public class LeetCode95 {
    public static class TreeNode {
      int val;
      TreeNode left;
      TreeNode right;
      TreeNode() {}
      TreeNode(int val) { this.val = val; }
      TreeNode(int val, TreeNode left, TreeNode right) {
         this.val = val;
          this.left = left;
          this.right = right;
      }
  }
    public static List<TreeNode> generateTrees(int n) {
        if (n==0){
            return new ArrayList<>();
        }
        return process(1,n);
    }



    private static List<TreeNode> process(int start, int end){
        List<TreeNode> options=new ArrayList<>();
        if (start>end){
            options.add(null);
            return options;
        }
        for (int i = start; i <= end; i++) {
            List<TreeNode> leftOp = process(start, i - 1);
            List<TreeNode> rightOp = process(i + 1, end);
            for (TreeNode left : leftOp) {
                for (TreeNode right : rightOp) {
                    TreeNode head=new TreeNode(i);//一定在循环最内层new出head对象，否则只是对值为i的同一个节点的修改行为
                    head.left=left;
                    head.right=right;
                    options.add(head);
                }
            }
        }
        return options;
    }

    public static void main(String[] args) {
        List<TreeNode> list= generateTrees(3);
        System.out.println(list);
    }
}

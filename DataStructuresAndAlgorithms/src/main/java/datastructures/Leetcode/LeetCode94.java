package datastructures.Leetcode;
import datastructures.tree.TreeNode;
import java.util.ArrayList;
import java.util.List;
/*
* 给定一个二叉树的根节点 root ，返回它的中序遍历 。
* */
public class LeetCode94 {
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> res=new ArrayList<>();
        inOrder(root,res);
        return res;
    }

    private void inOrder(TreeNode root, List<Integer> res) {
        if (root==null){
            return;
        }
        inOrder(root.left,res);
        res.add(root.val);
        inOrder(root.right,res);
    }
}

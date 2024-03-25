package datastructures.Leetcode;
import datastructures.tree.TreeNode;
import java.util.ArrayList;
import java.util.List;
/*
* 给你二叉树的根节点 root 和一个整数目标和 targetSum ，
* 找出所有 从根节点到叶子节点 路径总和等于给定目标和的路径。
* 叶子节点 是指没有子节点的节点。
* */
public class LeetCode113 {
    /**
     * 寻找路径
     * @param root 根节点
     * @param targetSum 目标路径和
     * @return 路径集合
     */
    public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        List<List<Integer>> res=new ArrayList<>();
        process(root,targetSum,res,new ArrayList<>());
        return res;
    }

    /**
     * @param root 当前根节点
     * @param targetSum 目标路径和
     * @param res 最终满足条件的路径集合
     * @param path 路径
     */
    private void process(TreeNode root, int targetSum, List<List<Integer>> res, List<Integer> path) {
        if (root==null){
            return;
        }
        targetSum-=root.val;
        path.add(root.val);
        if (root.left==null && root.right==null&&targetSum==0){
            res.add(path);
        }else{
            process(root.left,targetSum,res, new ArrayList<>(path));
            process(root.right,targetSum,res, path);
        }
    }
}

package datastructures.Leetcode;
import datastructures.tree.TreeNode;
/*
* 给定一个二叉搜索树的根节点 root 和一个值key，删除二叉搜索树中的key对应的节点，
* 并保证二叉搜索树的性质不变。返回二叉搜索树（有可能被更新）的根节点的引用。
* 一般来说，删除节点可分为两个步骤：
* 首先找到需要删除的节点；
* 如果找到了，删除它。
* 时间复杂度：O(n)
* 空间复杂度：O(n)
* */
public class LeetCode450 {

    /**
     * 删除以root为根节点的树上的key节点
     * 并返回该树根节点
     * @param root 根节点
     * @param key 目标值
     * @return 删除目标值后树的根节点
     */
    public static TreeNode deleteNode(TreeNode root, int key) {
        if (root == null) {
            return null;
        }
        // 在左子树上寻找目标值
        if (root.val > key) {
            root.left = deleteNode(root.left, key);
            return root;
        }
        // 在右子树上寻找目标值
        if (root.val < key) {
            root.right = deleteNode(root.right, key);
            return root;
        }
        //要删除的是当前节点
        if (root.left == null && root.right == null) {//root为叶节点
            return null;
        }
        if (root.right == null) {//没有右子树，那么返回左子树（就是删除root）
            return root.left;
        }
        if (root.left == null) {//没有左子树，那么返回右子树（就是删除root）
            return root.right;
        }
        //左右子树均存在，将右子树上的最小值节点rightMostSmall的值赋给root并删除rightMostSmall
        TreeNode rightMostSmall=root.right;
        while (rightMostSmall.left!=null){
            rightMostSmall=rightMostSmall.left;
        }
        root.val=rightMostSmall.val;
        root.right=deleteNode(root.right,rightMostSmall.val);//在右子树上删除key为rightMostSmall.val的节点
        return root;
    }
    public static void main(String[] args) {
        TreeNode root = new TreeNode(5, new TreeNode(3, new TreeNode(2, null, null), new TreeNode(4, null, null)), new TreeNode(6, null, new TreeNode(7, null, null)));
        deleteNode(root, 3);
        System.out.println();
    }
}

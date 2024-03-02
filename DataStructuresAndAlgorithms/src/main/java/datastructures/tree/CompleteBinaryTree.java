package datastructures.tree;

import java.util.LinkedList;
import java.util.Queue;

/*
* 判断一个二叉树是不是完全二叉树
* */
public class CompleteBinaryTree {


    public static boolean isW(TreeNode head){
        if (head==null){
            return false;
        }
        Queue<TreeNode> queue=new LinkedList<>();
        /*
        * 标记：是否遇到过第一个左右子不全的节点,第一次遇到时标记为true，
        * 后面遇到叶节点也会标记为true但是不影响判断,因为不改变flag的值
        * */
        boolean flag=false;
        queue.add(head);
        while (!queue.isEmpty()){
            head= queue.poll();
            /*
            * 判断是否满足完全二叉树的特性
            * 1. 存在右子树却没有左子树不是二叉树
            * 2. 如果是第一个左右子不全，则后续皆是叶节点-->已经遇到过第一个左右子不全的节点，
            *    但是还遇到不是叶节点的节点flag &&(head.right!=null || head.left!=null）
            * */
            if ((head.right!=null && head.left==null)||(flag &&(head.right!=null || head.left!=null))){
                return false;
            }
            if (head.left == null || head.right == null){
                flag=true;
            }
            if (head.left!=null){
                queue.add(head.left);
            }
            if (head.right!=null){
                queue.add(head.right);
            }
        }
        return true;
    }

    public static void main(String[] args) {
        TreeNode n8= new TreeNode(8, null, null);
        TreeNode n4 = new TreeNode(4, n8, null);
        TreeNode n5 = new TreeNode(5, null, null);
        TreeNode n6 = new TreeNode(6, null, null);
        TreeNode n7 = new TreeNode(7, null, null);
        TreeNode n2 = new TreeNode(2, n4, n5);
        TreeNode n3 = new TreeNode(3, n6, n7);
        TreeNode head = new TreeNode(1, n2, n3);
        System.out.println(isW(head));
    }
}

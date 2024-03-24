package datastructures.Leetcode;
import datastructures.tree.TreeNode;

/*
验证搜索二叉树
* */
public class LeetCode98 {
    public static long pre=Long.MIN_VALUE;
    /**
     * 验证搜索二叉树
     * @param root 根节点
     * @return 判断结果
     */
    public static boolean isValidBST(TreeNode root) {
        if (root==null){
            return true;
        }
        if (isValidBST(root.left)&&root.val>pre){
            pre=root.val;
            return isValidBST(root.right);
        }else{
            return false;
        }
    }

    public static long preValue =Long.MIN_VALUE;
    private static boolean isValidBST2(TreeNode root) {
        if (root == null) {
            return true;
        }
        boolean left= isValidBST2(root.left);
        if (!left){
            return false;
        }
        if (root.val<= preValue){
            return false;
        }else{
            preValue = root.val;
        }
        return isValidBST2(root.right);
    }
    public static void main(String[] args) {
        TreeNode root=new TreeNode(5,new TreeNode(1),new TreeNode(4,new TreeNode(3),new TreeNode(6)));
        isValidBST2(root);
    }
}

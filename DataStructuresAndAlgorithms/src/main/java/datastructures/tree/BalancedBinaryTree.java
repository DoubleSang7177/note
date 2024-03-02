package datastructures.tree;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

/*
* 平衡二叉树
* 左右子树的高度差不会超过1(高度差小于2)
* */
public class BalancedBinaryTree {
    private static class Result{
        boolean isBalanced;
        int height;

        public Result(boolean isBalanced, int height) {
            this.isBalanced = isBalanced;
            this.height = height;
        }
    }
    public static boolean isBalancedBinaryTree(TreeNode head){
        if (head==null){
            return false;
        }
        return process(head).isBalanced;
    }
    private static Result process(TreeNode head){
        if (head==null){
            return new Result(true,0);
        }
        Result leftResult = process(head.left);
        Result rightResult = process(head.right);
        int height=Math.max(leftResult.height,rightResult.height)+1;//高度就是左右子节点中高一点的节点的高度加一！！！
        boolean isBalanced=leftResult.isBalanced&&rightResult.isBalanced&&(Math.abs(leftResult.height-rightResult.height)<2);
        return new Result(isBalanced,height);
    }

    /*
    * 获取一个树节点的高度
    * */
    public static int getHigh(TreeNode head){
        if (head==null){
            return 0;
        }
        Queue<TreeNode> queue=new LinkedList<>();
        HashMap<TreeNode,Integer> levelMap=new HashMap<>();
        int L =1;
        queue.add(head);
        levelMap.put(head,1);
        while (!queue.isEmpty()){
            TreeNode curNode = queue.poll();
            Integer curNodeLevel = levelMap.get(curNode);
            L=curNodeLevel;
            if (curNode.left!=null){
                levelMap.put(curNode.left,curNodeLevel+1);
                queue.add(curNode.left);
            }
            if (curNode.right!=null){
                levelMap.put(curNode.right,curNodeLevel+1);
                queue.add(curNode.right);
            }
        }

        return L;
    }

    public static void main(String[] args) {
        TreeNode n9=new TreeNode(9,null,null);
        TreeNode n8= new TreeNode(8, n9, null);
        TreeNode n4 = new TreeNode(4, null, n8);
        TreeNode n5 = new TreeNode(5, null, null);
        TreeNode n6 = new TreeNode(6, null, null);
        TreeNode n7 = new TreeNode(7, null, null);
        TreeNode n2 = new TreeNode(2, n4, n5);
        TreeNode n3 = new TreeNode(3, n6, n7);
        TreeNode head = new TreeNode(1, n2, n3);
        System.out.println(isBalancedBinaryTree(head));
    }
}


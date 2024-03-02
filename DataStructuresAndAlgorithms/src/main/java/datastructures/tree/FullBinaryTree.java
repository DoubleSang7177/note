package datastructures.tree;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

/*
* 判断是否为满二叉树
* 最大的深度L和节点数N
* 满足：N=2^L-1
* */
public class FullBinaryTree {
    /*
    * 非递归方式判断
    * 自己写的应该没有问题的
    * */
    public static boolean isFullBinaryTreeUnRecur(TreeNode head){
        if (head==null){
            return false;
        }
        Queue<TreeNode> queue=new LinkedList<>();
        HashMap<TreeNode,Integer> levelMap=new HashMap<>();
        int L =1;
        int N =0;
        queue.add(head);
        levelMap.put(head,1);
        while (!queue.isEmpty()){
            TreeNode curNode = queue.poll();
            Integer curNodeLevel = levelMap.get(curNode);
            L=curNodeLevel;
            N++;
            if (curNode.left!=null){
                levelMap.put(curNode.left,curNodeLevel+1);
                queue.add(curNode.left);
            }
            if (curNode.right!=null){
                levelMap.put(curNode.right,curNodeLevel+1);
                queue.add(curNode.right);
            }
        }

        return N==(1<<L) -1;
    }

    private static class Result{
        int height;
        int nodes;

        public Result(int level, int nodes) {
            this.height = level;
            this.nodes = nodes;
        }
    }
    public static boolean isFullBinaryTreeRecur(TreeNode head){
        if (head==null){
            return false;
        }
        Result result = isFullBinaryTree(head);
//        return result.nodes==(Math.pow(2,result.height) -1);
        return result.nodes==(1<< result.height) -1;//位运算这里可能有问题。。是这么写的吧，是是

    }
    public static Result isFullBinaryTree(TreeNode head){
        if (head==null){
            return new Result(0,0);
        }
        Result leftResult = isFullBinaryTree(head.left);
        Result rightResult = isFullBinaryTree(head.right);
        int level=Math.max(leftResult.height,rightResult.height)+1;
        int nodes=leftResult.nodes+ rightResult.nodes+1;
        return new Result(level,nodes);
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
        System.out.println(isFullBinaryTreeRecur(head));
        System.out.println(isFullBinaryTreeUnRecur(head));
    }


}

package datastructures.Leetcode;
import datastructures.tree.TreeNode;
import java.util.*;
/*
* 层序遍历
* */
public class LeetCode102 {
    /**
     * 层序遍历使用 队列数据结构
     * @param root 根节点
     * @return 层序遍历结果集合
     */
    public static List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> res=new LinkedList<>();
        if(root==null){
            return res;
        }
        Queue<TreeNode> queue=new LinkedList<>();
        queue.add(root);
        while(!queue.isEmpty()){
            int size=queue.size();
            List<Integer> list=new ArrayList<>();
            for(int i=0;i<size;i++){
                TreeNode curNode=queue.poll();
                list.add(curNode.val);
                if(curNode.left!=null){
                    queue.add(curNode.left);
                }
                if(curNode.right!=null){
                    queue.add(curNode.right);
                }
            }
            res.add(list);
        }
        return res;

    }
    public static void main(String[] args) {
//        3,9,20,null,null,15,7
        TreeNode root = new TreeNode(3, new TreeNode(9), new TreeNode(20, new TreeNode(15), new TreeNode(7)));
        levelOrder(root);
    }
}

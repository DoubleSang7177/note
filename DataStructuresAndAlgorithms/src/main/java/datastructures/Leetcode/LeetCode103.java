package datastructures.Leetcode;

import datastructures.tree.TreeNode;

import java.util.*;
/*
* 给你二叉树的根节点 root ，返回其节点值的 锯齿形层序遍历 。
* （即先从左往右，再从右往左进行下一层遍历，以此类推，层与层之间交替进行）。
* */
public class LeetCode103 {
    /**
     * 双端队列
     * @param root 根节点
     * @return 结果
     */
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> res=new ArrayList<>();
        if(root==null){
            return res;
        }
        Deque<TreeNode> deque=new LinkedList<>();//队列存放层序遍历的节点
        HashMap<TreeNode,Integer> map=new HashMap<>();//记录各节点的level
        deque.add(root);
        map.put(root,1);
        int curLevel=1;//当前遍历到的层数
        Deque<Integer> list=new LinkedList<>();//当前层的结果集
        boolean flag=true;
        while(!deque.isEmpty()){
            TreeNode cur=deque.poll();
            if(map.get(cur)==curLevel){
                if(flag){
                    list.addLast(cur.val);
                }else{
                    list.addFirst(cur.val);
                }
            }else{
                curLevel++;
                res.add(new ArrayList<>(list));
                list=new LinkedList<>();
                list.add(cur.val);
                flag=!flag;
            }
            if(cur.left!=null){
                deque.add(cur.left);
                map.put(cur.left,curLevel+1);
            }
            if(cur.right!=null){
                deque.add(cur.right);
                map.put(cur.right,curLevel+1);
            }
        }
        res.add(new ArrayList<>(list));
        return res;

    }
}

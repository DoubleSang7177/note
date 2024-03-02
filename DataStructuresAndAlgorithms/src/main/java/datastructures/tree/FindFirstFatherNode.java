package datastructures.tree;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/*
* 给定一棵树上的两个树节点
* 找出最低的公共祖先节点
* */
public class FindFirstFatherNode {
    /*
     * 最佳方案！！！
     * */
    public static TreeNode bestSolution(TreeNode head,TreeNode n1,TreeNode n2){
        if (head==null || head==n1 || head==n2){
            return head;
        }
        TreeNode left = bestSolution(head.left, n1, n2);
        TreeNode right = bestSolution(head.right, n1, n2);
        //左右两棵树都有返回值，就是汇聚
        if (left!=null && right!=null){
            return head;
        }
        //不是都有返回值，返回有值的那个，也有可能都没有值
        return left!=null?left:right;
    }

    /*
    * 把一个节点到根节点经过的节点都放到一个set集合中
    * 然后让另一个节点往上窜，如果往上窜的过程中与set集合中的节点相遇
    * 那么该节点就是最低的公共祖先节点
    * */
    public static TreeNode findFFNode(TreeNode head,TreeNode n1,TreeNode n2){
        if (head==null){
            return null;
        }
        HashMap<TreeNode,TreeNode> fatherMap=new HashMap<>();
        fatherMap.put(head,head);
        getFatherNode(head,fatherMap);
        Set<TreeNode> set1=new HashSet<>();
        TreeNode cur=n1;
        while (fatherMap.get(cur)!=cur){
            set1.add(cur);
            cur=fatherMap.get(cur);
        }
        set1.add(head);
        while (fatherMap.get(n2)!=n2){
            if (set1.contains(n2)){
                return n2;
            }
            n2=fatherMap.get(n2);
        }
        return head;
    }
    /*
    * 遍历整棵树获取每个节点的父节点并放到HashMap中
    * HashMap<子节点,父节点>
    * */
    private static void getFatherNode(TreeNode head, HashMap<TreeNode,TreeNode> fatherMap){
       if (head==null){
           return;
       }
        fatherMap.put(head.left,head);
        fatherMap.put(head.right,head);
       getFatherNode(head.left,fatherMap);
       getFatherNode(head.right,fatherMap);
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
        System.out.println(findFFNode(head, n5, n9).val);
        System.out.println(bestSolution(head, n5, n8).val);
    }
}

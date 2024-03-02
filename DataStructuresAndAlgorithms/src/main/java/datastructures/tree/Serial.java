package datastructures.tree;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

/*
* 树的序列化与反序列化
* */
public class Serial {

    //以head为头的树，请序列化为字符串并返回
    public static String serialByPre(TreeNode head){
        if (head==null){
            return "#_";
        }
        String res=head.val+"_";
        res += serialByPre(head.left);
        res += serialByPre(head.right);
        return res;
    }

    public static TreeNode reConPreOrder(String s){
        String[] values = s.split("_");
        //方法 2
        Queue<String> queue=new LinkedList<>();
        for (String value : values) {
            queue.add(value);
        }
        return process2(queue);
        //方法1
//        return processX(values);
    }
    public static TreeNode process2(Queue<String> queue){
        String cur = queue.poll();
        if (Objects.equals(cur, "#")){
            return null;
        }
        TreeNode head=new TreeNode(Integer.parseInt(cur));
        head.left=process2(queue);
        head.right=process2(queue);
        return head;
    }
    public static int index=0;
    public static TreeNode process(String[] values){
        if (Objects.equals(values[index], "#")){
            return null;
        }
        int cur=index;
        //建左子树
        index++;
        TreeNode left = process(values);
        //建右子树
        index++;
        TreeNode right = process(values);
        return new TreeNode(Integer.parseInt(values[cur]),left,right);
    }

    public static void preOrder(TreeNode head) {
        if (head == null) {
            return;
        }
        System.out.print(head.val + " ");
        preOrder(head.left);
        preOrder(head.right);
    }
    public static void main(String[] args) {
        TreeNode n9=new TreeNode(9,null,null);
        TreeNode n8= new TreeNode(8, n9, null);
        TreeNode n4 = new TreeNode(4, null, null);
        TreeNode n5 = new TreeNode(5, null, null);
        TreeNode n6 = new TreeNode(6, null, null);
        TreeNode n7 = new TreeNode(7, null, null);
        TreeNode n2 = new TreeNode(2, n4, n5);
        TreeNode n3 = new TreeNode(3, n6, n7);
        TreeNode head = new TreeNode(1, n2, n3);
        System.out.println(serialByPre(head));
        String s=serialByPre(head);
        TreeNode head1 = reConPreOrder(s);
        preOrder(head1);
        System.out.println();
        preOrder(head);
    }
}

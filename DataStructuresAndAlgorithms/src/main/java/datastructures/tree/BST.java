package datastructures.tree;

import java.util.*;

/*
 * 判断是不是搜索树
 * 搜索树：左子树比自己小，右子树比自己大，每个子树都满足该特性
 * */
public class BST {

    /*
     * 采用morris实现二叉树中序遍历
     * 来判断是不是搜索二叉树
     * */
    public static boolean morrisInIsBST(TreeNode head){
        if (head ==null){
            return false;
        }
        TreeNode cur=head;
        TreeNode mostRight;
        int preValue=Integer.MIN_VALUE;
        while (cur!=null){//过流程
            mostRight=cur.left;//找cur左子树上的最右节点，从左子树头节点开始寻找mostRight
            if (mostRight!=null){//有左子树
                while (mostRight.right!=null && mostRight.right!=cur){
                    mostRight=mostRight.right;
                }
                if (mostRight.right==null){//这是第一次来到cur
                    mostRight.right=cur;
                    cur=cur.left;
                    continue;
                }else{//mostRight.right==cur
                    mostRight.right=null;//之前来过cur,接下来要移到cur.right
                }
            }
            if (cur.val<=preValue){
                return false;
            }
            preValue=cur.val;
            cur=cur.right;//cur没有左子树或者已经走完左子树
        }
        return true;
    }
    /*
     * 二叉树节点
     * */
    public static class Node {
        Integer value;
        Node left;
        Node right;

        public Node(Integer value, Node left, Node right) {
            this.value = value;
            this.left = left;
            this.right = right;
        }
    }

    /*
    * 方法一： 搞复杂了，没有那么复杂
    *  遍历 中序遍历后的队列
    * 判断是否满足搜索树的条件
    * */
    public static boolean isBST(Node head) {
        Queue<Integer> queue = process1(head);
        if (queue!=null){
            int s = queue.poll();
            while (!queue.isEmpty()) {
                int m = queue.poll();
                if (s >= m) {
                    return false;
                }
                s=m;
            }
        }
        return true;
    }
    /*
    * 递归实现中序遍历1
    * 将链表中序遍历放到队列中返回
    * */
    private static Queue<Integer> process1(Node head) {
        if (head == null) {
            return null;
        }
        Queue<Integer> left = process1(head.left);
        if (left != null) {
            left.add(head.value);
            Queue<Integer> right = process1(head.right);
            while (right != null && !right.isEmpty()) {
                left.add(right.poll());
            }
            return left;
        } else {
            Queue<Integer> cur = new LinkedList<>();
            cur.add(head.value);
            Queue<Integer> right = process1(head.right);
            while (right != null && !right.isEmpty()) {
                left.add(right.poll());
            }
            return cur;
        }
    }

    /*
     * 方法二：推荐！
     * 递归实现中序遍历2
     * */
    public static Integer preValue =Integer.MIN_VALUE;
    private static boolean isBST2(Node head) {
        if (head == null) {
            return true;
        }
        boolean left= isBST2(head.left);
        if (!left){
            return false;
        }
        if (head.value<= preValue){
            return false;
        }else{
            preValue =head.value;
        }
        return isBST2(head.right);
    }

    /*
    * 方法三：最简单
    * 中序遍历放集合
    * */
    public static boolean isBST3(Node head){
        if (head==null){
            return false;
        }
        List<Node> list=new ArrayList<>();
        process3(head,list);
        Node preNode = list.remove(0);
        for (Node cur : list) {
            if (preNode.value>=cur.value){
                return false;
            }
            preNode=cur;
        }
        return true;
    }
    private static void process3(Node head,List<Node> list){
        if (head == null) {
            return;
        }
       process3(head.left,list);
        list.add(head);
        process3(head.right,list);
    }

    /*
     * 递归判断是不是搜索树
     * */
    private static class Result{
        int max;
        int min;
        boolean isBST;

        public Result(int max, int min, boolean isBST) {
            this.max = max;
            this.min = min;
            this.isBST = isBST;
        }
    }
    public static Result isBstRecur(Node head){
        if (head==null){
            return new Result(Integer.MIN_VALUE,Integer.MAX_VALUE,true);
        }
        Result leftResul = isBstRecur(head.left);
        Result rightResult = isBstRecur(head.right);
        int max=Math.max(head.value,Math.max(leftResul.max, rightResult.max));
        int min=Math.min(head.value,Math.min(leftResul.min,rightResult.min));
        boolean isBST= leftResul.isBST && rightResult.isBST && (head.value>leftResul.max)&&(head.value< rightResult.min);
        return new Result(max,min,isBST);
    }

    /*
     * 利用非递归实现中序遍历
     * 来判断是不是搜索树
     * */
    public static boolean isBstUnRecur(Node head) {
        if (head != null) {
            Stack<Node> stack = new Stack<>();
            Integer preValue =Integer.MIN_VALUE;
            while (!stack.isEmpty() || head!=null){//栈非空或者还有需要压入栈的节点时需要走循环
                if (head!=null){
                    stack.push(head);
                    head=head.left;
                }else{
                    head = stack.pop();
                    if (head.value<= preValue){
                        return false;
                    }
                    preValue =head.value;
//                    System.out.printEdge(head.value+" ");//中序遍历
                    head=head.right;
                }
            }
        }
        return true;
    }
    public static void main(String[] args) {
        Node n8 = new Node(1, null, null);
        Node n4 = new Node(2, n8, null);
        Node n5 = new Node(4, null, null);
        Node n6 = new Node(61, null, null);
        Node n7 = new Node(8, null, null);
        Node n2 = new Node(3, n4, n5);
        Node n3 = new Node(7, n6, n7);
        Node head = new Node(5, n2, n3);
        System.out.println(isBST(head));
        System.out.println(isBST2(head));
        System.out.println(isBST3(head));
        System.out.println(isBstUnRecur(head));
        System.out.println(isBstRecur(head).isBST);
    }
}

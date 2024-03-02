package datastructures.tree;
import java.util.*;
import java.util.Queue;
import java.util.Stack;

/*
 * 二叉树
 * 三种遍历方式+层序遍历
 * 求最大宽度
 * */
public class BinaryTree {
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
     * 递归实现前序遍历
     * */
    public static void preOrder(Node head) {
        if (head == null) {
            return;
        }
        System.out.print(head.value + " ");
        preOrder(head.left);
        preOrder(head.right);
    }

    /*
     * 非递归实现前序遍历
     * */
    public static void preOrderUnRecur(Node head) {
        if (head != null) {
            Stack<Node> stack = new Stack<>();
            stack.push(head);
            while (!stack.isEmpty()) {
                head = stack.pop();
                System.out.print(head.value + " ");
                if (head.right != null) {
                    stack.push(head.right);
                }
                if (head.left != null) {
                    stack.push(head.left);
                }
            }
        }
    }

    /*
     * 递归实现中序遍历
     * */
    public static void inOrder(Node head) {
        if (head == null) {
            return;
        }
        inOrder(head.left);
        System.out.print(head.value + " ");
        inOrder(head.right);
    }

    /*
     * 非递归实现中序遍历
     * */
    public static void inOrderUnRecur(Node head) {
        /*if (head != null) {
            Stack<TrieNode> DSA.stack = new Stack<>();
            while (head != null) {
                DSA.stack.push(head);
                head = head.left;
            }
            while (!DSA.stack.isEmpty()) {
                head = DSA.stack.pop();
                System.out.printEdge(head.value + " ");
                head = head.right;
                while (head != null) {
                    DSA.stack.push(head);
                    head = head.left;
                }
            }
        }*/
        if (head != null) {
            Stack<Node> stack = new Stack<>();
           while (!stack.isEmpty() || head!=null){//栈非空或者还有需要压入栈的节点时需要走循环
               if (head!=null){
                   stack.push(head);
                   head=head.left;
               }else{
                   head = stack.pop();
                   System.out.print(head.value+" ");
                   head=head.right;
               }
           }
        }
    }

    /*
     * 递归实现后序遍历
     * */
    public static void posOrder(Node head) {
        if (head == null) {
            return;
        }
        posOrder(head.left);
        posOrder(head.right);
        System.out.print(head.value + " ");
    }

    /*
     * 非递归实现后序遍历
     * */
    public static void posOrderUnRecur(Node head) {
        if (head != null) {
            Stack<Node> stack = new Stack<>();
            stack.push(head);
            Stack<Node> temp = new Stack<>();
            while (!stack.isEmpty()) {
                head = stack.pop();
                temp.push(head);
                if (head.left != null) {
                    stack.push(head.left);
                }
                if (head.right != null) {
                    stack.push(head.right);
                }
            }
            while (!temp.isEmpty()) {
                System.out.print(temp.pop().value + " ");
            }
        }
    }

    /*
    * 宽度优先遍历,也就是层序遍历
    * */
    public static void wOrder(Node head){
        int max=Integer.MIN_VALUE;
        if (head!=null){
            Queue<Node> queue=new LinkedList<>();
            HashMap<Node,Integer> map=new HashMap<>();
            int curLevel=1;
            int curLevelNodes =0;
            map.put(head,1);
            queue.add(head);
            while (!queue.isEmpty()){
                Node cur=queue.poll();
                int curNodeLevel=map.get(cur);
                if (curNodeLevel==curLevel){
                    curLevelNodes++;
                }else{
                    curLevel++;
                    max=Math.max(max, curLevelNodes);
                    curLevelNodes =1;
                }
                System.out.print(cur.value+" ");
                if (cur.left!=null){
                    map.put(cur.left,curNodeLevel+1);
                    queue.add(cur.left);
                }
                if (cur.right!=null){
                    map.put(cur.right,curNodeLevel+1);
                    queue.add(cur.right);
                }
            }
            max=Math.max(max,curLevelNodes);//最后要再比较一次，否则不会考虑最后一层的宽度
        }
//        System.out.println(max);
    }
    public static void main(String[] args) {
        Node n4 = new Node(4, null, null);
        Node n5 = new Node(5, null, null);
        Node n6 = new Node(6, null, null);
        Node n7 = new Node(7, null, null);
        Node n2 = new Node(2, n4, n5);
        Node n3 = new Node(3, n6, n7);
        Node head = new Node(1, n2, n3);
        preOrder(head);// 1 2 4 5 3 6 7
        System.out.println("递归实现前序遍历");
        preOrderUnRecur(head);// 1 2 4 5 3 6 7
        System.out.println("非递归实现前序遍历");
        inOrder(head);// 4 2 5 1 6 3 7
        System.out.println("递归实现中序遍历");
        inOrderUnRecur(head);// 4 2 5 1 6 3 7
        System.out.println("非递归实现中序遍历");
        posOrder(head);// 4 5 2 6 7 3 1
        System.out.println("递归实现后序遍历");
        posOrderUnRecur(head);// 4 5 2 6 7 3 1
        System.out.println("非递归实现后序遍历");
        wOrder(head);// 1 2 3 4 5 6 7
        System.out.println("层序遍历");

    }
}

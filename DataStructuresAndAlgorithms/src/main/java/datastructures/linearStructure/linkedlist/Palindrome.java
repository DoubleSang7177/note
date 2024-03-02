package datastructures.linearStructure.linkedlist;

import java.util.Objects;
import java.util.Stack;

/*
 * 回文数
 * */
public class Palindrome {
    /*
    * 单向链表
    * */
    public static class Node {
        Integer value;
        Node next;

        public Node() {
        }

        public Node(int value, Node next) {
            this.value = value;
            this.next = next;
        }
    }

    /*
     * 入栈的方式判断是不是回文数
     * */
    public static boolean isPalindrome(Node node) {
        Node temp = node;
        if (node == null) {
            return false;
        }
        Stack<Integer> stack = new Stack<>();
        while (node != null) {
            stack.push(node.value);
            node = node.next;
        }
        while (!stack.isEmpty() && temp != null) {//其实遍历到中间位置就可以了！
            if (!Objects.equals(temp.value, stack.pop())) {
                return false;
            }
            temp = temp.next;
        }
        return true;
    }

    /*
     * 倒序链表存储到新链表判断是不是回文数
     * */
    public static boolean isPalindrome2(Node node) {
        Node reverse = reverse(node);
        while (node != null && reverse!=null) {
            if (!Objects.equals(node.value, reverse.value)){
                return false;
            }
            node=node.next;
            reverse=reverse.next;
        }
        return true;

    }

    /*
     * 反转链表
     * */
    public static Node reverse(Node node) {
        if (node == null) {
            return null;
        }
        Node newNode = new Node(node.value, null);
        node = node.next;
        while (node != null) {
            newNode = new Node(node.value, newNode);
            node = node.next;
        }
        return newNode;
    }

    /*
    * 利用快慢指针定位到中间位置
    * 压入栈
    * 依次出栈比较是不是回文数
    * */
    public static boolean isPalindrome3(Node node) {
        if (node==null){
            return false;
        }
        Node slow=node;
        Node quick=node;
        while (quick.next!=null && quick.next.next!=null){
            slow=slow.next;
            quick=quick.next.next;
        }
        slow=slow.next;
        Stack<Integer> stack = new Stack<>();
        while (slow!=null){
            stack.push(slow.value);
            slow=slow.next;
        }
        while (!stack.isEmpty()){
            if (!Objects.equals(stack.pop(), node.value)){
                return false;
            }
            node=node.next;
        }
        return true;
    }
    /*
    * 快慢指针
    * 先逆序慢指针右侧的链表，尾指向慢指针位置，慢指针指向null
    * 判断，从左右两边逐步判断
    * 恢复顺序
    * */
    public static boolean isPalindrome4(Node node) {
        if (node==null){
            return false;
        }
        Node n1=node;
        Node n2=node;
        while (n2.next!=null && n2.next.next!=null){
            n1=n1.next;
            n2=n2.next.next;
        }
        n2=n1.next;
        n1.next=null;
        Node n3=null;
        //逆序
        while (n2!=null){
            n3=n2.next;
            n2.next=n1;
            n1=n2;
            n2=n3;
        }
        n2=node;
        n3=n1;
        //比较
        while (n1!=null && n2!=null){
            if (n1.value!=n2.value){
                return false;
            }
            n1=n1.next;
            n2=n2.next;
        }
        //恢复排序
        n1=n3.next;
        n3.next=null;
        while (n1!=null){
            n2=n1.next;
            n1.next=n3;
            n3=n1;
            n1=n2;
        }
        return true;
    }
    public static void main(String[] args) {
        Node node = new Node(1, new Node(3, new Node(13, new Node(13, new Node(3, new Node(1,null))))));
        System.out.println("isPalindrome : "+isPalindrome(node));
        System.out.println("isPalindrome2 : "+isPalindrome2(node));
        System.out.println("isPalindrome3 : "+isPalindrome3(node));
        System.out.println("isPalindrome4 : "+isPalindrome4(node));
        Node reverse = reverse(node);
        while (reverse != null) {
            System.out.println(reverse.value);
            reverse = reverse.next;
        }
    }
}

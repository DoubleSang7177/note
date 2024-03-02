package datastructures.linearStructure.linkedlist;

import datastructures.basicKnowledge.Student;

import java.util.HashMap;
/*
* 复制具有rand属性的单向链表到新的内存地址
* */
public class CopyListWithRand {
    private static class Node{
        Integer value;
        Node next;
        Node rand;

        public Node(Integer value) {
            this.value = value;
        }
        public Node(Integer value, Node next, Node rand) {
            this.value = value;
            this.next = next;
            this.rand = rand;
        }

        public Node(Integer value, Node next) {
            this.value = value;
            this.next = next;
        }
    }
    /*
    * 使用HashMap
    * 时间复杂度O(N)
    * 额外空间复杂度O(1)
    * */
    public static Node copyListWithRand1(Node head){
        if (head==null){
            return null;
        }
        HashMap<Node,Node> map=new HashMap<>();
        Node cur=head;
        while (cur!=null){
            map.put(cur,new Node(cur.value));
            cur=cur.next;
        }
        cur=head;
        while (cur!=null){
            map.get(cur).next=map.get(cur.next);
            map.get(cur).rand=map.get(cur.rand);
            cur=cur.next;
        }
        return map.get(head);
    }
    /*
    *
    * */
    public static Node copyListWithRand2(Node head){
        if (head==null){
            return null;
        }
        Node cur=head;
        Node next=null;
        //第一步：复制每一个节点一次,即生成克隆节点
        while (cur!=null){
            next = cur.next;
            cur.next=new Node(cur.value);
            cur.next.next=next;
            cur=next;
        }
        //第二步：找出每一个复制出的新节点的rand
        cur=head;
        while (cur!=null){
            next=cur.next.next;
            cur.next.rand=cur.rand!=null?cur.rand.next:null;
            cur=next;
        }
        Node res=head.next;
        cur=head;
        Node copy=null;
        while (cur!=null){
            next=cur.next.next;
            copy=cur.next;
            cur.next=next;
            copy.next=next!=null?next.next:null;
            cur=next;
        }
        return res;
    }
    public static void main(String[] args) {
        Node node5=new Node(13,null);
        Node node4 = new Node(12, node5);
        Node node3 = new Node(102, node4);
        Node node2 = new Node(111, node3);
        Node node1 = new Node(18, node2);
        Node node = new Node(222, node1);
        node.rand=node4;
        node1.rand=node5;
        node2.rand=node3;
        Node copy = copyListWithRand1(node);
        while (copy!=null){
            System.out.print(copy.value+" ");
            if (copy.next !=null){
                System.out.print(copy.next.value+" ");
            }
            if (copy.rand!=null){
                System.out.print(copy.rand.value);
            }
            copy=copy.next;
            System.out.println();
        }
        System.out.println("=========");
        Node copy2 = copyListWithRand2(node);
        while (copy2!=null){
            System.out.print(copy2.value+" ");
            if (copy2.next !=null){
                System.out.print(copy2.next.value+" ");
            }
            if (copy2.rand!=null){
                System.out.print(copy2.rand.value);
            }
            copy2=copy2.next;
            System.out.println();
        }
        /*
        * 引用啊引用!卧槽！！！
        * */
        Student s1=new Student();
        s1.setName("张三");
        Student s2=s1;
        s2.setName("李四");
        System.out.println(s1.getName()+" "+s2.getName());//李四 李四
        /*
        *new出一个新对象了
        * */
        int x=10;
        int y=x;
        y=20;
        System.out.println(x+" "+y);//10 20
    }

}

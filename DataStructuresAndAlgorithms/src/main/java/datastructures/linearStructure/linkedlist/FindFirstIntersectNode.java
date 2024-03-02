package datastructures.linearStructure.linkedlist;
/*
* 非常经典的题目！！！
* 给定两个有环也有可能无环的单链表，头节点head1和head2，请实现一个函数，
* 如果两个链表相交，请返回第一个相交的节点，如果不相交，返回null
* 要求：如果两个链表长度和为N，时间复杂度请达到0(N),额外的空间复杂度请达到O(1)
* */
public class FindFirstIntersectNode {
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
    * 找到两个链表的第一个相交节点
    * */
    public static Node findFirstIntersectNode(Node head1,Node head2){
        if (head1==null || head2==null){
            return null;
        }
        //先找到两个链表的入环节点，以此判断是否有环
        Node loop1 = getLoopNode(head1);
        Node loop2 = getLoopNode(head2);
        //第一种可能：两个都无环
        if (loop1==null && loop2==null){
            return noLoop(head1,head2);
        }
        //第二种可能：两个都有环
        if (loop1!=null && loop2!=null) {
            bothLoop(head1,loop1,head2,loop2);
        }
        //第三种可能：一个有环，一个无环，这种情况下不可能存在相交节点
        return null;
    }
    /*
    * 找到第一个入环节点，如果无环则返回null
    * */
    public static Node getLoopNode(Node head){
        //空链表或者链表节点不足3的情况不可能有环
        if (head==null||head.next==null||head.next.next==null){
            return null;
        }
        //快慢指针
        Node n1=head.next;
        Node n2=head.next.next;
        //判断快慢指针能不能相遇，如果相遇则证明存在环
        while (n1!=n2){
            if (n2.next==null||n2.next.next==null){
                return null;
            }
            n1=n1.next;
            n2=n2.next.next;
        }
        //相遇后，快指针返回到头节点位置，两个指针同时移动，步长均为1，再次相遇的地方就是第一个入环节点
        n2=head;
        while (n1!=n2){
            n1=n1.next;
            n2=n2.next;
        }
        return n1;
    }
    /*
    * 如果两个链表都无环，返回第一个相交节点，如果不相交则返回null
    * */
    public static Node noLoop(Node head1,Node head2){
        if (head1==null || head2==null){
            return null;
        }
        Node n1 =head1;
        int n =0;
        while (n1.next !=null){
            n1 = n1.next;
            n++;
        }
        Node n2 =head2;
        while (n2.next !=null){
            n2 = n2.next;
            n--;
        }
        if (n1!=n2){
            return null;
        }
        //规定长的为n1
        n1=n>0?head1:head2;
        n2=n1==head1?head2:head1;
        n=Math.abs(n);
        while (n!=0){
            n1=n1.next;
            n--;
        }
        while (n1!=n2){
            n1=n1.next;
            n2=n2.next;
        }
        return n1;
    }
    /*
    * 两个有环链表返回第一个相交节点，如果不相交返回null
    * */
    public static Node bothLoop(Node head1,Node loop1,Node head2,Node loop2){
        //两个都有环，那么可能有相同的环，有相同的环时，入环节点可能相同也可能不相同，分两种情况讨论
        //具有两个不同环时不可能相交
        Node cur1=null;
        Node cur2=null;
        if (loop1==loop2){
            //第一种情况，两个链表拥有相同的入环节点，那么第一个相交的节点在入环节点的前面部分
            //以入环节点为终止节点，按照两个无环链表的思路寻找第一个相交节点
            cur1=head1;
            cur2=head2;
            int n =0;
            while (cur1!=loop1){
                cur1=cur1.next;
                n++;
            }
            while (cur2!=loop2){
                cur2=cur2.next;
                n--;
            }
            cur1=n>0?head1:head2;
            cur2=cur1==head1?head2:head1;
            n=Math.abs(n);
            while (n!=0){
                cur1=cur1.next;
                n--;
            }
            while (cur1!=cur2){
                cur1=cur1.next;
                cur2=cur2.next;
            }
            return cur1;
        }else{
            //两个链表有不同的入环节点
            cur1=loop1.next;
            while (loop1!=cur1){
                if (cur1==loop2){
                    //两个链表有不同的入环节点，但是具有相同的环
                    return loop1;
                }
                cur1=cur1.next;
            }
            //两个链表有不同的环，入环节点肯定也不相同
            return null;
        }
    }


    public static void main(String[] args) {
        Node end = new Node(0, null);
        Node n1 = new Node(1, end);
        Node n2 = new Node(2, n1);
        Node n3 = new Node(3, n2);
        Node n4 = new Node(4, n3);
        Node n5 = new Node(5, n4);
        Node n6 = new Node(6, n5);
        Node n61 = new Node(61, n6);
        Node n62 = new Node(62, n6);
        Node head1 = new Node(71, n61);
        Node n72 = new Node(72, n62);
        Node n82 = new Node(82, n72);
        Node n92= new Node(92, n82);
        Node head2= new Node(102, n92);
        end.next=n6;
        Node node = bothLoop(head1,n6, head2,n6);
        System.out.println(node.value);
    }
}

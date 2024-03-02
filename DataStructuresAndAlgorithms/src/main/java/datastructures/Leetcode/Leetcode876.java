package datastructures.Leetcode;
/*
* 查找链表的中间节点
* */
public class Leetcode876 {
    public static ListNode middleNode(ListNode head){
        ListNode p1=head;
        ListNode p2=head;
        while (p2!=null && p2.next!=null){
            p1=p1.next;
            p2=p2.next;
            p2=p2.next;
        }
        return p1;
    }
}

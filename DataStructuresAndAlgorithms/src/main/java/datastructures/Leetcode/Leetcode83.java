package datastructures.Leetcode;

public class Leetcode83 {

    //Definition for singly-linked list.
    public class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    public  ListNode deleteDuplicates(ListNode head) {
        if(head==null || head.next==null){
            return head;
        }
        ListNode p1 = head;
        ListNode p2;
        while ((p2=p1.next) != null) {
            if (p1.val == p2.val) {
                p1.next = p2.next;
            } else {
                p1 = p1.next;
            }
        }
        return head;
    }


}

package datastructures.Leetcode;

public class Leetcode82 {
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

    public ListNode deleteDuplicates(ListNode head) {
        if(head==null || head.next==null){
            return head;
        }
        if(head.next.val == head.val){
            ListNode x=head.next.next;
            while (x!=null && x.val==head.val){
                x=x.next;
            }
            return deleteDuplicates(x);
        }else{
            head.next=deleteDuplicates(head.next);
            return head;
        }
    }
}

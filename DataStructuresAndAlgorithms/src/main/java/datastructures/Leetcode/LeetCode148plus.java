package datastructures.Leetcode;

public class LeetCode148plus {
    public  ListNode sortList(ListNode head) {
        return sortList(head, null);
    }
    public  class ListNode {
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

    public ListNode sortList(ListNode head, ListNode tail) {
        if (head==null){//节点为空
            return null;
        }
        if (head.next==tail){//只有一个节点
            head.next=null;
            return head;
        }
        ListNode slow=head;
        ListNode fast=head;
        while (fast!=tail){
            fast=fast.next;
            slow=slow.next;
            if (fast!=tail){
                fast=fast.next;
            }
        }
        ListNode head1 = sortList(head, slow);
        ListNode head2 = sortList(slow, tail);
        return merge(head1,head2);
    }

    public ListNode merge(ListNode head1, ListNode head2) {
        ListNode resHead=new ListNode(0);
        ListNode temp=resHead,temp1=head1,temp2=head2;
        while (temp1!=null && temp2!=null){
            if (temp1.val< temp2.val){
                temp.next=temp1;
                temp1=temp1.next;
            }else{
                temp.next=temp2;
                temp2=temp2.next;
            }
            temp=temp.next;
        }
        if (temp1!=null){
            temp.next=temp1;
        }else if (temp2!=null){
            temp.next=temp2;
        }
        return resHead.next;
    }
}

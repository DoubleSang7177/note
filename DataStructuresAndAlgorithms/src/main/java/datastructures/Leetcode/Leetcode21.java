package datastructures.Leetcode;

public class Leetcode21 {
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
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        ListNode s=new ListNode(-1,null);
        ListNode n=s;
        while (list1!=null && list2!=null){
            if(list1.val<list2.val){
                n.next=list1;
                list1=list1.next;
            }else{
                n.next=list2;
                list2=list2.next;
            }
            n=n.next;
        }
        if(list1!=null){
            n.next=list1;
        }
        if(list2!=null){
            n.next=list2;
        }
        return s.next;

    }
}

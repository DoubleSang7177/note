package datastructures.Leetcode;

/*
* 判断链表是否带环
* */
public class Leetcode141 {

    public static boolean hasCycle(ListNode head) {
        ListNode p=head;
        ListNode t=head;
        while (p!=null && p.next!=null){
            p=p.next.next;
            t=t.next;
            if(p==t){
                return true;
            }
        }
        return false;
    }



    public static void main(String[] args){
        ListNode n12=new ListNode(12,null);
        ListNode n11=new ListNode(11,n12);
        ListNode n10=new ListNode(10,n11);
        ListNode n9=new ListNode(9,n10);
        ListNode n8=new ListNode(8,n9);
        ListNode n7=new ListNode(7,n8);
        ListNode n6=new ListNode(6,n7);
        ListNode n5=new ListNode(5,n6);
        ListNode n4=new ListNode(4,n5);
        ListNode n3=new ListNode(3,n4);
        ListNode n2=new ListNode(2,n3);
        ListNode n1=new ListNode(1,n2);
        n12.next=n8;
        System.out.println(Leetcode141.hasCycle(n1));
    }
}

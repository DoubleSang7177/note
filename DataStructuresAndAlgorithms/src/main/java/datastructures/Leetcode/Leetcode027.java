package datastructures.Leetcode;

/*
* 判断回文链表
* */
public class Leetcode027 {
    /*public  boolean isPalindrome(ListNode head) {
        ListNode middleListNode = findMiddleListNode(head);
        ListNode.printEdge(middleListNode);
        ListNode reversedListNode=reverse(middleListNode);
        ListNode.printEdge(reversedListNode);
        while (reversedListNode!=null){
            if(head.val!=reversedListNode.val){
                return false;
            }
            head=head.next;
            reversedListNode=reversedListNode.next;
        }
        return true;
    }

    public ListNode reverse(ListNode o1){
        ListNode n1=null;
        while (o1!=null){
            ListNode o2=o1.next;
            o1.next=n1;
            n1=o1;
            o1=o2;
        }
        return n1;
    }


    public ListNode findMiddleListNode(ListNode head){
        ListNode p1=head;
        ListNode p2=head;
        while (p2!=null && p2.next!=null){
            p1=p1.next;
            p2=p2.next;
            p2=p2.next;
        }
        return p1;
    }*/


    public  boolean isPalindrome(ListNode head) {
        ListNode p1= head;
        ListNode p2= head;
        ListNode o1=head;
        ListNode n1=null;
        while (p2!=null && p2.next!=null){
            p1=p1.next;
            p2=p2.next;
            p2=p2.next;
            // 反转链表
            o1.next=n1;
            n1=o1;
            o1=p1;
        }

        ListNode.print(p1);
        ListNode.print(n1);
        if(p2!=null) {//奇数节点
            p1=p1.next;
        }
        while (n1!=null){
            if(p1.val!=n1.val){
                return false;
            }
            p1=p1.next;
            n1=n1.next;
        }

        return true;
    }


    public static void main(String[] args){
        int[] list={1,2,3,2,1};
        ListNode head = ListNode.of(list);
        Leetcode027 leetcode234 = new Leetcode027();
        System.out.println(leetcode234.isPalindrome(head));
    }
}

package datastructures.Leetcode;

import org.junit.Test;

public class LeetcodeTests {
    @Test
    public void Leetcode876(){
        ListNode head=new ListNode(1,new ListNode(2,new ListNode(3,new ListNode(4,new ListNode(5,null)))));
        ListNode middle=Leetcode876.middleNode(head);
        ListNode.print(middle);

        ListNode head2=new ListNode(1,new ListNode(2,new ListNode(3,new ListNode(4,new ListNode(5,new ListNode(6,null))))));
        ListNode middle2=Leetcode876.middleNode(head2);
        ListNode.print(middle2);
        int[] list={-1,3,4,5,6,8,9};
        ListNode of = ListNode.of(list);
        ListNode.print(of);
        ListNode.print(Leetcode876.middleNode(of));


    }

}

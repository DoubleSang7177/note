package datastructures.Leetcode;

public class Leetcode23 {
    public ListNode mergeKList(ListNode[] lists){
        if(lists.length==0){
            return null;
        }
        return split(lists,0,lists.length-1);
    }
    public ListNode split(ListNode[] list,int i,int j){
        if(i==j){
            return list[i];
        }
        int m=(i+j)>>>1;
        ListNode left = split(list, i, m);
        ListNode right = split(list, m+1, j);
        return mergeTwoLists(left,right);
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

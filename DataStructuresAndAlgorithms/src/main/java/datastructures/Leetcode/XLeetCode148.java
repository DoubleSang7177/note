package datastructures.Leetcode;
/*
* 有错去，需要修改！！
* */
public class XLeetCode148 {
    public static class ListNode {
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

    public static ListNode sortList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode tail = head;//浅拷贝
        while (tail.next != null) {
            tail = tail.next;
        }
        return process(head,tail, head);
    }

    public static ListNode process(ListNode leftNode, ListNode rightNode, ListNode head) {
        if (leftNode == rightNode){
            return leftNode;
        }
//        ListNode mid = leftNode;
        ListNode quick=leftNode;
        ListNode slow=leftNode;
        while (quick.next!=null && quick.next.next!=rightNode.next){//使用快慢指针定位中间节点
            quick=quick.next.next;
            slow=slow.next;
        }
//        int subSize=0;
        /*
        if (size % 2 == 0) {
            for (int i = 1; i < size / 2; i++) {//偶数
                mid = mid.next;
                subSize++;
            }
        } else {
            for (int i = 0; i < size / 2; i++) {//奇数
                mid = mid.next;
                subSize++;
            }
            System.out.println(mid.val +" vs "+ slow.val);
        }*/
        process(leftNode, slow, head);
        process(slow.next, rightNode, head);
        return merge(leftNode,rightNode,head,slow);
    }

    private static ListNode merge(ListNode left, ListNode right, ListNode head, ListNode mid) {
        ListNode cur=head;
        while (cur!=left){
            cur=cur.next;
        }
        ListNode p1=left;
        ListNode p2=mid.next;
        while (p1!=p2 && p2!=right.next){
            if (p1.val< p2.val){
//                cur.val=p1.val;
                cur=cur.next;
                p1=p1.next;
            }else{
                int tmp=cur.val;
                cur.val=p2.val;
                ListNode t=cur;
                while (t!=p2){
                    int x=t.next.val;
                    t.next.val=tmp;
                    tmp=x;
                    t=t.next;
                }
                cur=cur.next;
                p1=p1.next;
                p2=p2.next;
            }
        }
        while (p1!=p2){
            cur.val= p1.val;
            cur=cur.next;
            p1=p1.next;
        }
        while (p2!=right.next){
            cur.val=p2.val;
            cur=cur.next;
            p2=p2.next;
        }
        return head;
    }

    public static void main(String[] args) {
        ListNode head = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5, new ListNode(6,new ListNode(7)))))));
        ListNode tail = head;
        ListNode mid = head;
        int size = 0;
        while (tail.next != null) {
            tail = tail.next;
            size++;
        }
        int subSize=0;
        if ((size + 1) % 2 == 0) {
            for (int i = 1; i < (size + 1) / 2; i++) {//偶数
                mid = mid.next;
                subSize++;
            }
        } else {
            for (int i = 0; i < (size + 1) / 2; i++) {//奇数
                mid = mid.next;
                subSize++;
            }
        }
        System.out.println(head.val + " " + tail.val + " " + mid.val +" " +(subSize+1));


        ListNode cur=head;
        System.out.println(cur+",,");
        for (int i = 7; i < 10; i++) {
            cur.val=i;
            cur=cur.next;
        }
        System.out.println(cur+"  "+head.next.val);
        ListNode sortList = sortList(head);
        while (sortList!=null && head!=null){
            System.out.println(sortList.val+ " "+ head.val);
            sortList=sortList.next;
            head=head.next;
        }
    }

}
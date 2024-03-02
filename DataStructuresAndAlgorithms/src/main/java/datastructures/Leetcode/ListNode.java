package datastructures.Leetcode;

public class ListNode {
    public int val;
    public ListNode next;

    ListNode() {
    }

    ListNode(int val) {
        this.val = val;
    }

    public ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }

    public static void print(ListNode listNode){
        System.out.print("[");
        while (listNode!=null && listNode.next!=null){
            System.out.print(listNode.val+",");
            listNode=listNode.next;
        }
        if(listNode!=null){
            System.out.print(listNode.val);
        }
        System.out.print("]");
        System.out.println();
    }
    public ListNode addLast(int value,ListNode head) {
        ListNode last = findLast(head);
        if (last == null) {
            //addFirst(value);
            head = new ListNode(value, null);
        } else {
            last.next = new ListNode(value, null);
        }
        return head;
    }
    public ListNode findLast(ListNode head) {
        ListNode last = null;
        for (ListNode p = head; p != null; p = p.next) {
            last = p.next == null ? p : null;
        }
        return last;//null 或者 最后一个有值的节点
    }
    public static ListNode of(int[] elements){
        if(elements.length==0){
            return null;
        }
        ListNode head=new ListNode(elements[0],null);
        for (int i=1;i<elements.length;i++) {
            head = head.addLast(elements[i], head);
        }
        return head;
    }
    public ListNode addFirst(int value, ListNode head) {
       /*
       1.链表为空
        head=new TrieNode(value,null);
       2.链表不为空
        head=new TrieNode(value,head);
       */
        //合并
        head = new ListNode(value, head);
        return head;
    }
   /* public ListNode removeLast(ListNode head){
        ListNode last = null;
        for (ListNode p = head; p != null; p = p.next) {
            last = p.next == null ? p : null;
        }

        return last;//null 或者 最后一个有值的节点

    }*/
}

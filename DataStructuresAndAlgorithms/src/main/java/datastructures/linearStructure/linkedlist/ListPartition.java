package datastructures.linearStructure.linkedlist;
/*
* 将单链表按照左边小于某值，中间等于某值，右边大于某值的形式进行排序
* */
public class ListPartition {
    public static class Node {
        Integer value;
        Node next;

        public Node() {
        }

        public Node(int value, Node next) {
            this.value = value;
            this.next = next;
        }
    }
    public static Node listPartition(Node head,int pivot){
        Node sH=null;
        Node sT=null;
        Node eH=null;
        Node eT=null;
        Node mH=null;
        Node mT=null;
        Node next=null;
        while (head!=null){
            next=head.next;
            head.next=null;
            if (head.value<pivot){
                if (sH==null && sT==null){
                    sT=head;
                    sH=head;
                }else{
                    sT.next=head;
                    sT=head;
                }
            }else if(head.value==pivot){
                if (eH==null && eT==null){
                    eT=head;
                    eH=head;
                }else{
                    eT.next=head;
                    eT=head;
                }
            }else{
                if (mH==null && mT==null){
                    mT=head;
                    mH=head;
                }else{
                    mT.next=head;
                    mT=head;
                }
            }
            head=next;
        }

        if (sT!=null){
            sT.next=eH;
            eT=eT==null?sT:eT;
        }

        if (eT!=null){
            eT.next=mH;
        }

        return sH!=null?sH:(eH!=null?eH:mH);
    }

    public static void main(String[] args) {
        Node node = new Node(1, new Node(3, new Node(13, new Node(13, new Node(3, new Node(1,null))))));
        Node node1 = listPartition(node, 3);
        while (node1!=null){
            System.out.print(node1.value+" ");
            node1 = node1.next;
        }
    }
}

package datastructures.linearStructure.linkedlist;

import java.util.Iterator;

/*
 * 双向环形链表（带哨兵）
 * */
public class DoublyLinkedListSentinelLoop implements Iterable<Integer> {
    @Override
    public Iterator<Integer> iterator() {
        return new Iterator<Integer>() {
            Node p=sentinel.next;
            @Override
            public boolean hasNext() {
                return p!=sentinel;
            }

            @Override
            public Integer next() {
                int value=p.value;
                p=p.next;
                return value;
            }
        };
    }

    static class Node {
        Node prev;
        Node next;
        int value;

        Node(Node prev, int value, Node next) {
            this.prev = prev;
            this.value = value;
            this.next = next;

        }
    }

    private Node sentinel = new Node(null, -1, null);
    public DoublyLinkedListSentinelLoop(){
        sentinel.next=sentinel;
        sentinel.prev=sentinel;
    }
    public void addFirst(int value){
        Node next=sentinel.next;
        Node added=new Node(sentinel,value,next);
        sentinel.next=added;
        next.prev=added;
    }
    public void addLast(int value){
        Node last=sentinel.prev;
        Node added=new Node(last,value,sentinel);
        last.next=added;
        sentinel.prev=added;
    }
    public void removeFirst(){
        Node removed=sentinel.next;
        if(removed==sentinel){
            throw new IllegalArgumentException("非法！");
        }
        Node next=removed.next;
        next.prev=sentinel;
        sentinel.next=next;
    }
    public void removeLast(){
        Node last=sentinel.prev;
        if(last==sentinel){
            throw new IllegalArgumentException("非法！");
        }
        Node pre=last.prev;
        sentinel.prev=pre;
        pre.next=sentinel;
    }
    /*
    * 如果有多个相同值的删除第一个
    * */
    public void removeByValue(int value){
        Node node = findByValue(value);
        if(node==null){
            throw new IllegalArgumentException(String.format("%d不存在",value));
        }
        Node prev=node.prev;
        Node next=node.next;
        prev.next=next;
        next.prev=prev;

    }
    private Node findByValue(int value){
        for(Node p=sentinel.next;p!=sentinel;p=p.next){
            if(p.value==value){
               return p;
            }
        }
        return null;
    }


}
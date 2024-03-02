package datastructures.linearStructure.linkedlist;

import java.util.Iterator;
/*
* 双向链表（带哨兵）
* */
public class DoublyLinkedListSentinel implements Iterable<Integer> {
    private Node head;
    private Node tail;

    @Override
    public Iterator<Integer> iterator() {
        return new Iterator<Integer>() {
            Node last = head.next;

            @Override
            public boolean hasNext() {
                return last.next != null;
            }

            @Override
            public Integer next() {
                Integer result = last.value;
                last = last.next;
                return result;
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

    public DoublyLinkedListSentinel() {
        head = new Node(null, 666, null);
        tail = new Node(null, 999, null);
        head.next = tail;
        tail.prev = head;
    }

    /*
     * 根据索引寻找节点
     * */
    private Node findNode(int index) {
        int i = -1;
        for (Node p = head; p != tail; p = p.next, i++) {
            if (i == index) {
                return p;
            }
        }
        return null;
    }

    public int get(int index) {
        Node node = findNode(index);
        if (node == null || index==-1) {
            throw new IllegalArgumentException(String.format("index[%d]不合法", index));
        }
        return node.value;
    }

    public void insert(int index, int value) {
        Node prev = findNode(index - 1);
        if (prev == null) {
            throw new IllegalArgumentException(String.format("index[%d]不合法", index));
        }
        Node next = prev.next;
        Node inserted = new Node(prev, value, next);
        prev.next = inserted;
        next.prev = inserted;
    }
    public void remove(int index){
        Node prev=findNode(index-1);
        Node deleted=findNode(index);
        if(prev==null||deleted==null){
            throw new IllegalArgumentException(String.format("index[%d]不合法", index));
        }
        Node next=deleted.next;
        prev.next=next;
        next.prev=prev;
    }

    public void addFirst(int value) {
        insert(0,value);
    }

    public void removeFirst() {
      remove(0);
    }

    public void addLast(int value) {
        Node last=tail.prev;
        Node newLast=new Node(last,value,tail);
        last.next=newLast;
        tail.prev=newLast;
    }

    public void removeLast(){
        Node last = tail.prev;
        if(last==head){
            throw new IllegalArgumentException(String.format("链表为空"));
        }
        Node prev = last.prev;
        prev.next=tail;
        tail.prev=prev;
    }
}
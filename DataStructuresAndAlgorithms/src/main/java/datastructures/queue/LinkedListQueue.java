package datastructures.queue;

import java.util.Iterator;
/*
* 队列
* */
public class LinkedListQueue<E> implements Queue<E>, Iterable<E> {
    private static class Node<E> {
        Node<E> next;
        E value;
        public Node(E value, Node<E> next) {
            this.next = next;
            this.value = value;
        }
    }
    private Node<E> head=new Node<>(null,null);//刚开始没办法指向head自己
    private Node<E> tail=head;
    private int size;//节点数
    private int capacity=Integer.MAX_VALUE;//容量

    {
        tail.next=head;
    }
    public LinkedListQueue() {
    }

    public LinkedListQueue(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public boolean offer(E value) {
        if(isFull()){
            return false;
        }
        Node<E> added = new Node<>(value, head);
        tail.next=added;
        tail=added;
        size++;
        return true;
    }

    @Override
    public E poll() {
        if(isEmpty()){
            return null;
        }
        E value=head.next.value;
        if(head.next==tail){
            tail=head;
        }
        head.next=head.next.next;
        size--;
        return value;
    }

    //获取头部值，不移除
    @Override
    public E peek() {
        if(isEmpty()){
            return null;
        }
        return head.next.value;
    }

    @Override
    public boolean isEmpty() {
        return head==tail;
    }

    @Override
    public boolean isFull() {
        return size==capacity;
    }

    //迭代器
    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            Node<E> p=head.next;
            @Override
            public boolean hasNext() {
                return p!=head;
            }

            @Override
            public E next() {
                E value=p.value;
                p=p.next;
                return value;
            }
        };
    }


    public static void main(String[] args){
        LinkedListQueue<Integer> queue = new LinkedListQueue<>();
        System.out.println(queue.isEmpty());
        System.out.println(queue.peek());
        queue.offer(8);
        queue.offer(2);
        queue.offer(3);
        queue.offer(4);
        queue.offer(5);
        for (Integer integer : queue) {
            System.out.print(integer+" ");
        }
        System.out.println();
        System.out.println(queue.isEmpty());
        System.out.println(queue.peek());
        for (Integer integer : queue) {
            System.out.print(integer+" ");
        }
        System.out.println();
        System.out.println(queue.poll());
        for (Integer integer : queue) {
            System.out.print(integer+" ");
        }

    }
}

package datastructures.linearStructure.stack;

import java.util.Iterator;

public class LinkedListStack<E> implements Stack<E>,Iterable<E> {
    private int capacity;
    private int size;
    private Node<E> head=new Node<>(null,null);
    private static class Node<E> {
        Node<E> next;
        E value;
        public Node(E value,Node<E> next) {
            this.next = next;
            this.value = value;
        }
    }

    public LinkedListStack(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public boolean push(E value) {
        if (isFull()){
            return false;
        }
        head.next = new Node<>(value, head.next);
        size++;
        return true;
    }

    @Override
    public E pop() {
        if (isEmpty()){
            return null;
        }
        Node<E> first = head.next;
        head.next=first.next;
        size--;
        return first.value;
    }

    @Override
    public E peek() {
        if (isEmpty()){
            return null;
        }
        return head.next.value;
    }

    @Override
    public boolean isEmpty() {
        return size==0;
    }

    @Override
    public boolean isFull() {
        return size==capacity;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            Node<E> p=head.next;
            @Override
            public boolean hasNext() {
                return p!=null;
            }

            @Override
            public E next() {
                E value = p.value;
                p=p.next;
                return value;
            }
        };
    }

    public static void main(String[] args){
        LinkedListStack<Integer> stack=new LinkedListStack<>(3);
        stack.push(1);
        stack.push(2);
        stack.push(3);
        System.out.println(stack.push(4));
        for (Integer integer : stack) {
            System.out.println(integer);
        }
        System.out.println(stack.peek());
        stack.pop();
        System.out.println("===");
        for (Integer integer : stack) {
            System.out.println(integer);
        }
    }
}

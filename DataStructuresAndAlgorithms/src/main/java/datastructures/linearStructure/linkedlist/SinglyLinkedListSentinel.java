package datastructures.linearStructure.linkedlist;

import java.util.Iterator;
import java.util.function.Consumer;

/*
 * 单向链表（带哨兵节点）
 * 小tips：当内部类和外部的成员变量没有什么关系的时候，加static，反之不加static
 * */
public class SinglyLinkedListSentinel implements Iterable<Integer> {
    private Node head = new Node(666,null);//头指针

    /*
     * 节点类
     * */
    private static class Node {
        int value;
        Node next;

        public Node(int value, Node next) {
            this.next = next;
            this.value = value;
        }
    }

    /*
     * 向节点头部添加
     * */
    public void addFirst(int value) {
        insert(0,value);
    }

    /*
     * 遍历链表方法1
     * */
    public void loop1(Consumer<Integer> consumer) {
        Node pointer = head.next;
        while (pointer != null) {
            //System.out.println(pointer.value);
            consumer.accept(pointer.value);
            pointer = pointer.next;
        }
    }

    /*
     * 遍历链表方法2
     * */
    public void loop2(Consumer<Integer> consumer) {
        for (Node pointer = head.next; pointer != null; pointer = pointer.next) {
            consumer.accept(pointer.value);
        }
    }

    /*
     * 遍历链表方法3
     * */
    /*
     * 寻找最后一个元素
     * */
    public Node findLast() {
        Node last=null;
        for (Node p = head; p != null; p = p.next) {
            last=p;
        }
        return last;//最后一个有值的节点(包括哨兵节点)
    }

    @Override
    public Iterator<Integer> iterator() {
        return new Iterator<Integer>() {
            Node pointer = head.next;

            @Override
            public boolean hasNext() {
                return pointer != null;
            }

            @Override
            public Integer next() { //注意：一定是先获取value再给pointer赋值
                int value = pointer.value;
                pointer = pointer.next;
                return value;
            }
        };
    }

    /*
     * 在尾部添加元素
     * */
    public void addLast(int value) {
        Node last = findLast();
        last.next = new Node(value, null);
    }

    /*
     * 查找指定索引的节点
     * */
    private Node findNode(int index) {
        int i = -1;
        for (Node p = head; p != null; p = p.next, i++) {
            if (i == index) {
                return p;
            }
        }
        return null; //没找到 null有两种情况1.链表为空 2.索引越界
    }

    public int get(int index) {
        Node node = findNode(index);
        if (node == null) {
            throw new IllegalArgumentException(
                    String.format("index[%d]不合法%n", index)
            );
        }
        return node.value;
    }

    /*
     * 向索引位置插入
     * */
    public void insert(int index, int value) {
        Node node = findNode(index);
        //链表为空的时候放在最后一个节点上
        //索引越界的时候抛出异常
        if (node == null) {
            throw new IllegalArgumentException(
                    String.format("index[%d]不合法%n", index)
            );
        } else {
            Node left = findNode(index - 1);
            left.next = new Node(value, left.next);
        }

    }
    /*
    * 删除第一个节点
    * */
    public void removeFirst() {
        remove(0);
    }
    /*
    * 删除指定位置的节点
    * */
    public void remove(int index){
        Node node = findNode(index);
        //链表为空的时候放在最后一个节点上
        //索引越界的时候抛出异常
        if (node == null) {
            throw new IllegalArgumentException(
                    String.format("index[%d]不合法！",index)
            );
        } else {
            Node left = findNode(index - 1);
            left.next = node.next;
        }

    }
}

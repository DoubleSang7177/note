package datastructures.linearStructure.linkedlist;

import org.junit.Test;

public class LinkedListTest {
    @Test
    public void addFirstTest(){
        SinglyLinkedList singlyLinkedList=new SinglyLinkedList();
        singlyLinkedList.addFirst(2);
    }
    @Test
    public void loopTest(){
        SinglyLinkedList singlyLinkedList=new SinglyLinkedList();
        //System.out.println(singlyLinkedList.findLast());
        singlyLinkedList.addFirst(5);
        singlyLinkedList.addFirst(4);
        singlyLinkedList.addFirst(3);
        singlyLinkedList.addFirst(2);
        singlyLinkedList.addFirst(1);
        System.out.println("第一种遍历方式");
        singlyLinkedList.loop1(System.out::println);
        System.out.println("第二种遍历方式");
        singlyLinkedList.loop2(System.out::println);
        //System.out.println(singlyLinkedList.findLast());
        singlyLinkedList.addLast(13);
        singlyLinkedList.addLast(14);
        System.out.println("第三种遍历方式");
        singlyLinkedList.forEach(System.out::println);

    }
    @Test
    public void findLastTest(){
        SinglyLinkedList singlyLinkedList=new SinglyLinkedList();
        System.out.println(singlyLinkedList.findLast());
        singlyLinkedList.addFirst(52);
        singlyLinkedList.addFirst(21);
        System.out.println(singlyLinkedList.findLast());
    }
    @Test
    public void addLastTest(){
        SinglyLinkedList singlyLinkedList=new SinglyLinkedList();
        singlyLinkedList.addLast(13);
        singlyLinkedList.addLast(14);
        singlyLinkedList.addFirst(3);
        System.out.println("第三种遍历方式");
        singlyLinkedList.forEach(System.out::println);
    }
    @Test
    public void findNodeTest(){
        SinglyLinkedList singlyLinkedList=new SinglyLinkedList();
        //singlyLinkedList.get(0);
        singlyLinkedList.addLast(13);
        singlyLinkedList.addLast(14);
        singlyLinkedList.addLast(15);
        singlyLinkedList.addLast(16);
        System.out.println(singlyLinkedList.get(2));
        /*
        * 抛出异常
        * */
        //System.out.println(singlyLinkedList.get(6));

    }
    @Test
    public void insertTest(){
        SinglyLinkedList singlyLinkedList=new SinglyLinkedList();
        singlyLinkedList.addLast(1);
        singlyLinkedList.addLast(2);
        singlyLinkedList.addLast(3);
        singlyLinkedList.addLast(4);
        singlyLinkedList.insert(3,13);
        singlyLinkedList.forEach(System.out::println);
        //System.out.println(singlyLinkedList.get(1));
    }
    @Test
    public void removeFirstTest(){
        SinglyLinkedList singlyLinkedList=new SinglyLinkedList();
        //singlyLinkedList.removeFirst();
        singlyLinkedList.addLast(1);
        singlyLinkedList.addLast(2);
        singlyLinkedList.addLast(3);
        singlyLinkedList.addLast(4);
        singlyLinkedList.removeFirst();
        for (Integer integer : singlyLinkedList) {
            System.out.println(integer);
        }
    }
    @Test
    public void deleteTest(){
        SinglyLinkedList singlyLinkedList=new SinglyLinkedList();
       // singlyLinkedList.erase(0);
        singlyLinkedList.addLast(1);
        singlyLinkedList.addLast(2);
        singlyLinkedList.addLast(3);
        singlyLinkedList.addLast(4);
        singlyLinkedList.delete(2);
        for (Integer integer : singlyLinkedList) {
            System.out.println(integer);
        }
    }

    @Test
    public void loop3(){
        SinglyLinkedList list=new SinglyLinkedList();
        list.addLast(1);
        list.addLast(2);
        list.addLast(3);
        list.addLast(4);
        list.addLast(5);
        list.loop3(value->{
            System.out.print(value+" ");
        },value->{
            System.out.print(value+" ");
        });
    }

}

package datastructures.linearStructure.linkedlist;

import org.junit.Test;

public class DoublyLinkedListSentinelLoopTest {
    @Test
    public void test() {
        DoublyLinkedListSentinelLoop list=new DoublyLinkedListSentinelLoop();
        //list.removeFirst();
        for (Integer integer : list) {
            System.out.println(integer+" ");
        }
        list.addFirst(5);
        list.addFirst(4);
        list.addFirst(3);
        list.addFirst(2);
        list.addFirst(1);
        list.addLast(6);
        list.addLast(7);
        list.removeFirst();
        list.removeFirst();
        list.removeLast();
        list.removeByValue(3);
        list.removeByValue(4);
        list.removeByValue(5);
        //list.removeByValue(6);
        //list.removeByValue(-1);
        for (Integer integer : list) {
            System.out.print(integer+" ");
        }

    }

}

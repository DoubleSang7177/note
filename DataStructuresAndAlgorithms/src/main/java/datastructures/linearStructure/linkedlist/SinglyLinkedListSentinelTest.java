package datastructures.linearStructure.linkedlist;

import org.junit.Test;

public class SinglyLinkedListSentinelTest {
    @Test
    public void addLastTest(){
        SinglyLinkedListSentinel list=new SinglyLinkedListSentinel();
        System.out.println(list.findLast());
        list.loop1(value->{
            System.out.println(value);
        });
        list.loop1(value->{
            System.out.println(value);
        });
        list.addLast(1);
        list.addLast(2);
        list.addLast(3);
        //System.out.println(list.get(-1));
        list.insert(2,12);
        list.addFirst(13);
        System.out.println(list.get(0));
        list.loop2(value->{
            System.out.println(value);
        });
        list.remove(0);
        System.out.println("===");
        for (Integer integer : list) {
            System.out.println(integer);
        }
        list.removeFirst();
        System.out.println("===");
        for (Integer integer : list) {
            System.out.println(integer);
        }


    }
}

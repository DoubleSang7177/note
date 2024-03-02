package datastructures.linearStructure.linkedlist;
import java.util.Arrays;
import java.util.Iterator;
import java.util.function.Consumer;
import java.util.stream.IntStream;

/*
* 动态数组
* */
public class DynamicArray implements Iterable<Integer>{
    private int size=0; //逻辑大小
    private int capacity=8; //容量
    private int[] array={};
    /*private int[] DSA.array=new int[capacity];*/

    /*
    * 添加元素到末尾
    * */
    public Boolean addLast(int element){
        return add(size,element);
    }
    /*
     * 在指定位置添加元素
     * */
    public Boolean add(int index,int element){
        //容量检查
        checkAndGrow();
        if(index<=size && index>=0){
            if (index<size&&index>=0){
                System.arraycopy(array,index,array,index+1,size-index);
            }
            array[index]=element;
            size++;
            return true;
        }else{
            return false;
        }
    }

    private void checkAndGrow() {
        /*
        * 容量检查
        * */
        if(size==0){
            array=new int[capacity];
        }else if(size==capacity){
            //进行扩容 1.5 1.618 2
            capacity+= capacity>>1;
            int[] newArray=new int[capacity];
            System.arraycopy(array,0,newArray,0,size);
            array=newArray;
        }
    }

    /*
    * 获取指定位置的元素
    * */
    public int get(int index){
        return array[index];//没有考虑索引越界的问题
    }
    /*
    * 删除指定位置的元素
    * */
    public int remove(int index){
        int element=array[index];
        if(index<size-1){
        System.arraycopy(array,index+1,array,index,size-index-1);
        }
        size--;
        return element;
    }
    /*
    *遍历方法1:
    * params:consumer 遍历要执行的操作，入参：每个元素
    * */
    public void foreach(Consumer<Integer> consumer){
        /*for (int element : DSA.array) {
            consumer.accept(element);
        }*/
        for (int i = 0; i < size; i++) {
            consumer.accept(array[i]);
        }
    }
    /*
    * 遍历方法2:
    *  Java迭代器（Iterator）是 Java 集合框架中的一种机制，是一种用于遍历集合（如列表、集合和映射等）的接口。
      它提供了一种统一的方式来访问集合中的元素，而不需要了解底层集合的具体实现细节。
      Java Iterator（迭代器）不是一个集合，它是一种用于访问集合的方法，可用于迭代 ArrayList 和 HashSet 等集合。
    * */
    @Override
    public Iterator<Integer> iterator() {
        return new Iterator<Integer>() {
            int i=0;
            @Override
            public boolean hasNext() {
                return i<size; //有没有下一个元素 i=size-1的时候有元素，返回true
            }

            @Override
            public Integer next() {//返回当前元素！!！并移动到下一个元素
                return array[i++];//先返回后自增 返回array[i] 然后i++
            }
        };
    }
    /*
    * 遍历方法3:
    * stream流来遍历
    * */
    public IntStream stream(){
        return IntStream.of(Arrays.copyOfRange(array,0,size));//左闭右开 [0,size)
    }
}

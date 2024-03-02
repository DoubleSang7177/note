package datastructures.search;

import org.junit.Assert;
import org.junit.Test;
import datastructures.linearStructure.linkedlist.DynamicArray;

import java.util.HashMap;
import java.util.Map;

public class TestBinarySearchTest {
    @Test
    public void test(){
        int[] arr={7,12,34,54,56,70};
        System.out.println(BinarySearch.binarySearch1(arr, 700));
        System.out.println(BinarySearch.binarySearch2(arr, 6));

    }

    @Test
    public void testLeftmost(){
        int[] arr={7,12,1,54,54,5,54,54,54,56,70};
        System.out.println(BinarySearch.binarySearchLeftmost(arr, 54));
    }

    @Test
    public void testRightmost(){
        int[] arr={7,12,54,54,54,54,54,56,70};
        System.out.println(BinarySearch.binarySearchRightmost(arr, 54));
    }

    @Test
    public void testDynamicArray(){
        DynamicArray array=new DynamicArray();
        array.addLast(1);
        array.addLast(2);
        array.addLast(3);
        array.addLast(4);
        array.addLast(5);
        System.out.println(array.add(2, 12));
        System.out.println("第一种遍历方式");
        array.foreach(element->
            System.out.println(element)
        );
        System.out.println("第二种遍历方式");
        for (Integer element : array) {//增强for在底层都会调用hasNext()和next()
            System.out.println(element);
        }
        System.out.println("第三种遍历方式");
        array.stream().forEach(element->{
            System.out.println(element);
        });
        /*
        * 用断言判断程序正确与否
        * */
        Assert.assertEquals(12,array.remove(2));

    }
    @Test
    public void testDynamicArray2(){
        DynamicArray dynamicArray=new DynamicArray();
        dynamicArray.add(0,10);
        /*
        * 必须先添加元素才能进行初始化，否则get时报错！
        * */
        System.out.println(dynamicArray.get(1));
    }

    @Test
    public void testMap(){
        int[] nums={3,2,3};
        int sum=0;
        Map<Integer,Integer> count=new HashMap<>();
        for (int num : nums) {
            if(!count.containsKey(num)){
                count.put(num,1);
            }else{
                count.put(num,count.get(num)+1);
            }
        }
        System.out.println("出现次数"+nums.length/2);
        if(nums.length/2==1){
            sum=nums.length;
        }else{
            for(Integer key:count.keySet()){

                System.out.println(key+"出现了"+count.get(key));
                if(count.get(key)>=nums.length/2){
                    sum++;
                }
            }
        }

        System.out.println(sum);


        Map.Entry<Integer, Integer> majorityEntry = null;
        for (Map.Entry<Integer, Integer> entry : count.entrySet()) {
            if (majorityEntry == null || entry.getValue() > majorityEntry.getValue()) {
                majorityEntry = entry;
            }
        }

        System.out.println(majorityEntry.getKey());


    }
}

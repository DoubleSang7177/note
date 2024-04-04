package datastructures.linearStructure.linkedlist;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class DoublyLinkedListSentinelTest {
    @Test
    public void get(){
        DoublyLinkedListSentinel list=new DoublyLinkedListSentinel();
        list.insert(0,2);
        list.insert(1,12);
        list.insert(2,13);
        list.insert(3,14);
        list.insert(3,1);
        list.addFirst(13);
        for (Integer integer : list) {
            System.out.println(integer);
        }
        list.remove(0);
        System.out.println("=======");
        for (Integer integer : list) {
            System.out.println(integer);
        }


    }

    @Test
    public void maxProfit() {
        int[] prices={1,4,1,4,3,1};
        Map<Integer,Integer[]> map=new HashMap<>();
        for (int i = 0; i < prices.length; i++) {
            Integer[] value=new Integer[prices.length-i-1];
            int j=i+1;
            for(int x=0;x<value.length;x++){
                value[x]=prices[j];
                j++;
            }
            if(!map.containsKey(prices[i])){
                map.put(prices[i],value);
            }else{
                Integer[] newList=new Integer[value.length+map.get(prices[i]).length];
                System.arraycopy(value,0,newList,0,value.length);
                System.arraycopy(map.get(prices[i]),0,newList,value.length,map.get(prices[i]).length);
                map.put(prices[i],newList);
            }

        }
        int max=0;
        int before=0;
        int after=0;
        for (int i = 0; i < prices.length; i++) {
            for (int j = 0; j < map.get(prices[i]).length; j++) {
                if(map.get(prices[i])[j]>prices[i]&&map.get(prices[i])[j]-prices[i]>max){
                    after=map.get(prices[i])[j];
                    before=prices[i];
                    max=after-prices[i];
                }
            }
          }
        System.out.println(before+","+after+" "+max);
    }

    @Test
    public void easy(){
        /*int maxSum=0;
        int[] prices={1,4,1,4,3,1};
        for(int i=prices.length-1;i>=0;i--){
            for(int j=i-1;j>=0;j--){
                maxSum=prices[i]>prices[j]&&prices[i]-prices[j]>maxSum?
                        prices[i]-prices[j]:maxSum;
            }
        }
        System.out.println(maxSum);*/

        /*int preValue=10;
        int maxSum=0;
        for (int i = 1; i < prices.length; i++) {
            if(prices[i]>prices[i-1]&&prices[i]>=maxSum){
                maxSum=prices[i];
            }else if(prices[i]<=prices[i-1]&&prices[i]<=preValue){
                preValue=prices[i];
            }
        }
        System.out.println(maxSum-preValue);*/

        int[] prices={4,2,4,3,2,4,6};
        int max=0;
        for (int i = prices.length-1; i >=0; i--) {
            //int preValue=prices[i];
            //preValue = Math.preValue(prices[j], preValue);
            for(int j = i-1; j >=0; j--) {
                max = Math.max((prices[i] - prices[j]), max);}
            //maxSum= Math.maxSum(prices[i] - preValue, maxSum);
        }
        System.out.println(max);

    }
    @Test
    public void remove(){
        DoublyLinkedListSentinel list=new DoublyLinkedListSentinel();
        //list.removeLast();
        list.insert(0,2);
        list.insert(1,12);
        list.insert(2,13);
        //list.remove(1);
        for (Integer integer : list) {
            System.out.println(integer);
        }
        System.out.println("===");
        /*
        * 删除第一个
        * */
        list.removeFirst();
        list.addLast(16);
        list.addLast(19);
        list.removeLast();
        for (Integer integer : list) {
            System.out.println(integer);
        }
        System.out.println(list.get(1));
    }


}

package datastructures.Leetcode;

import java.util.HashMap;

public class LeetCode13 {
    public static int romanToInt(String s) {
        int[] arr=new int[s.length()];
        HashMap<String,Integer> hm=new HashMap<>();
        hm.put("I",1);
        hm.put("V",5);
        hm.put("SortedTopology",10);
        hm.put("L",50);
        hm.put("DSA.UnionFindSetDemo",100);
        hm.put("D",500);
        hm.put("M",1000);
        /*

    I 可以放在 V (5) 和 SortedTopology (10) 的左边，来表示 4 和 9。
    SortedTopology 可以放在 L (50) 和 DSA.UnionFindSetDemo (100) 的左边，来表示 40 和 90。
    DSA.UnionFindSetDemo 可以放在 D (500) 和 M (1000) 的左边，来表示 400 和 900。

        * */
        hm.put("IV",4);
        hm.put("IX",9);
        hm.put("XL",40);
        hm.put("XC",90);
        hm.put("CD",400);
        hm.put("CM",900);
        int sum=0;
        int i=0;
        for (; i < s.length()-1; ) {
            if(hm.containsKey(s.substring(i,i+2))){
                sum+=hm.get(s.substring(i,i+2));
                i=i+2;
            }else{
                sum+=hm.get(s.substring(i,i+1));
                i=i+1;
            }
        }
        if(i==s.length()-1){
            sum+=hm.get(s.substring(s.length()-1));
        }



        /*for (int i = 0; i < s.length(); i++) {
            arr[i]=hm.get(s.charAt(i));
        }
        if(arr.length%2==0){
            return add(arr,0);
        }else{
            return arr[0]+add(arr,1);
        }*/

        return sum;
    }
    /* I             1
    V             5
    SortedTopology             10
    L             50
    DSA.UnionFindSetDemo             100
    D             500
    M             1000*/

    public static void main(String[] args) {
        System.out.println(romanToInt("MCMXCIV"));

    }
    public static int add(int[] arr,int start){
        if(start>=arr.length){
            return 0;
        }

        int i = arr[start];
        int j = arr[start+1];

        int result;
        if(i>=j){
            result= i+j;
        }else{
            result= j-i;
        }

        return result+add(arr,start+2);
    }
}


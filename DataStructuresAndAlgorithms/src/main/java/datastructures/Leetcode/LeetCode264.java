package datastructures.Leetcode;

import java.util.*;
/*
* 返回第n个丑数
* */
public class LeetCode264 {
    public static int nthUglyNumber(int n) {
        int[] factors={2,3,5};
        HashSet<Long> set=new HashSet<>();//用来判断是否进过优先队列
        PriorityQueue<Long> pq=new PriorityQueue<>();
        set.add(1L);//1是丑数
        pq.offer(1L);
        int ugly=0;
        for (int i = 0; i < n; i++) {
            Long cur = pq.poll();//第i+1个丑数
            ugly= Math.toIntExact(cur);
            for (int factor : factors) {
                Long next = cur * factor;//如果cur为int类型，存在溢出风险
//                Integer.MAX_VALUE==0x7fffffff
//                Long.MAX_VALUE==0x7fffffffffffffffL
                if (set.add(next)){//true则说明是第一次加next
                    pq.offer(next);
                    set.add(next);
                }
            }
        }
        return ugly;
    }

    public static void main(String[] args) {
        System.out.println(nthUglyNumber(1407));
    }

}

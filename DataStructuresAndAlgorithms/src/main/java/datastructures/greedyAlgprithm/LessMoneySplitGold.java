package datastructures.greedyAlgprithm;
import java.util.PriorityQueue;

/*
* 贪心算法
* 用最少的铜板切分金条
* */
public class LessMoneySplitGold {
    public static int lessMoneySplitGold(int[] arr){
        int sum=0;
        //小根堆
        PriorityQueue<Integer> queue=new PriorityQueue<>();
        for (int i : arr) {
            queue.add(i);
        }
        int cur;
        while (queue.size()>1){
            cur=queue.poll()+queue.poll();
            sum+=cur;
            queue.add(cur);
        }
        return sum;
    }

}

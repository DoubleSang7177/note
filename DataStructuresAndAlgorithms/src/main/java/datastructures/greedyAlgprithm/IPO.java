package datastructures.greedyAlgprithm;
import java.util.Comparator;
import java.util.PriorityQueue;
/*
* 贪心算法
* 在规定能做的项目最大个数的情况下做出利润最大化
* */
public class IPO {
    public static class Node{
        int capital;
        int profit;

        public Node(int capital, int profit) {
            this.capital = capital;
            this.profit = profit;
        }
    }

    public static class MinCostComparator implements Comparator<Node>{

        @Override
        public int compare(Node o1, Node o2) {
            return o1.capital -o2.capital;
        }
    }
    public static class MaxProfitComparator implements Comparator<Node>{

        @Override
        public int compare(Node o1, Node o2) {
            return o2.profit-o1.profit;
        }
    }

    public static int ipo(int[] profits,int[] capital,int w,int k){
        //w为初始资金 k为最多能做的项目个数
        PriorityQueue<Node> minCostQ=new PriorityQueue<>(new MinCostComparator());
        PriorityQueue<Node> maxProfitQ=new PriorityQueue<>(new MaxProfitComparator());
        for (int i = 0; i < profits.length; i++) {
            minCostQ.add(new Node(capital[i],profits[i]));
        }
        for (int i = 0; i < k; i++) {
            //解锁所有启动资金小于当前所拥有的资金的项目
            while (!minCostQ.isEmpty() && minCostQ.peek().capital<=w){
                maxProfitQ.add(minCostQ.poll());
            }
            //没有能做的项目，直接返回
            if (maxProfitQ.isEmpty()){
                break;
            }
            //在能做的项目中选出利润最大的项目来做，做完更新当前资金
            w +=maxProfitQ.poll().profit;
        }
        return w;
    }
}

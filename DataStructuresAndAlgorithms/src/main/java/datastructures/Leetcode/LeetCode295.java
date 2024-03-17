package datastructures.Leetcode;
import java.util.PriorityQueue;
/*
* 给出有序树的中位数
* */
public class LeetCode295 {
    public static class MedianFinder {
        private PriorityQueue<Integer> left;
        private PriorityQueue<Integer> right;
        public MedianFinder() {
            this.left=new PriorityQueue<>((o1,o2)-> o2-o1);//大根堆
            this.right=new PriorityQueue<>();//小根堆
        }

        public void addNum(int num) {
            if (left.isEmpty()||num<=left.peek()){
                left.offer(num);
                if (left.size()-right.size()>1){
                    right.offer(left.poll());
                }
            }else{
                right.offer(num);
                if (right.size()>left.size()){
                    left.offer(right.poll());
                }
            }
        }

        public double findMedian() {
            if (left.size()==right.size()){
               return left.peek()/2.0+right.peek()/2.0;
            }else{
                return (double)left.peek();
            }
        }
    }

    public static void main(String[] args) {
        MedianFinder mf=new MedianFinder();
        mf.addNum(1);
        mf.addNum(3);
        mf.addNum(2);
        mf.addNum(6);
        mf.addNum(5);
        mf.addNum(5);
        mf.addNum(2);
        mf.addNum(12);
        System.out.println(mf.findMedian());
    }
}

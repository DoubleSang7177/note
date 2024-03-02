package datastructures.linearStructure.heap;

import java.util.HashMap;
import java.util.PriorityQueue;
/*
* 定义一种数据结构解决滑动窗口求中位数问题
* */
public class DualHeap {
    private PriorityQueue<Integer> small;
    private PriorityQueue<Integer> large;
    private HashMap<Integer,Integer> delay;
    private int smallSize;
    private int largeSize;
    private int k;
    public DualHeap(int k){
        this.large=new PriorityQueue<>();
        this.small=new PriorityQueue<>( ( o1,o2) -> o2.compareTo(o1));//直接用减号会出现溢出！！！😉😉🤷‍♀️
        this.delay=new HashMap<>();
        this.largeSize=0;
        this.smallSize=0;
        this.k=k;

    }
    /*
    * 获取中位数
    * */
    public double getMedian(){
        return (k&1)==1?
                (double)small.peek():
                ((double)small.peek()/2+(double) large.peek()/2);
    }
    /*
    * 插入数据
    * */
    public void insert(int num){
        if (small.isEmpty() || num<=small.peek()){
            small.offer(num);
            ++smallSize;
        }else{
            large.offer(num);
            ++largeSize;
        }
        makeBalance();//每次加入一个元素都要判断是否需要调整
    }
    /*
    * 延迟删除！在调整过程中该元素到堆顶时才会真正从结构中删除
    * 但是大根堆和小根堆的大小是删除元素后的实际值，会严格按照规则分布，
    * 尽管延迟删除堆顶元素就是实际的中位数(k奇数时大根堆的堆顶；k偶数时大根堆和小根堆堆顶的平均值)
    * 延迟元素只要不在堆顶参与计算也不影响实际的数据排序和分布，就不会影响最终结果
    * */
    public void erase(int num){
        delay.put(num,delay.getOrDefault(num,0)+1);
        if (num<=small.peek()){
            --smallSize;
            if (num==small.peek()){//如果要删除的元素在堆顶直接删除
                prune(small);
            }
        }else{
            --largeSize;
            if (num==large.peek()){
                prune(large);
            }
        }
        makeBalance();//根据实际的堆大小进行调整
    }
    /*
    * 满足small.size()-large.size()==1 k为奇数 这时small.peek()就是中位数
    * 或者 small.size()-large.size()==0 k为偶数 这时中位数是small.peek()和large.peek()的平均值
    * */
    private void makeBalance() {
        if (smallSize-largeSize>1){
            large.offer(small.poll());
            --smallSize;
            ++largeSize;
            prune(small);//检查新堆顶是不是延迟删除的，如果是，那么在堆顶时删除
        }
        if (largeSize>smallSize){
            small.add(large.poll());
            ++smallSize;
            --largeSize;
            prune(large);//检查新堆顶是不是延迟删除的，如果是，那么在堆顶时删除
        }
    }
    // 如果堆顶是延迟删除过的元素，不断地弹出 heap 的堆顶元素，并且更新哈希表
    private void prune(PriorityQueue<Integer> heap) {
        while (!heap.isEmpty()){
            Integer num = heap.peek();
            if (delay.containsKey(num)){
                delay.put(num,delay.get(num)-1);
                if (delay.get(num)==0){//该元素已删干净，可以移除map
                    delay.remove(num);
                }
                heap.poll();//只要在堆顶出现延迟删除过的元素，趁在堆顶，马上删除
            }else{
                break;
            }
        }
    }
}

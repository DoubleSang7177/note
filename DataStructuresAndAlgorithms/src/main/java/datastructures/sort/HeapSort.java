package datastructures.sort;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Random;

/*
* 堆排序
* 堆结构数组的底层就是完全二叉树
* */
public class HeapSort {

    /*
    * 数组排序
    * 已知某数组在局部上有序，也就是说排序移动的距离不会超过k
    * 使用java提供的小根堆实现
    * PriorityQueue优先队列其实就是小根堆
    * */
    public static void heapSort(int[] arr,int k){
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        int index=0;
        for (; index <= k; index++) {
            heap.add(arr[index]);
        }
        int i=0;
        for (;index<arr.length;i++,index++){
            heap.add(arr[index]);
            arr[i]=heap.poll();
        }
        while (!heap.isEmpty()){
            arr[i]=heap.poll();
        }

    }

    /*
    * 堆排序
    * */
    public static void heapSort(int[] arr){
        if (arr==null || arr.length<2){
            return;
        }
        for (int i = 0; i < arr.length; i++) {//O(N)
            heapInsert(arr,i);//O(logN)
        }
        int heapSize=arr.length;
        swap(arr,0,--heapSize);
        while (heapSize>0){//O(N)
            heapify(arr,0,heapSize);//O(logN)
            swap(arr,0,--heapSize);//O(1)
        }
    }
    /*
    * 某个数现在处在index位置，向上继续移动,也就是形成大根堆
    * index节点和父节点进行比较，比父亲大则向上窜
    * 不比父亲大则不做处理，退出
    * */
    public static void heapInsert(int[] arr, int index){
        //(index-1)/2就是index位置节点的父节点位置
        while (arr[index]>arr[(index-1)/2]){//当index为0时，判断条件是否满足时不会抛异常吗？arr[0]>arr[-1/2]??
            //先交换值
           swap(arr,index,(index-1)/2);
           //更新该数的位置
            index=(index-1)/2;
        }
    }
    /*
    * 某个数在index位置上，能否往下移动
    * 向下移动是指，看index位置节点的值是否大于左右孩子中值大的节点
    * 是则交换，并index更新到参与交换的孩子节点位置，继续向下移动
    * 如果不是，则说明当前节点就是该子树上的最大值，不用交换也不用向下移动，直接return
    * */
    public static void heapify(int[] arr,int index,int heapSize){
        int left=index*2+1; //左孩子的下标
        while (left<heapSize){
            int largest=left+1<heapSize&&arr[left+1]>arr[left]?left+1:left;
            largest=arr[largest]>arr[index]?largest:index;
            if (largest==index){
                return;//一定要return不然当largest==index时进入死循环！
            }
            swap(arr,index,largest);
            index=largest;
            left=index*2+1;
        }
    }
    /*
    * 元素交换位置
    * */
    private static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    /*
    * 自定义一个比较器对象，传入java提供的小根堆得到大根堆
    * */
    public static class AComp implements Comparator<Integer>{
        /*
         *返回值是负数时，第一个参数排上面
         * 返回值是正数时，第二个参数排上面
         * 返回0时，谁在前面无所谓
        * */
        @Override
        public int compare(Integer o1, Integer o2) {
            return o2-o1;
        }
    }

    public static void main(String[] args) {
//        int[] arr = {6, 3, 2, 2, 1, 3, 81, -23, 11, 65, 22, 1, 1, -1, 23, -5, 5, 921, 2, 2, 111, 3, 22, 1, 2, 3, 21, 2, 1, -1, 23, 12, 11};
        int[] arr={7,9,5,9,5,4,5,0};
        heapSort(arr);
        for (int i : arr) {
            System.out.print(i + " ");
        }
        System.out.println();
        System.out.println("========");
        int[] arr2={7,9,5,9,5,4,5,0};//测试实例不准确所以没有进行测试哦！
        heapSort(arr,6);
        for (int i : arr) {
            System.out.print(i + " ");
        }

        PriorityQueue<Integer> heap = new PriorityQueue<>(new AComp());
        Random random=new Random();
        for (int j = 0; j < 30; j++) {
            heap.add(random.nextInt(100));
        }
        System.out.println();
        while (!heap.isEmpty()){
            System.out.print(heap.poll()+" ");
        }

    }
}

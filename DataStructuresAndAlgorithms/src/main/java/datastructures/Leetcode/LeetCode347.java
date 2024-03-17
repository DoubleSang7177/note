package datastructures.Leetcode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
/*
 * 前k个高频元素
 * 13ms 好弱智的解法😢。。。但是效率比PriorityQueue高1ms🤦‍♂️
 * */
public class LeetCode347 {
    public static int[] topKFrequent(int[] nums, int k) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            if (map.containsKey(num)) {
                map.put(num, map.get(num) + 1);
            } else {
                map.put(num, 1);
            }
        }
        //结算
        int[] result = new int[k];
        //进小根堆
        HashMap<Integer, List<Integer>> countMap = new HashMap<>();
        for (Integer num : map.keySet()) {
            int count = map.get(num);//num出现的次数
            if (countMap.containsKey(count)) {
                List<Integer> list = countMap.get(count);
                list.add(num);
                countMap.put(count, list);
            } else {
                List<Integer> list = new ArrayList<>();
                list.add(num);
                countMap.put(count, list);
            }
        }
        //对出现次序进行排序
        int[] countArr=new int[countMap.size()];
        int index=0;
        for (Integer count : countMap.keySet()) {
            countArr[index++]=count;
        }
        int size=countArr.length;
        for (int i = 0; i < countArr.length; i++) {
            heapInsert(countArr,i);
        }
        swap(countArr,0,--size);
        while (size>0){
            heapify(countArr,0,size);
            swap(countArr,0,--size);
        }
        //已从小到大排好序
        --k;
        for (int i = countArr.length-1; i>=0; i--) {
            List<Integer> list = countMap.get(countArr[i]);
            for (Integer num : list) {
                if (k>=0){
                    result[k]=num;
                    k--;
                }
            }
        }
        return result;
    }

    /*
     * 向上窜，比父节点大则交换，节点移动到父节点
     * */
    private static void heapInsert(int[] arr, int index) {
        while (arr[index] > arr[(index - 1) / 2]) {
            swap(arr, index, (index - 1) / 2);
            index = (index - 1) / 2;
        }
    }

    /*
     * 从节点index出发，如果比左右孩子的较大者大，则与其交换，否则退出
     * 重复此操作
     * */
    public static void heapify(int[] result, int index, int heapSize) {//向下冒泡
        int left = index * 2 + 1;//左孩子
//        int right = left + 1;//右孩子
        while (left < heapSize) {
            int largest = (left+1)<heapSize && result[left+1] > result[left] ? (left+1) : left;
            largest = result[index] > result[largest] ? index : largest;
            if (largest == index) {
                return;
            }
            swap(result, index, largest);
            index = largest;
            left = index * 2 + 1;
        }
    }

    private  static void swap(int[] result, int i, int j) {
        int tmp = result[i];
        result[i] = result[j];
        result[j] = tmp;
    }


    public static void main(String[] args) {
        int[] arr={1,4,3,2,2,2,3,3,1};//9
        for (int i : topKFrequent(arr, 6)) {
            System.out.println(i);
        }
        System.out.println((int) (Math.random() * arr.length));
    }
}

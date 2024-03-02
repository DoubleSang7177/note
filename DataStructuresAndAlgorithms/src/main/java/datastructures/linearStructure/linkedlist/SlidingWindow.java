package datastructures.linearStructure.linkedlist;

import java.util.*;

/*
 * 滑动窗口
 * 使用双端队列LinkedList：两端既可以插入也可以删除
 * */
public class SlidingWindow {

    public static int[] get(int[] arr, int w) {
        if (arr == null || w < 1 || arr.length < w) {
            return null;
        }
        int[] res = new int[arr.length - w + 1];
        LinkedList<Integer> list = new LinkedList<>();
        int r = 0;
        int index = 0;
        while (r < arr.length) {
            //严格保证加入元素后保持有序
            while (!list.isEmpty() && arr[r] >= arr[list.peekLast()]) {
                list.pollLast();
            }
            list.add(r);
            //保证最大值属于有效范围
            if (list.peekFirst() == r - w) {
                list.pollFirst();
            }
            if (r - w + 1 >= 0) {//窗口形成了
                res[index++] = arr[list.getFirst()];
            }
            r++;
        }
        return res;
    }

    public static int[] getMaxInWindow2(int[] arr, int w) {
        if (arr == null || w < 1 || arr.length < w) {
            return null;
        }
        int[] res = new int[arr.length - w + 1];
        LinkedList<Integer> list = new LinkedList<>();
        int index = 0;
        for (int i = 0; i < arr.length; i++) {
            while (!list.isEmpty() && arr[i] >= arr[list.peekLast()]) {
                list.pollLast();
            }
            list.add(i);
            int l = i - w;//过期的下标
            if (l == list.peekFirst()) {
                list.pollFirst();
            }
            if (i - w + 1 >= 0) {//窗口形成了
                res[index++] = arr[list.getFirst()];
            }
        }
        return res;
    }


    public static void main(String[] args) {
        int[] ints = get(new int[]{1, 3, -1, -3, 5, 3, 6, 7}, 3);
        for (int i = 0; i < ints.length; i++) {
            System.out.print(ints[i] + " ");
        }
        System.out.println();
        TreeMap<Integer, Integer> map = new TreeMap<>();
        map.put(3, 12);
        map.put(1, 3);
        map.put(0, 16);
        for (Integer value : map.values()) {
            System.out.print(value + " ");
        }
        System.out.println();
        for (Integer integer : map.keySet()) {
            System.out.print(integer + "..");
        }

        System.out.println();


    }

}

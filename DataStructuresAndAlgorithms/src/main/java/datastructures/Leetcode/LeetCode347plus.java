package datastructures.Leetcode;

import java.util.*;

/*
 * 14ms
 * */
public class LeetCode347plus {
    public static int[] topKFrequent(int[] nums, int k) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            if (map.containsKey(num)) {
                map.put(num, map.get(num) + 1);
            } else {
                map.put(num, 1);
            }
        }
        int[] result = new int[k];
        PriorityQueue<Integer> pq = new PriorityQueue<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return map.get(o1) - map.get(o2);
            }
        });
        for (Integer num : map.keySet()) {
            if (pq.size() < k) {
                pq.offer(num);
            } else if (map.get(num) > map.get(pq.peek())) {
                pq.poll();
                pq.offer(num);
            }
        }
        int index = 0;
        while (!pq.isEmpty()) {
            result[index++] = pq.poll();
        }
        return result;
    }

    public static void main(String[] args) {
        int[] arr = {1, 4, 3, 2, 2, 2, 3, 3, 1};
        for (int i : topKFrequent(arr, 3)) {
            System.out.println(i);
        }
    }
}

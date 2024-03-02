package datastructures.linearStructure.stack;

import java.util.*;
import java.util.Stack;

/*
* 单调栈
* */
public class MonotoneStack {

    /*
    * 接雨水问题
    * */
    public static int trap(int[] height) {
        int res=0;
        java.util.Stack<Integer> stack = new java.util.Stack<>();
        for (int i = 0; i < height.length; ) {
            if (stack.isEmpty() || height[i] <= height[stack.peek()]) {
                stack.push(i);
                i++;
            } else {
                int midHeight=height[stack.pop()];
                int leftHeight=!stack.isEmpty()?height[stack.peek()]:0;
                int leftIndex=!stack.isEmpty()?stack.peek():-1;
                if (leftHeight>midHeight){
                    res+=(i-leftIndex-1)*(Math.min(leftHeight,height[i])-midHeight);
                }
            }
        }
        return res;
    }

    public static HashMap<Integer, List<Integer>> get(int[] arr) {
        java.util.Stack<Integer> stack = new java.util.Stack<>();
        HashMap<Integer, List<Integer>> map = new HashMap<>();
        for (int i = 0; i < arr.length; ) {
            if (stack.isEmpty() || arr[i] > arr[stack.peek()]) {
                stack.push(i);
                i++;
            } else {
                Integer index = stack.pop();
                ArrayList<Integer> list = new ArrayList<>();
                list.add(!stack.isEmpty()?stack.peek():-1);//左边比自己小且最近的位置
                list.add(i);//右边比自己小的位置
                map.put(index, list);
            }
        }
        //最后结算
        while (!stack.isEmpty()){
            Integer index = stack.pop();
            ArrayList<Integer> list = new ArrayList<>();
            list.add(!stack.isEmpty()?stack.peek():-1);//左边比自己小且最近的位置
            list.add(-1);//右边比自己小的位置
            map.put(index, list);
        }
        return map;
    }

    public static HashMap<Integer, List<Integer>> get2(int[] arr) {
        java.util.Stack<LinkedList<Integer>> stack = new Stack<>();
        HashMap<Integer, List<Integer>> map = new HashMap<>();
        for (int i = 0; i < arr.length; ) {
            if (stack.isEmpty() || arr[i] > arr[stack.peek().peekLast()]) {
                LinkedList<Integer> list= new LinkedList<>();
                list.offerLast(i++);
                stack.push(list);
            }else if(arr[i] == arr[stack.peek().peekLast()]){
                stack.peek().offerLast(i++);
            } else {
                LinkedList<Integer> list=stack.pop();
                while (!list.isEmpty()){
                    int index=list.pollLast();
                    ArrayList<Integer> res = new ArrayList<>();
                    res.add(!stack.isEmpty()?stack.peek().peekLast():-1);//左边比自己小且最近的位置
                    res.add(i);//右边比自己小的位置
                    map.put(index, res);
                }
            }
        }
        //最后结算
        while (!stack.isEmpty()){
            LinkedList<Integer> list=stack.pop();
            while (!list.isEmpty()){
                int index=list.pollLast();
                ArrayList<Integer> res = new ArrayList<>();
                res.add(!stack.isEmpty()?stack.peek().peekLast():-1);//左边比自己小且最近的位置
                res.add(-1);//右边比自己小的位置
                map.put(index, res);
            }
        }
        return map;
    }

    public static void main(String[] args) {
        int[] arr={5, 4, 4,2,2,6, 7, 2, 3, 0, 1};
        HashMap<Integer, List<Integer>> map = get2(arr);
        for (Integer key : map.keySet()) {
            System.out.println(arr[key]+" 左"+map.get(key).get(0)+" 右 "+map.get(key).get(1));
        }

        System.out.println(trap(new int[]{0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1}));

    }
}

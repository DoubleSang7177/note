package datastructures.Leetcode;
import java.util.Stack;

/*
* 给定一个整数数组temperatures ，表示每天的温度，
* 返回一个数组 answer ，其中answer[i]是指对于第 i 天，
* 下一个更高温度出现在几天后。如果气温在这之后都不会升高，
* 请在该位置用 0 来代替。
* */
public class LeetCode739 {
    /**
     * 时间复杂度：O(n)，其中n是温度列表的长度。正向遍历温度列表一遍，对于温度列表中的每个下标，
     * 最多有一次进栈和出栈的操作。
     * 空间复杂度：O(n)，其中n是温度列表的长度。需要维护一个单调栈存储温度列表中的下标。
     *
     * @param temperatures 温度列表
     * @return
     */
    public int[] dailyTemperatures(int[] temperatures) {//200ms
        int[] ans = new int[temperatures.length];
        Stack<Integer> stack = new Stack<>();
        for (int j = 0; j < temperatures.length; j++) {
            while (!stack.isEmpty() && temperatures[j] > temperatures[stack.peek()]) {
                int i = stack.pop();
                ans[i] = j - i;
            }
            stack.push(j);
        }
        return ans;
    }
}

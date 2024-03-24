package datastructures.Leetcode;
import java.util.*;
/*
* 你正在参加一个多角色游戏，每个角色都有两个主要属性：攻击和防御 。给你一个二维整数数组 properties ，
* 其中 properties[i] = [attacki, defensei] 表示游戏中第 i 个角色的属性。
* 如果存在一个其他角色的攻击和防御等级都严格高于该角色的攻击和防御等级，
* 则认为该角色为弱角色。更正式地，如果认为角色 i 弱于存在的另一个角色 j ，
* 那么 attackj > attacki 且 defensej > defensei
* 返回 弱角色 的数量。
* */
public class LeetCode1996 {
    /**
     * 返回弱角色数量
     * @param properties 角色数组
     * @return 数量
     */
    public static int numberOfWeakCharacters(int[][] properties) {
        Arrays.sort(properties,(o1,o2)-> o1[0]!=o2[0]?o1[0]-o2[0]:o2[1]-o1[1]);//根据角色的攻击值升序排序，
        // 如果攻击值相同则根据预防值降序排序，降序排序是防止后面的角色去释放栈中攻击值相同的角色时，预防值大的角色放
        // 后后面阻挡前面预防值偏小的角色
        int sum=0;//此时栈顶元素为预防值最小的角色，如果存在一个攻击值大于栈顶角色的，那么栈顶角色为若弱角色
        Deque<Integer> deque=new ArrayDeque<>();
        for (int[] role : properties) {
            while (!deque.isEmpty()&&role[1]>deque.peek()){
                sum++;
                deque.poll();
            }
            deque.push(role[1]);
        }
        return sum;
    }

    public static void main(String[] args) {
        int[][] arr={{1,5},{10,4},{4,3}};
        numberOfWeakCharacters(arr);

    }
}

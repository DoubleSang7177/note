package datastructures.Leetcode;
public class LeetCode1526 {
    public static int minNumberOperations(int[] target) {
        int count = target[0];
        for (int i = 0; i < target.length - 1; i++) {
            if (target[i + 1] > target[i]) {
                count += target[i + 1] - target[i];
            }
        }
        return count;
    }

    public static void main(String[] args) {
        int[] target = {1, 2, 3, 2, 1};
        minNumberOperations(target);
    }
}

package datastructures.violentRecursion;

/*
 *  暴力递归
 *  哈诺塔经典问题
 * */
public class Hanoi {
    public static void hanoi(int n) {
        process(n, "左", "右", "中");
    }

    private static void process(int n, String start, String end, String other) {
        if (n == 1) {
            System.out.println("Move 1 from " + start + " to " + end);
            return;
        }

        process(n - 1, start, other, end);
        System.out.println("Move " + n + " from " + start + " to " + end);
        process(n - 1, other, end, start);

    }

    public static void main(String[] args) {
        hanoi(15);
    }
}

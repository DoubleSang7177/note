package datastructures.dp;
/*
* 象棋
* */
public class HorseJump {
    public static int process(int x, int y, int step) {
        if (x < 0 || x > 8 || y < 0 || y > 9) {
            return 0;
        }
        if (step == 0) {
            return (x == 0 && y == 0) ? 1 : 0;
        }
        return process(x + 2, y + 1, step - 1)
                + process(x + 2, y - 1, step - 1)
                + process(x - 2, y + 1, step - 1)
                + process(x - 2, y - 1, step - 1)
                + process(x + 1, y + 2, step - 1)
                + process(x + 1, y - 2, step - 1)
                + process(x - 1, y + 2, step - 1)
                + process(x - 1, y - 2, step - 1);
    }

    public static int get(int x, int y, int step) {
        if (x < 0 || x > 8 || y < 0 || y > 9 || step<0) {
            return 0;
        }
        int[][][] dp = new int[9][10][step + 1];
        dp[0][0][0] = 1;//第0层的剩余位置默认全是0
        for (int h = 1; h <= step; h++) {//第1层开始
            for (int row = 0; row < 9; row++) {
                for (int col = 0; col < 10; col++) {//每一层依赖下一层
                    dp[row][col][h] = getValue(row+2,col+1,h-1,dp)
                            + getValue(row+2,col-1,h-1,dp)
                            + getValue(row-2,col+1,h-1,dp)
                            + getValue(row-2,col-1,h-1,dp)
                            + getValue(row+1,col+2,h-1,dp)
                            + getValue(row+1,col-2,h-1,dp)
                            + getValue(row-1,col+2,h-1,dp)
                            + getValue(row-1,col-2,h-1,dp);
                }
            }
        }
        return dp[x][y][step];
    }

    public static int getValue(int row, int col, int h, int[][][] dp) {
        if (row < 0 || row > 8 || col < 0 || col > 9) {
            return 0;
        }
        return dp[row][col][h];
    }
    /*
     * pass掉！
     * */
    public static int process2(int n, int a, int b, int x, int y, int step) {
        if (x == n || y == n || x < 0 || y < 0) {
            return 0;
        }
        if (step == 0) {
            return (a == x && y == b) ? 1 : 0;
        }
        return process2(n, a, b, x, y + 1, step - 1)
                + process2(n, a, b, x, y - 1, step - 1)
                + process2(n, a, b, x - 1, y, step - 1)
                + process2(n, a, b, x + 1, y, step - 1)
                + process2(n, a, b, x - 1, y - 1, step - 1)
                + process2(n, a, b, x + 1, y + 1, step - 1)
                + process2(n, a, b, x - 1, y + 1, step - 1)
                + process2(n, a, b, x + 1, y - 1, step - 1);
    }

    /*
     * 不是这种游戏规则，但是整体思路是对的！
     * */
    public static int process3(int n, int x, int y, int step) {
        if (x == n || y == n || x < 0 || y < 0) {
            return 0;
        }
        if (step == 0) {
            return (x == 0 && y == 0) ? 1 : 0;
        }
        return process3(n, x, y + 1, step - 1)
                + process3(n, x, y - 1, step - 1)
                + process3(n, x - 1, y, step - 1)
                + process3(n, x + 1, y, step - 1)
                + process3(n, x - 1, y - 1, step - 1)
                + process3(n, x + 1, y + 1, step - 1)
                + process3(n, x - 1, y + 1, step - 1)
                + process3(n, x + 1, y - 1, step - 1);
    }

    public static void main(String[] args) {
//        System.out.println(process2(8, 3, 2, 0, 0, 10));
//        System.out.println(process3(8, 3, 2, 10));
        System.out.println(process(7, 7, 10));
        System.out.println(get(7,7,10));

    }
}

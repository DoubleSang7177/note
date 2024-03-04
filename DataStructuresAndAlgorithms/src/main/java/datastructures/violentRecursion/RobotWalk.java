package datastructures.violentRecursion;
/*
* 机器人走路
* */
public class RobotWalk {

    public static int robotWalk(int n, int end, int start, int k){
        int[][] dp=new int[k+1][n+1];
        for (int i = 0; i <= k; i++) {
            for (int j = 0; j <= n; j++) {
                dp[i][j]=-1;
            }
        }
        return process(n,end,start,k,dp);
    }
    public static int process(int n, int end, int cur, int res, int[][] dp){
        if (dp[res][cur]!=-1){
            return dp[res][cur];
        }
        if (res==0){
            dp[res][cur]=cur==end?1:0;
        }else if (cur==1){//右移
            dp[res][cur]= process(n,end,cur+1,res-1, dp);
        }else if (cur==n){//左移
            dp[res][cur]= process(n,end,cur-1,res-1, dp);
        }else {//右移+左移
            dp[res][cur]=process(n, end, cur - 1, res - 1, dp) + process(n, end, cur + 1, res - 1, dp);
        }
        return dp[res][cur];
    }

    public static void main(String[] args) {
        System.out.println(robotWalk(10, 5, 8, 5));
    }
}

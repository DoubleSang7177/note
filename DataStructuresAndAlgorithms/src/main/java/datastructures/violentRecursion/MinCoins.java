package datastructures.violentRecursion;
/*
* 最少硬币
* */
public class MinCoins {
    public static int minCoins(int target, int[] arr){
        int[][] dp=new int[target+1][arr.length+1];
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp[0].length; j++) {
                dp[i][j]=-1;
            }
        }
        return process(target,0,arr,dp);
    }

    public static int process(int res, int cur, int[] arr, int[][] dp){
        if (dp[res][cur]!=-1){
            return dp[res][cur];
        }
        if (cur==arr.length){
            dp[res][cur]=0;
            return  dp[res][cur];
        }
        if (arr[cur]==res){
            dp[res][cur]=1;
            return dp[res][cur];
        }
        if (arr[cur]>res){
            dp[res][cur]=process(res,cur+1,arr, dp);
            return dp[res][cur];
        }
        int x= process(res-arr[cur],cur+1,arr, dp);
        int y= process(res,cur+1,arr, dp);
        if (x==0 && y==0){
            dp[res][cur]=0;
        }else{
            if (y==0){//y无效
                dp[res][cur]=1+x;
                return dp[res][cur];
            }
            if(x == 0){//x无效
                dp[res][cur]=y;
                return dp[res][cur];
            }
            dp[res][cur]=Math.min(x+1, y);//都有效，选择小的
        }
        return dp[res][cur];
    }

    public static int minCoins2(int target, int[] arr){
        int[][] dp=new int[target+1][arr.length+1];
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp[0].length; j++) {
                dp[i][j]=-2;
            }
        }
        return process2(target,0,arr,dp);
    }
    public static int process2(int res, int cur, int[] arr, int[][] dp){
        if (res<0){
            return -1;
        }
        if (dp[res][cur]!=-2){
            return dp[res][cur];
        }
        if (res==0){
            dp[res][cur]=0;
        }else if (cur==arr.length){
            dp[res][cur]=-1;
        }else{
            int x= process2(res-arr[cur],cur+1,arr, dp);
            int y= process2(res,cur+1,arr, dp);
            if (x==-1 && y==-1){
                dp[res][cur]=-1;
            }else{
                if (y==-1){//y无效
                    dp[res][cur]=1+x;
                }else if(x == -1){//x无效
                    dp[res][cur]=y;
                }else{
                    dp[res][cur]=Math.min(x+1, y);//都有效，选择小的
                }
            }
        }
        return  dp[res][cur];
    }

    public static void main(String[] args) {
        int[] arr={2,2,2,1,5,6,4,3,7,1,5,1,14,1};
        System.out.println(minCoins(15, arr));
        System.out.println(minCoins2(15,arr));

    }
}

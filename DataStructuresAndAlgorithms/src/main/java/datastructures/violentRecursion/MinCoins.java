package datastructures.violentRecursion;

/*
* 最少硬币
* */
public class MinCoins {
    /*
    * 每种硬币可以使用多次
    * 有更好的方案！！逆天没看懂
    * */
    public static int coinChange(int[] coins, int amount) {
        int[][] dp=new int[coins.length+1][amount+1];
        for (int i = 0; i <= coins.length; i++) {
            for (int j = 0; j <= amount; j++) {
                dp[i][j]=-2;
            }
        }
        return y(coins,0,amount,dp);
    }
    public static int dynamic(int[] coins, int rest){
        int n=coins.length;
        int[][] dp=new int[n+1][rest+1];
        for (int i = 1; i <= rest; i++) {
            dp[n][i]=-1;
        }
        for (int h = n-1; h >= 0; h--) {
            for (int col = 1; col <= rest; col++) {
                int min=Integer.MAX_VALUE;
                boolean flag=false;
                for (int count = 0; count*coins[h] <=col ; count++) {
                    int result=getValue(h+1,col-count*coins[h],dp,n,rest);
                    if (result!=-1 && (result+count)<min){
                        min=(result+count);
                        flag=true;
                    }
                }
                dp[h][col]=flag?min:-1;
            }
        }
        return dp[0][rest];
    }
    public static int dynamic2(int[] coins, int amount){
        int n=coins.length;
        int[][] dp=new int[n+1][amount +1];
        for (int i = 1; i <= amount; i++) {
            dp[n][i]=-1;
        }
        for (int h = n-1; h >= 0; h--) {
            for (int col = 1; col <= amount; col++) {
                int x=getValue(h+1,col,dp,n, amount);
                dp[h][col]=x;
                if (col-coins[h]>=0){
                    int y=getValue(h,col-coins[h],dp,n, amount);
                    if (x==-1 && y==-1){
                        dp[h][col]=-1;
                    }else if (y==-1){
                        dp[h][col]=x;
                    }else if(x==-1){
                        dp[h][col]=y+1;
                    }else{
                        dp[h][col]=Math.min(x,y+1);
                    }
                }
            }
        }
        return dp[0][amount];
    }

    private static int getValue(int h, int col, int[][] dp, int n, int rest) {
        if (h<0 || h>n || col<0 || col>rest){
            return -1;
        }
        return dp[h][col];
    }

    public static int y(int[] coins, int cur, int rest, int[][] dp){
        if (dp[cur][rest]!=-2){
            return dp[cur][rest];
        }
        if (cur==coins.length){
            dp[cur][rest]=rest==0?0:-1;//有效or无效
        }else if (rest==0){
            dp[cur][rest]=0;//有效
        }else if (coins[cur]==rest){
            dp[cur][rest]=1;//直接返回1
        }else if (coins[cur]>rest){
            dp[cur][rest]=y(coins,cur+1,rest, dp);//跳过当前位置
        }else{
            int min= Integer.MAX_VALUE;
            boolean flag=false;
            for (int count = 0; count*coins[cur] <= rest; count++) {
                //res右有3种可能 0 说明当前count有效 -1说明当前count不可取 具体数字说明说明当前count有效
                int res=y(coins,cur+1,rest-count*coins[cur], dp);
                if (res!=-1 && (res+count)<min){
                    min=res+count;
                    flag=true;//只要有一个有效则标记
                }
            }
            dp[cur][rest]=flag?min:-1;
        }
        return dp[cur][rest];
    }

    /*
    * 每种硬币只能使用一次的情况
    * */
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
//        int[] arr={2,2,2,1,5,6,4,3,7,1,5,1,14,1};
//        System.out.println(minCoins(15, arr));
//        System.out.println(minCoins2(15,arr));
//        System.out.println(coinChange(new int[]{1, 2, 5},11));
        System.out.println(dynamic2(new int[]{23,1,2,3,1},0));
        int testTime=10000;
        int len=10;
        int max=10;
        for (int i = 0; i < testTime; i++) {
            int[] array = generateRandomArray(len, max);
            int aim = (int) (Math.random() * 3 * max) + max;
            if (coinChange(array,aim)!=dynamic(array,aim)||dynamic(array,aim)!=dynamic2(array,aim)){
                System.out.println("oops!");
                break;
            }

        }
    }
    public static int[] generateRandomArray(int len,int max){
        int[] array = new int[(int) (Math.random() * len) + 1];
        for (int i = 0; i < array.length; i++) {
            array[i]= (int) (Math.random()*max)+1;
        }
        return array;
    }
}

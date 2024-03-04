package datastructures.dynamic;

/*
* Bob游戏
* */
public class BobDie {

    public static String bob(int m,int n,int a,int b,int step){
        long live = process(m, n, a, b, step);
        long all= (long) Math.pow(4,step);
//        long gcd=gcd(live,all);//最大公约数
//        return String.valueOf((live/gcd)+"/"+(all/gcd));//这个还没实现
        return String.valueOf(live/all);//返回0
    }
    public static int process(int m,int n,int a,int b,int step){
        if (a<0 || a==m || b<0 || b==n){
            return 0;
        }
        if (step==0){
            return 1;
        }
        return   process(m,n,a-1,b,step-1)
                +process(m,n,a+1,b,step-1)
                +process(m,n,a,b+1,step-1)
                +process(m,n,a,b-1,step-1);
    }

    /*
    * 动态规划
    * */
    public static int processDynamic(int m,int n,int a,int b,int step){
        if (a<0 || a==m || b<0 || b==n){
            return 0;
        }
        int[][][] dp=new int[m][n][step+1];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                dp[i][j][0]=1;
            }
        }
        for (int h = 1; h <= step; h++) {
            for (int row = 0; row < m; row++) {
                for (int col = 0; col < n; col++) {
                    dp[row][col][h]= getValue(row+1,col,h-1,dp,m,n)
                            +getValue(row-1,col,h-1,dp,m,n)
                            +getValue(row,col+1,h-1,dp,m,n)
                            +getValue(row,col-1,h-1,dp,m,n);
                }
            }
        }
        return dp[a][b][step];
    }

    private static int getValue(int row, int col, int h, int[][][] dp, int m, int n) {
        if (row<0 || row==m || col<0 || col==n){
            return 0;
        }
        return dp[row][col][h];
    }

    public static void main(String[] args) {
        System.out.println(process(9, 9, 3, 5, 10));
        System.out.println(processDynamic(9, 9, 3, 5, 10));
        System.out.println(bob(9, 9, 3, 5, 10));

    }
}

package datastructures.violentRecursion;
/*
* 一个矩阵中只有0和1两种值，每个位置都可以和自己的上下左右四个
* 位置相连，如果右一片1连在一起，这个部分就叫做一个岛，求一个矩阵
* 中有多少个岛
* */
public class CountIsland {
    public static int countIsland(int[][] arr){
        if (arr==null||arr[0]==null){
            return 0;
        }
        return process(0,arr);
    }
    public static int process(int i,int[][] arr){
        if (i==arr.length){
            return 0;
        }
        int res=0;
        for (int j = 0; j < arr[0].length; j++) {
            if (arr[i][j]==1) {
//                infection(i,j, arr.length, arr);
                infection2(i,j,arr.length,arr[0].length,arr);
                res+=1;
            }
        }
        return res+process(i+1,arr);
    }

    /*
    * 太繁琐了。。
    * */
    private static void infection(int i, int j,int n, int[][] arr) {
        arr[i][j]=2;
        //左
        if ((j-1)>=0&&arr[i][j-1]==1){
            infection(i,j-1,n , arr);
        }
        //右
        if ((j+1)<arr[0].length&&arr[i][j+1]==1){
            infection(i,j+1, n, arr);
        }
        //上
        if ((i-1)>=0&&arr[i-1][j]==1){
            infection(i-1,j, n, arr);
        }
        //下
        if ((i+1)<n&&arr[i+1][j]==1){
            infection(i+1,j,n , arr);
        }
    }

    public static int countIsland2(int[][] arr){
        if (arr==null || arr[0]==null){
            return 0;
        }
        int n=arr.length;
        int m=arr[0].length;
        int res=0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (arr[i][j]==1){
                    res+=1;
                    infection2(i,j,n,m,arr);
                }
            }
        }
        return res;
    }
    private static void infection2(int i, int j,int n,int m, int[][] arr){
        if (i<0 || i>=n || j<0 || j>=m || arr[i][j]!=1){
            return;
        }
        arr[i][j]=2;
        infection2(i,j-1,n,m,arr);//左
        infection2(i,j+1,n,m,arr);//右
        infection2(i-1,j,n,m,arr);//上
        infection2(i+1,j,n,m,arr);//下
    }

    public static void main(String[] args) {
        int[][] arr1= new int[][]{
                {0,1,0,1,0,1},
                {1,1,1,0,0,1},
                {1,0,0,1,1,1},
                {0,0,1,0,0,0}
        };
        int[][] arr2= new int[][]{
                {0,1,0,1,0,1},
                {1,1,1,0,0,1},
                {1,0,0,1,1,1},
                {0,0,1,0,0,0}
        };
        System.out.println(countIsland(arr1));
        System.out.println(countIsland2(arr2));
    }
}

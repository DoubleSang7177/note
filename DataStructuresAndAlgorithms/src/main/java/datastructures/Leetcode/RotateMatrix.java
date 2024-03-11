package datastructures.Leetcode;
/*
* 使用有限几个变量
* 将正方形矩阵顺时针转动90度
* */
public class RotateMatrix {
    public static void rotate(int[][] matrix){
        int x1 =0;
        int y1=0;
        int x2 =matrix.length-1;
        int y2=matrix[0].length-1;
        while (x1 < x2){
            process(x1++,y1++,x2--,y2--,matrix);
        }
    }
    public static void process(int x1,int y1,int x2,int y2,int[][] matrix){
        int temp;
        // y2-y1组
        for (int i = 0; i < y2 - y1; i++) {
            temp=matrix[x1][y1+i];
            matrix[x1][y1+i]=matrix[x2-i][y1];//t1=t4
            matrix[x2-i][y1]=matrix[x2][y2-i];;//t4=t3
            matrix[x2][y2-i]=matrix[x1+i][y2];//t3=t2
            matrix[x1+i][y2]=temp;//t2=t1
//            int t1=matrix[x1][y1+i];
//            int t2=matrix[x1+i][y2];
//            int t3=matrix[x2][y2-i];
//            int t4=matrix[x2-i][y1];
        }
    }
}

package datastructures.Leetcode;
/*
* zigzag的方式打印矩阵
* */
public class ZigZag {
    public static void zigzagPrintMatrix(int[][] matrix){
        int ar=0;
        int ac=0;
        int br=0;
        int bc=0;
        int rowEnd=matrix.length-1;
        int colEnd=matrix[0].length-1;
        boolean flag=false;
        while (ar!=rowEnd+1){
            printLevel(ar,ac,br,bc,matrix,flag);
            ar=ac==colEnd? ar+1:ar;
            ac=ac==colEnd? ac:ac+1;
            br=br==rowEnd? br:br+1;
            bc=br==rowEnd? bc+1:bc;
            flag=!flag;
        }
    }
    public static void printLevel(int ar,int ac,int br,int bc,int[][] matrix,boolean flag){
        if (flag){//从下到斜向上
            int x=br;
            int y=bc;
            while (br!=ar-1){
                System.out.println(matrix[br--][bc++]);
            }
        }else{//从上到斜下
            while (ar!=br+1){
                System.out.println(matrix[ar++][ac--]);
            }
        }
    }
}

package datastructures.Leetcode;
import java.util.ArrayList;
import java.util.List;
/*
 * 螺旋方式打印矩阵
 * */
public class LeetCode54 {
    public static List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> list = new ArrayList<>();
        process(matrix, 0, 0, matrix.length - 1, matrix[0].length - 1, list);
        return list;
    }

    public static void process(int[][] matrix, int x1, int y1, int x2, int y2, List<Integer> list) {
        if (x1 > x2 || y1 > y2) {
            return;
        }
        if (x1 == x2) {
            for (int j = y1; j <= y2; j++) {
                list.add(matrix[x1][j]);
            }
        } else if (y1 == y2) {
            for (int i = x1; i <= x2; i++) {
                list.add(matrix[i][y1]);
            }
        } else {
            //标准情况
            int row=x1;
            int col=y1;
            while (col!=y2) {
                list.add(matrix[row][col++]);
            }
            while (row!=x2) {
                list.add(matrix[row++][col]);
            }
            while (col!=y1) {
                list.add(matrix[row][col--]);
            }
            while (row!=x1) {
                list.add(matrix[row--][col]);
            }
            process(matrix, x1 + 1, y1 + 1, x2 - 1, y2 - 1, list);
        }
    }
    public static void main(String[] args) {
        int[][] matrix = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12}};
        System.out.println(spiralOrder(matrix));//1,2,3,4,8,12,11,10,9,5,6,7
    }
}

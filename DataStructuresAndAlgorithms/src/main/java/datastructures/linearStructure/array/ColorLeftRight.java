package datastructures.linearStructure.array;

/*
 * 预处理问题
 * 最后要左侧全为绿色G 右侧全为红色L
 * 返回最少的染色次数
 * */
public class ColorLeftRight {
    /*
    * O(N^2)
    * */
    public static int minPaintCount(String str) {
        char[] arr = str.toCharArray();
        int n = arr.length;
        int res = Integer.MAX_VALUE;
        for (int l = 0; l <= n; l++) {
            int num;
            if (l == 0) {//左侧长度为0
                num = getNum(arr, 0, n - 1);//arr[0...n-1]上的G的数量
            } else if (l == n) {//右侧长度为0
                num = n - getNum(arr, 0, n - 1);//arr[0...n-1]上的R的数量
            } else {
                num = (l + 1 - getNum(arr, 0, l)) + getNum(arr, l + 1, n - 1);//arr[0...l]上的R的数量+arr[l+1...n-1]上的G的数量
            }
            res = Math.min(res, num);
        }
        return res;
    }
    private static int getNum(char[] arr, int i, int j) {
        int numG = 0;
        for (int k = i; k <= j; k++) {
            if (arr[k] == 'G') {
                numG++;
            }
        }
        return numG;
    }
    /*
    * 所谓空间换时间
    * O(N)
    * */
    public static int minPaintCount2(String str) {
        char[] arr = str.toCharArray();
        int n = arr.length;
        int res = Integer.MAX_VALUE;
        int[] left = new int[n];
        int[] right = new int[n];
        left[0] = arr[0] == 'R' ? 1 : 0;
        right[n - 1] = arr[n - 1] == 'G' ? 1 : 0;
        for (int i = 1; i < left.length; i++) {
            left[i] = arr[i] == 'R'?1 + left[i - 1]: left[i - 1];
        }
        for (int i = n - 2; i >= 0; i--) {
            right[i] = arr[i] == 'G'?1 + right[i + 1]:right[i + 1];
        }
        for (int l = 0; l <= n; l++) {
            int num;
            if (l == 0) {//左侧长度为0
                num = right[0];//arr[0...n-1]上的G的数量
            } else if (l == n) {//右侧长度为0
                num = left[n-1];//arr[0...n-1]上的R的数量
            } else {
                num = left[l-1]+right[l];//arr[0...l]上的R的数量+arr[l+1...n-1]上的G的数量
            }
            res = Math.min(res, num);
        }
        return res;
    }


    public static void main(String[] args) {
        System.out.println(minPaintCount("RRRGGRRRRGRGGGRGRRGRGRGGGGRRRRRGGGRGR"));
        System.out.println(minPaintCount2("RRRGGRRRRGRGGGRGRRGRGRGGGGRRRRRGGGRGR"));
    }
}

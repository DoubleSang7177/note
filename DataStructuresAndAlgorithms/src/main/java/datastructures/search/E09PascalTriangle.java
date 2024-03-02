package datastructures.search;

public class E09PascalTriangle {
    public static int element(int i,int j){
        if(i<0||j<0){
            return -1;
        }
        if(j==0||i==j){
            return 1;
        }
        return element(i-1,j-1)+element(i-1,j);
    }
    public static void pascalTriangle(int n){
        for (int i = 0; i < n; i++) {
            printSpace(n,i);
            for (int j = 0; j <= i; j++) {
                System.out.printf("%-4d",element(i,j));
            }
            System.out.println();
        }
    }
    static void printSpace(int n,int i){
        int num=(n-1-i)*2;
        for (int j = 0; j < num; j++) {
            System.out.print(" ");
        }
    }
}

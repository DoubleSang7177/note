package datastructures.Leetcode;

import java.util.ArrayList;
import java.util.List;
/*
* N皇后问题
* */
class Solution {
    public static List<List<String>> solveNQueens(int n) {
        List<List<String>> res=new ArrayList<>();
        process(n, 0, new int[n], new ArrayList<>(),res);
        return res;
    }

    private static boolean isValid(int i, int j, int[] arr) {
        for (int k = 0; k < i; k++) {
            if (arr[k]==j || (Math.abs(i-k)==Math.abs(j-arr[k]))){
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        List<List<String>> res = solveNQueens(6);
        System.out.println(res);
        for (List<String> re : res) {
            System.out.println(re);
        }
    }


    public static boolean process(int n,int i,int[] arr,List<String> list,List<List<String>> res){
        if (i==n){
            res.add(list);
            return true;
        }
        List<String> copy=copy(list);
        for (int j = 0; j < n; j++) {
            if (isValid(i,j,arr)){
                arr[i]=j;
                String str= getString(n,j);
                copy.add(str);
                if (!process(n,i+1,arr,copy,res)) {
                    copy.remove(str);
                }
            }
        }
        return false;
    }

    private static String getString(int n,int j){
        StringBuilder sb=new StringBuilder();
        for (int x = 0; x < n; x++) {
            if(x==j){
                sb.append("Q");
            }else {
                sb.append(".");
            }
        }
        return sb.toString();
    }
    private static List<String> copy(List<String> list) {
        List<String> copy=new ArrayList<>();
        copy.addAll(list);
        return copy;
    }
}
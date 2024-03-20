package datastructures.Leetcode;

/**
 * 不同得二叉搜索树 动态规划
 * G(n): 长度为 nnn 的序列能构成的不同二叉搜索树的个数。
 * F(i,n): 以 i 为根、序列长度为 n的不同二叉搜索树个数 (1≤i≤n)
 * G(n)=i=∑F(i,n)i从1到n的和
 * F(i,n)=G(i-1)*(n-i)
 * 于是得到G(n)=∑G(i-1)*(n-i)--i从1到n
  */
public class LeetCode96 {
    /*public int numTrees(int n) {
        if (n == 0) {
            return 0;
        }
        return process(1,n);
    }

    private int process(int start, int end) {
        if (start>end){
            return 1;
        }
        int count=0;
        for (int i = start; i <= end; i++) {
            int left = process(start, i - 1);
            int right = process(i + 1, end);
            count+=left*right;
        }
        return count;
    }*/
    public int numTrees(int n) {
        int[] G=new int[n+1];
        G[0]=1;
        G[1]=1;
        for (int i = 2; i <= n; i++) {
            for (int j = 1; j <= i; j++) {
                G[i]+=G[j-1]*G[i-j];
            }
        }
        return G[n];
    }
}

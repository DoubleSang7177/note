package datastructures.Leetcode;
/*
* 678. 有效的括号字符串
* 给你一个只包含三种字符的字符串，支持的字符类型分别是 '('、')' 和 '*'。
* 请你检验这个字符串是否为有效字符串，如果是有效字符串返回 true 。
* 有效字符串符合如下规则：
    任何左括号 '(' 必须有相应的右括号 ')'。
    任何右括号 ')' 必须有相应的左括号 '(' 。
    左括号 '(' 必须在对应的右括号之前 ')'。
    '*' 可以被视为单个右括号 ')' ，或单个左括号 '(' ，或一个空字符串。
    一个空字符串也被视为有效字符串。
*
* */
public class LeetCode678 {
    /*
    * 动态规划
    * */
    public static boolean checkValidString(String s) {
        int n= s.length();
        if(n==1){
            return s.charAt(0)=='*';
        }
        boolean[][] dp=new boolean[n][n];
        for (int i = 0; i < n; i++) {
            if (s.charAt(i)=='*'){
                dp[i][i]=true;
            }
        }
        for (int i = 1; i < n; i++) {
            dp[i-1][i]=(s.charAt(i-1) == '*'||s.charAt(i-1) == '(')
                    && (s.charAt(i) == '*' || s.charAt(i) == ')');
        }
        for (int length = 2; length < n; length++) {
            int row=0;
            int col=length;
            while (row<n && col<n){
                if ((s.charAt(row) == '*'||s.charAt(row) == '(')
                        && (s.charAt(col) == '*' || s.charAt(col) == ')')){
                    dp[row][col]=dp[row+1][col-1];
                }
                for (int k=row;k<col && !dp[row][col];k++){
                    dp[row][col]= dp[row][k] && dp[k+1][col];
                }
                row++;
                col++;
            }
        }
        return dp[0][n-1];
    }


    public static void main(String[] args) {
        System.out.println(checkValidString("((((()(()()()*()(((((*)()*(**(())))))(())()())(((())())())))))))(((((())*)))()))(()((*()*(*)))(*)()"));
    }
}

package datastructures.Leetcode;

public class LeetCode125 {
    public static boolean isPalindrome(String s) {
        s=s.toLowerCase();
        s = s.replaceAll("[^a-z0-9]", "");
        char[] arr = s.toCharArray();
//        int i=0;
       /* while (arr.length-1-i>=i) {
            if(arr[i]!=arr[arr.length-1-i]){
                return false;
            }else{
                i++;
            }
        }*/
        for (int i=0,j=arr.length-1;i<=j;i++,j--){
            if(arr[i]!=arr[arr.length-1-i])
                return false;
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println(isPalindrome("A man, a plan, a canal: Panama"));
    }
}

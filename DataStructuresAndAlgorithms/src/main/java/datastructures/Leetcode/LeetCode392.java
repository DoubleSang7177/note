package datastructures.Leetcode;

public class LeetCode392 {
    public static boolean isSubsequence(String s, String t) {
        for (int i = 0; i < s.length(); i++) {
            String c = s.substring(i, i + 1);
            if(!t.contains(c)){
                return false;
            }else{
                t = t.substring(t.indexOf(c)+1);
            }
        }
        return true;
    }

    public static boolean isSubsequence2(String s, String t) {
        int i=0,j=0;
        while (i<s.length() && j<t.length()){
            if(s.charAt(i)==t.charAt(j)){
                i++;
            }
            j++;
        }
        return i==s.length();
    }
    public static void main(String[] args) {
        System.out.println(isSubsequence2("aaaaaa", "bbaaaa"));
    }
}

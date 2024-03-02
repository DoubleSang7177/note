package datastructures;

/*
* KMP算法
* */
public class KMP {
    public static int getIndexOf(String s1,String s2){
        if (s1==null || s2==null || s2.length()<1 || s2.length()>s1.length()){
            return -1;
        }
        int i1=0;
        int i2=0;
        int[] next= getNextArray(s2.toCharArray());//O(M)
        //O(N)
        while (i1<s1.length()&&i2<s2.length()){
            if (s1.charAt(i1)==s2.charAt(i2)){
                i1++;
                i2++;
            } else if (next[i2] == -1) {
                i1++;
            }else{
                i2=next[i2];
            }
        }
        return i2==s2.length()?i1-i2:-1;
    }

    private static int[] getNextArray(char[] chars) {
        if (chars.length==1){
            return new int[]{-1};
        }
        int[] next=new int[chars.length];
        next[0]=-1;
        next[1]=0;
        int i=2;
        int cn=0;//int cn=next[1];
        while (i<next.length){
            if (chars[i-1]==chars[cn]){
                next[i++]=++cn;
            }else if(cn>0){
                cn=next[cn];
            }else{
                next[i++]=0;
            }
        }
        return next;
    }
}

package datastructures.violentRecursion;
/*
* 规定1对应A，2对应B，3和C对应以此类推
* 那么一个数字字符串"111",就可以转化成"AAA","KA","AK"
* 返回有多少种转化结果
* * */
public class ConvertToLetterString {

    public static void function(String str){
        char[] chars = str.toCharArray();
        StringBuilder sb=new StringBuilder();
        System.out.println("总共有"+s(chars, 0, sb)+"种组合方式");
    }
    public static int s(char[] str,int i,StringBuilder sb){
        if (i==str.length){
            System.out.println(sb.toString());
            return 1;
        }
        if (str[i]=='0'){
            sb.delete(0,sb.length());
            return 0;
        }
        if (str[i]=='1'){
            StringBuilder sb1=new StringBuilder();
            sb1.append(sb.toString());
            sb1.append(getChar(str[i]));//加入str[i]对应的字符
            int res=s(str,i+1,sb1);
            if (i+1<str.length){
                StringBuilder sb2=new StringBuilder();
                sb2.append(sb.toString());
                sb2.append(getAppendChar(str[i],str[i+1]));
                res+=s(str,i+2,sb2);
            }
            return res;
        }
        if (str[i]=='2'){
            StringBuilder sb1=new StringBuilder();
            sb1.append(sb.toString());
            sb1.append(getChar(str[i]));//加入str[i]对应的字符
            int res=s(str,i+1,sb1);
            if (i+1<str.length&&str[i+1]>='0'&&str[i+1]<='6'){
                StringBuilder sb2=new StringBuilder();
                sb2.append(sb.toString());
                sb2.append(getAppendChar(str[i],str[i+1]));//加入str[str[i]+str[i]]对应的字符
                res+=s(str,i+2,sb2);
            }
            return res;
        }
        StringBuilder sb1=new StringBuilder();
        sb1.append(sb.toString());
        sb1.append(getChar(str[i]));//加入str[i]对应的字符
        return s(str,i+1,sb1);
    }

    private static char getAppendChar(char c1, char c2) {
        int i = Character.getNumericValue(c1) * 10 + Character.getNumericValue(c2);
        return (char) ('A'+i-1);
    }

    public static char getChar(char c){
        int num = Character.getNumericValue(c);
        return (char) ('A'+num-1);
    }

    public static void main(String[] args) {
        function("21112331");
    }
}

package datastructures.violentRecursion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

/*
* 暴力递归
* 打印一个字符串的所有子序列，包括空字符串
* */
public class SubsequenceOfString {
    public static void function(String str){

        ArrayList<Character> res = new ArrayList<>();
        char[] chars = str.toCharArray();
        process(0,chars,res);
        process2(chars,0);
    }
    public static ArrayList<String> strings=new ArrayList<>();
    private static void process(int i, char[] chars, ArrayList<Character> res) {
        if (i==chars.length){
            /*if (!res.isEmpty()){
                String str = res.stream().map(Object::toString).collect(Collectors.joining());
                strings.add(str);
            }else{
                strings.add("");
            }*/
            String str = res.stream().map(Object::toString).collect(Collectors.joining());
            strings.add(str);
            return;
        }
//        ArrayList<Character> yes=res;//这是引用啊大傻妞卧槽！！！也会改变res的值
        ArrayList<Character> yes=copy(res);
        yes.add(chars[i]);
        process(i+1,chars,yes);
        ArrayList<Character> no=copy(res);
        process(i+1,chars,no);
    }
    private static ArrayList<Character> copy(ArrayList<Character> res) {
        return new ArrayList<>(res);//一定要new一个新的list，不然就是对原集合的引用，会改变原集合的值
    }

    /*
    * 来到当前位置，要和不要，走两条路
    * 之前的选择形成的结果是str
    * */
    public static void process2(char[] str,int i){
        if (i==str.length){
            System.out.println(Arrays.toString(str));
            return;
        }
        process2(str,i+1);//要当前的字符
        char temp=str[i];
        str[i]=0;
        process2(str,i+1);//不要当前的字符
        str[i]=temp;
    }

    public static void main(String[] args) {
        function("abc");
        for (String string : strings) {
            System.out.println(string);
        }
        System.out.println(strings.size());
    }
}

package datastructures.greedyAlgprithm;

import java.util.Arrays;
import java.util.Comparator;

/*
* 贪心算法
* 按照最小字典序进行排序
* */
public class LowestLexicography {
    public static class StringComparator implements Comparator<String>{

        @Override
        public int compare(String o1, String o2) {
            return (o1+o2).compareTo(o2+o1);
        }
    }
    public static String lowestLexicography(String[] strings){
        if (strings==null || strings.length==0){
            return "";
        }
        StringBuilder result= new StringBuilder();
        Arrays.sort(strings, new StringComparator());
        for (String string : strings) {
            result.append(string);
        }
        return result.toString();
    }
}

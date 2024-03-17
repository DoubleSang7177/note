package datastructures.Leetcode;
import java.util.*;

public class LeetCode692 {
    public static List<String> topKFrequent(String[] words, int k) {
        HashMap<String,Integer> map=new HashMap<>();
        for (String word : words) {
            map.put(word,map.getOrDefault(word,0)+1);
        }
        List<String> res = new ArrayList<>(map.keySet());
        res.sort((String o1, String o2) -> !map.get(o1).equals(map.get(o2)) ? map.get(o2) - map.get(o1) : o1.compareTo(o2));//compareTo()对字符串是按字典序升序排序
        return res.subList(0,k);
    }

    private static int myCompare(String s1,String s2) {
        String small=s1.length()>s2.length()?s2:s1;
        int len=small.length();
        for (int i = 0; i < len; i++) {
            if (s1.charAt(i)!=s2.charAt(i)){
                return (s1.charAt(i)-'a')-(s2.charAt(i)-'a');
            }
        }
        if (small.equals(s1)){
            return -1;//s2排前面
        }else{
            return 1;//s1排前面
        }
    }

    public static void main(String[] args) {
        String[] strs={"i", "love", "leetcode", "i", "love", "coding"};
        System.out.println(topKFrequent(strs, 2));
    }
}

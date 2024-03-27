package datastructures.Leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class LeetCode {
    public static int ladderLength(String beginWord, String endWord, List<String> wordList) {
        if(!wordList.contains(endWord)){
            return 0;
        }
        HashMap<String,List<String>> edgeMap=new HashMap<>();//满足转换条件的边集
        if(!wordList.contains(beginWord)){
            for(int i=0;i<wordList.size();i++){
                if(isValid(beginWord,wordList.get(i))){
                    List<String> curNeighbor= edgeMap.getOrDefault(beginWord,new ArrayList<>());
                    curNeighbor.add(wordList.get(i));
                    edgeMap.put(beginWord,curNeighbor);
                }
            }
        }
        for(int i=0;i<wordList.size();i++){
            String cur=wordList.get(i);
            //wordList = ["hot","dot","dog","lot","log","cog"]
            for(int j=0;j<wordList.size();j++){
                if(isValid(cur,wordList.get(j))){
                    List<String> curNeighbor= edgeMap.getOrDefault(cur,new ArrayList<>());
                    curNeighbor.add(wordList.get(j));
                    edgeMap.put(cur,curNeighbor);
                }
            }
        }

        int[] res=process(edgeMap,beginWord,0,endWord,new  HashSet<>());
        return res[1]==1?res[0]:0;
    }

    private static int[] process(HashMap<String,List<String>> edgeMap, String cur, int ans, String endWord, HashSet<String> path){
        if(cur==endWord){
            return new int[]{ans+1,1};//1表示该路径有效，-1表示无效
        }
        List<String> neighborList=edgeMap.get(cur);

        if(neighborList==null||neighborList.isEmpty()){
            return new int[]{ans+1,-1};
        }
        path.add(cur);
        int res=Integer.MAX_VALUE;
         for(String neighbor:neighborList){
            if(!path.contains(neighbor)){
                int[] arr=process(edgeMap,neighbor,ans+1,endWord, new HashSet<>(path));
                res=Math.min(res,arr[1]==1?arr[0]:res);
            }
//            path.add(neighbor);
        }
        return res==Integer.MAX_VALUE?new int[]{ans+1,-1}:new int[]{res,1};
    }

    private  static boolean isValid(String a,String b){
        int count=0;
        for(int i=0;i<a.length();i++){
            if(a.charAt(i)==b.charAt(i)){
                count++;
            }
        }
        return count==a.length()-1;
    }

    public static void main(String[] arr){
        List<String> wordList = new ArrayList<>();
        wordList.add("hot");
        wordList.add("dot");
        wordList.add("dog");
        wordList.add("lot");
        wordList.add("log");
        wordList.add("cog");
        System.out.println(ladderLength("hit","cog",wordList));
        System.out.println(wordList.indexOf("jsj"));
        
    }
}

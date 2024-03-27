package datastructures.Leetcode;
import java.util.*;
/*
* 解法正确，但是需要优化，会超时！！！
* */
public class LeetCode126 {
    /**
     * 单词接龙2
     * @param beginWord 起始单词
     * @param endWord 结尾单词
     * @param wordList 单词集合
     * @return 结果集
     */
    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        //最终结果
        List<List<String>> ans=new ArrayList<>();
        //用来判断某单词是否在wordList中
        Set<String> wordSet=new HashSet<>(wordList);
        if(wordSet.size()==0|| !wordSet.contains(endWord)){
            return ans;
        }
        //广度优先遍历 队列
        Queue<List<String>> queue=new LinkedList<>();
        List<String> path=new ArrayList<>(Arrays.asList(beginWord));
        queue.add(path);
        //记录访问过的单词
        Set<String> visited=new HashSet<>();
        boolean flag=false;
        //开始广度优先遍历
        while(!queue.isEmpty()&& !flag){
            int size=queue.size();
            Set<String> subVisited=new HashSet<>();
            for(int i=0;i<size;i++){//第i层
                List<String> curPath=queue.poll();
                String curString=curPath.get(curPath.size()-1);
                char[] charArray=curString.toCharArray();
                for(int j=0;j<endWord.length();j++){
                    char preChar=charArray[j];
                    for(char k='a';k<='z';k++){
                        if(k==preChar){
                            continue;
                        }
                        charArray[j]=k;
                        String nextStr=String.valueOf(charArray);
                        if(wordSet.contains(nextStr)&&!visited.contains(nextStr)){
                            List<String> p=new ArrayList<>(curPath);
                            p.add(nextStr);
                            if(nextStr.equals(endWord)){
                                flag=true;
                                ans.add(p);
                            }
                            subVisited.add(nextStr);
                            queue.add(p);
                        }
                    }
                    charArray[j]=preChar;
                }
            }
            visited.addAll(subVisited);
        }
        return ans;
    }
}

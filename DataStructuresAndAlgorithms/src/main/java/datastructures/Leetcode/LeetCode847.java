package datastructures.Leetcode;
import java.util.HashMap;

public class LeetCode847 {
    static HashMap<Integer,Integer> path = new HashMap<>();
    static boolean[][] used;

    public static int shortestPathLength(int[][] graph) {
        used=new boolean[graph.length][graph.length];
        int min = Integer.MAX_VALUE;
         HashMap<Integer,Integer> x = path;
         boolean[][] y=used;
        for (int i = 0; i < graph.length; i++) {
            path.put(i,1);
            min=Math.min(min,process(i,graph,0));
            path.remove(i);
        }
        return min;
    }

    private static int process(int cur, int[][] graph, int count) {
        if (path.size() == graph.length) {
            return count;
        }
        HashMap<Integer,Integer> x = path;
        boolean[][] y=used;
        int min = Integer.MAX_VALUE;
        for (int to : graph[cur]) {
            if (!used[cur][to]) {
                used[cur][to] = true;
                path.put(to,path.getOrDefault(to,0)+1);
                min = Math.min(min, process(to, graph, count + 1));
                if(path.get(to)==1){
                    path.remove(to);
                }else{
                    path.put(to,path.get(to)-1);
                }

            }
        }
        return min;
    }
    public static void main(String[] args){
        shortestPathLength(new int[][]{
                {1,2,3},{0},{0},{0}
        });
    }
}

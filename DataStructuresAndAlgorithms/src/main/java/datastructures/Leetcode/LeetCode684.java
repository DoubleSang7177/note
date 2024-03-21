package datastructures.Leetcode;
/*
* 树可以看成是一个连通且 无环的无向图。
* 给定往一棵n个节点 (节点值1～n) 的树中添加一条边后的图。
* 添加的边的两个顶点包含在 1 到 n中间，且这条附加的边不属
* 于树中已存在的边。图的信息记录于长度为 n 的二维数组 edges，
* edges[i] = [ai, bi]表示图中在 ai 和 bi 之间存在一条边。
* 请找出一条可以删去的边，删除后可使得剩余部分是一个有着 n
*  个节点的树。如果有多个答案，则返回数组edges中最后出现的那个。
* */
public class LeetCode684 {
    public int[] findRedundantConnection(int[][] edges) {
        int n=edges.length;//n个树节点
        int[] parent=new int[n+1];
        for (int i = 1; i <= n; i++) {
            parent[i]=i;
        }
        for (int[] edge : edges) {
            int i = edge[0];
            int j = edge[1];
            if (isSame(parent,i,j)){
                return edge;
            }
            union(parent,i,j);
        }
        return null;
    }

    private boolean isSame(int[] parent, int i, int j) {
        return findTop(parent,i)==findTop(parent,j);
    }


    /**
     * 找到城市i的省会（省会满足parent[i]==i）
     * @param parent 父节点数组
     * @param i 当前城市
     * @return 省会
     */
    private int findTop(int[] parent,int i){
        while (parent[i]!=i){//循环！！！老是容易写成if
            i=parent[i];
        }
        return i;
    }

    /**
     * 合并两个集合
     * @param parent 父节点数组
     * @param i 城市i
     * @param j 城市j
     */
    private void union(int[] parent,int i,int j){
        parent[findTop(parent, i)]=findTop(parent, j);
    }
}

package datastructures.Leetcode;

import java.util.*;
/*
* 有n个城市，其中一些彼此相连，另一些没有相连。如果城市 a 与城市 b 直接相连，
* 且城市b与城市c直接相连，那么城市a与城市c间接相连。
* 省份是一组直接或间接相连的城市，组内不含其他没有相连的城市。
* 给你一个 n x n 的矩阵 isConnected ，其中 isConnected[i][j] = 1 表示第 i 个城市和第 j
* 个城市直接相连，而 isConnected[i][j] = 0 表示二者不直接相连。
* 返回矩阵中 省份 的数量。
* */
public class LeetCode547 {
    /**
     * 统计省份总数
     * @param isConnected 是否直接相连
     * @return 省份总数
     */
    public int findCircleNum2(int[][] isConnected) {
        int n=isConnected.length;//n个城市
        List<Integer> values=new ArrayList<>();
        for (int i = 0; i < n; i++) {
            values.add(i);
        }
        UnionFindSet<Integer> unionFindSet=new UnionFindSet<>(values);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (isConnected[i][j]==1){//直接相连
                    unionFindSet.union(i,j);
                }
            }
        }
        return unionFindSet.sizeMap.size();
    }

    /**
     * 统计省份总数
     * @param isConnected 是否直接相连
     * @return 省份总数
     */
    public int findCircleNum(int[][] isConnected) {
        int n=isConnected.length;//n个城市
        int[] parent=new int[n];//parent[i]代表城市i的父节点
        for (int i = 0; i < n; i++) {
            parent[i]=i;//初始化时父节点指向自己
        }
        for (int i = 0; i < n; i++) {
            for (int j = i+1; j < n; j++) {
                if (isConnected[i][j]==1){
                    union(parent,i,j);
                }
            }
        }
        int provinces=0;
        for (int i = 0; i < parent.length; i++) {
            if (parent[i]==i){
                provinces+=1;
            }
        }
        return provinces;
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

    /**
     * 元素节点类
     * @param <V>
     */
    public static class Element<V>{
        public V value;
        public Element(V value){
            this.value=value;
        }
    }
    /**
     * 并查集类
     * @param <V>
     */
    public static class UnionFindSet<V>{
        public HashMap<V,Element<V>> elementMap;
        public HashMap<Element<V>,Element<V>> fatherMap;
        public HashMap<Element<V>,Integer> sizeMap;

        /**
         * 初始化
         * @param values 集合元素
         */
        public UnionFindSet(List<V> values) {
            this.elementMap = new HashMap<>();
            this.fatherMap = new HashMap<>();
            this.sizeMap = new HashMap<>();
            for (V value : values) {
                Element<V> element = new Element<>(value);
                elementMap.put(value,element);
                fatherMap.put(element, element);
                sizeMap.put(element,1);
            }
        }
        /**
         * 找到element所在集合头部节点
         * @param element element所在集合
         * @return 头部节点
         */
        public Element<V> findTop(Element<V> element){
            Stack<Element<V>> path=new Stack<>();
            path.push(element);
            while (fatherMap.get(element)!=element){
                path.push(element);
                element=fatherMap.get(element);
            }
            while (!path.isEmpty()) {
                fatherMap.put(path.pop(),element);
            }
            return element;
        }

        /**
         * 对外提供的方法
         * 判断两个值是否在一个集合里
         * @param v1 值1
         * @param v2 值2
         * @return true表示在一个集合 false则有可能并查集中不包含目标值也有可能是不在一个集合
         */
        public boolean isSameSet(V v1,V v2){
            if (elementMap.containsKey(v1) && elementMap.containsKey(v2)){
                return findTop(elementMap.get(v1)) == findTop(elementMap.get(v2));
            }
            return false;
        }

        /**
         * 对外提供的方法
         * 合并两个集合
         * @param v1 v1
         * @param v2 v2
         */
        public void union(V v1,V v2){
            if (elementMap.containsKey(v1)&& elementMap.containsKey(v2)){
                if (!isSameSet(v1,v2)){//不在一个集合
                    Element<V> top1 = findTop(elementMap.get(v1));
                    Element<V> top2 = findTop(elementMap.get(v2));
                    Element<V> big= sizeMap.get(top1)>sizeMap.get(top2)?top1:top2;
                    Element<V> small=big==top1?top2:top1;
                    fatherMap.put(small,big);
                    sizeMap.put(big,sizeMap.get(top1)+sizeMap.get(top2));
                    sizeMap.remove(small);
                }
            }
        }
    }
}

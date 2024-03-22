package datastructures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;
/*
* 并查集
* */
public class UnionFindSetDemo {

    /**
     * 元素类
     * @param <V>
     */
    public static class Element<V>{
        public V value;
        public Element(V value) {
            this.value = value;
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
            this.elementMap=new HashMap<>();
            this.fatherMap=new HashMap<>();
            this.sizeMap=new HashMap<>();
            for (V value : values) {
                Element<V> element = new Element<>(value);
                elementMap.put(value,element);
                fatherMap.put(element,element);
                sizeMap.put(element,1);
            }
        }
        /**
         * 对外提供的方法
         * 判断两个值是否在一个集合里
         * @param a 值1
         * @param b 值2
         * @return true表示在一个集合 false反之
         */
        public boolean isSameSet(V a, V b){
            if (elementMap.containsKey(a) && elementMap.containsKey(b)){
                return findHeap(elementMap.get(a)) == findHeap(elementMap.get(b));
            }
            return false;
        }
        /**
         * 合并两个集合
         * @param a v1
         * @param b v2
         */
        public void union(V a,V b){
            if (elementMap.containsKey(a) && elementMap.containsKey(b)){
                if (!isSameSet(a, b)){
                    Element<V> heap1 = findHeap(elementMap.get(a));
                    Element<V> heap2 = findHeap(elementMap.get(b));
                    Element<V> big = sizeMap.get(heap1) > sizeMap.get(heap2) ? heap1 : heap2;
                    Element<V> small = big == heap1 ? heap2 : heap1;
                    fatherMap.put(small,big);
                    sizeMap.put(big,sizeMap.get(heap1)+sizeMap.get(heap2));
                    sizeMap.remove(small);
                }
            }
        }
        /**
         * 找到element所在集合头部节点
         * @param element element所在集合
         * @return 头部节点
         */
        private Element<V> findHeap(Element<V> element) {
            Stack<Element<V>> path =new Stack<>();
            while (element!=fatherMap.get(element)){
                path.push(element);
                element=fatherMap.get(element);
            }
            while (!path.isEmpty()){
                fatherMap.put(path.pop(),element);
            }
            return element;
        }
    }

    public static void main(String[] args) {
        List<Integer> list=new ArrayList<>();
        for (Integer integer : list) {
            System.out.println(integer);
        }
    }
}

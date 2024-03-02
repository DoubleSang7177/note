package datastructures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;
/*
* 并查集
* */
public class UnionFindSetDemo {

    public static class Element<V>{
        public V value;
        public Element(V value) {
            this.value = value;
        }
    }
    public static class UnionFindSet<V>{
        public HashMap<V,Element<V>> elementMap;
        public HashMap<Element<V>,Element<V>> fatherMap;
        public HashMap<Element<V>,Integer> sizeMap;

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

        public boolean isSameSet(V a, V b){
            if (elementMap.containsKey(a) && elementMap.containsKey(b)){
                return findHeap(elementMap.get(a)) == findHeap(elementMap.get(b));
            }
            return false;
        }

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

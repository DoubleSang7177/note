package datastructures.hash;

import java.util.HashMap;
import java.util.Random;
/*
* 设计一个RandomPoll结构
* insert(key)不重复加入key
* erase(key)移除key
* getRandom()等概率随机返回结构中的任何一个key
* 时间复杂度都是key
* */
public class RandomPoll<K>{
    private HashMap<K,Integer> keyIndexMap;
    private HashMap<Integer,K> indexKeyMap;
    private int size;

    public RandomPoll(){
        this.keyIndexMap = new HashMap<>();
        this.indexKeyMap = new HashMap<>();
        this.size = 0;
    }
    public RandomPoll(HashMap<K, Integer> keyIndexMap, HashMap<Integer, K> indexKeyMap, int size) {
        this.keyIndexMap = keyIndexMap;
        this.indexKeyMap = indexKeyMap;
        this.size = size;
    }
    /*
     * 插入key
     * */
    public void insert(K key){
        if (!keyIndexMap.containsKey(key)){
            keyIndexMap.put(key,size);
            indexKeyMap.put(size++,key);
        }
    }
    /*
     * 删除key
     * */
    public void delete(K key){
        //删除前判断key是否在哈希表中
        if (keyIndexMap.containsKey(key)){
            Integer index = keyIndexMap.get(key);
            keyIndexMap.put(indexKeyMap.get(size-1),index);
            indexKeyMap.put(index,indexKeyMap.get(size-1));
            keyIndexMap.remove(key);
            indexKeyMap.remove(--size);
        }
    }
    /*
     * 等概率随机返回结构中的任何一个key
     * */
    public K getRandom(){
        Random r=new Random();
        int randomIndex = r.nextInt(size);
//            int randomIndex = (int)(Math.random() * this.size);//0~size-1
        return indexKeyMap.get(randomIndex);
    }
}

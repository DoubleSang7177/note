package datastructures.violentRecursion;
import datastructures.tree.TreeNode;

import java.util.HashMap;
/*
* 树形dp
* */
public class MaxDistance {
    public static int maxDistance(TreeNode head){
        return process(head).maxDistance;
    }
    public static class Info{
        private int maxDistance;
        private int height;

        public Info(int distance, int height) {
            this.maxDistance = distance;
            this.height = height;
        }
    }
    public static Info process(TreeNode head){
        if (head==null){
            return new Info(0,0);//一定要选好baseCase
        }
        Info leftInfo = process(head.left);
        Info rightInfo = process(head.right);
        int height=Math.max(leftInfo.height,rightInfo.height)+1;
        int maxDistance=Math.max(leftInfo.maxDistance,rightInfo.maxDistance);
        maxDistance=Math.max(maxDistance,leftInfo.height+rightInfo.height+1);
        return new Info(maxDistance,height);
    }

    public static HashMap<String,Integer> processX(TreeNode head){
        HashMap<String,Integer> infoMap=new HashMap<>();
        if (head==null){
            infoMap.put("height",0);
            infoMap.put("distance",0);
            return infoMap;
        }
        HashMap<String, Integer> leftInfo = processX(head.left);
        HashMap<String, Integer> rightInfo = processX(head.right);
        infoMap.put("height", Math.max(leftInfo.get("height"),rightInfo.get("height"))+1);
        int maxDistance=Math.max(leftInfo.get("maxDistance"),rightInfo.get("maxDistance"));
        maxDistance=Math.max(maxDistance,leftInfo.get("height")+rightInfo.get("height")+1);
        infoMap.put("distance",maxDistance);
        return infoMap;
    }
}

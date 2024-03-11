package datastructures.Leetcode;

import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class LeetCode10 {

    public static boolean isMatch(String s, String p) {
        int i = 0;
        int j = 0;
        boolean flag = true;
        while (i < s.length() && j < p.length()) {
            char cur = s.charAt(i);
            char y = p.charAt(j);
            if (y != '.' && y != '*') {
                if (cur != y) {
                    return false;
                }
                i++;
                j++;
                continue;
            }
            if (y == '.') {
                i++;
                j++;
                continue;
            }

            char pre = p.charAt(j - 1);
            if (pre != '.' && pre != cur) {
                if (flag) {
                    return false;
                } else {
                    j++;
                    continue;
                }
            }
            if (flag) {
                flag = false;
            } else {
                if (s.charAt(i) != s.charAt(i - 1)) {
                    return false;
                }
            }
            i++;
        }
        return i == s.length();
    }

    public static boolean isMatch2(String s, String p) {
        int i = 0;
        int j = 0;
        boolean flag = true;
        while (i < s.length() && j < p.length()) {
            char cur = s.charAt(i);
            char y = p.charAt(j);
            //匹配
            if (y == '.' || cur == y) {
                //如果后一位不是*两个指针都往后移动
                if ((j + 1 < p.length()) && p.charAt(j + 1) != '*') {
                    i++;
                    j++;
                    continue;
                }
                //后一位是*
                //0
                j = j + 2;
                continue;
                //1
//                i++;
//                j=j+2;
//                continue;
                //2
            }
            //不匹配且后一位不是* 直接返回
            if ((j + 1 < p.length()) && p.charAt(j + 1) != '*') {
                return false;
            } else {//不匹配但是后一位是*  取0个
                j = j + 2;
            }
        }
        return i == s.length();
    }

    public static boolean process(String s, String p, int i, int j) {
        if (i == s.length() && j == p.length()) {
            return true;
        } else if (i != s.length() && j != p.length()) {
            char cur = s.charAt(i);
            char y = p.charAt(j);
            //匹配
            if (y == '.' || cur == y) {
                //如果后一位不是* 两个指针都往后移动
                if (j + 1 == p.length() || p.charAt(j + 1) != '*') {
                    return process(s, p, ++i, ++j);
                }
                //后一位是*
                if (y == '.') {
                    //这里还是有问题
                    return true;
                }
                int count = 0;
                for (int k = i; k < s.length(); k++) {
                    if (s.charAt(k) != cur) {
                        break;
                    }
                    count++;
                }
                for (int u = 0; u <= count; u++) {
                    if (process(s, p, i + u, j + 2)) {
                        return true;
                    }
                }
                return false;
            }

            //不匹配且后一位不是*或没有后一位 直接返回
            if (j + 1 == p.length() || p.charAt(j + 1) != '*') {
                return false;
            } else {//不匹配但是后一位是*  取0个
                return process(s, p, i, j + 2);
            }
        } else {
            return false;
        }
    }

    public static void main(String[] args) {
        System.out.println(process("aacabc", "c*a*.*", 0, 0));
    }


    public static void test(List<String> manList,List<String> womanList,HashMap<String,Stack<String>> preference) {
        Stack<String> singleMan = new Stack<>();
        singleMan.addAll(manList);
        HashMap<String, String> arrange = new HashMap<>();//婚配
        HashMap<String, HashMap<String, Integer>> womanP = new HashMap<>();
        for (String woman : womanList) {
            Stack<String> p = preference.get(woman);
            HashMap<String,Integer> toP=new HashMap<>();
            int order=p.size();
            while (!p.isEmpty()){
                String man = p.pop();
                toP.put(man,order--);
            }
            womanP.put(woman,toP);
        }
        while (!singleMan.isEmpty()) {
            String pursuer = singleMan.peek();//当前追求者
            Stack<String> target = preference.get(pursuer);//追求目标列表
            String first = target.peek();//选择最喜欢的
            //单身
            if (!arrange.containsKey(first)) {
                arrange.put(first, pursuer);//单身女生不得不接受追求
                singleMan.pop();//从单身男士里移除
            } else {
                //已婚
                String husband = arrange.get(first);//现任
                HashMap<String, Integer> order = womanP.get(first);//被追女生的心动嘉宾排序
                if (order.get(husband) < order.get(pursuer)) {//如果更喜欢当前追求者，选择离婚
                    arrange.put(first, pursuer);//更新婚配关系
                    singleMan.pop();//从单身男士里移除当前追求者
                    singleMan.push(husband);//现任恢复单身！荣获一顶绿帽
                }
            }
            target.pop();//好马不吃回头草，无论追求成功与否，都不会再对该女生进行追求
            preference.put(pursuer, target);
        }

    }
}

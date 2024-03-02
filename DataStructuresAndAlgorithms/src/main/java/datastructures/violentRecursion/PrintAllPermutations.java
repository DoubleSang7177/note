package datastructures.violentRecursion;

import java.util.ArrayList;

/*
 * 暴力递归
 * 打印所有排列
 * */
public class PrintAllPermutations {

    public static void process(char[] str, int i, ArrayList<String> res) {
        if (i == str.length) {
            res.add(String.valueOf(str));
            return;
        }
        boolean[] visit = new boolean[26];//visit[0]=true 说明a参与过交换 visit[1]=true说明b参与过交换
        // 是为了避免i位置和不同位置却相同字符的位置交换，最终产生相同的字符串
        for (int j = i; j < str.length; j++) {
            if (!visit[str[j] - 'a']) {
                visit[str[j] - 'a'] = true;
                swap(str, i, j);
                process(str, i + 1, res);
                swap(str, i, j);
            }
        }
    }

    private static void swap(char[] str, int i, int j) {
        char temp = str[i];
        str[i] = str[j];
        str[j] = temp;
    }

    public static void main(String[] args) {
        String str = "abca";
        char[] chars = str.toCharArray();
        ArrayList<String> list = new ArrayList<>();
        process(chars, 0, list);
        for (String s : list) {
            System.out.println(s);
        }
    }
}

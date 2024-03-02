package datastructures.tree;

/*
 * 前缀树
 * */
public class TrieTree {
    /*
     * 前缀树根节点
     * */
    private TrieNode root;

    /*
     * 前缀树节点
     * */
    public class TrieNode {
        int pass;
        int end;
        TrieNode[] next;

        public TrieNode() {
            this.pass = 0;
            this.end = 0;
            /*
             * 每个节点都可以走向26条路
             * next[0]!=null 说明该节点有走向'a'的路
             * next[1]!=null 说明该节点有走向'b'的路
             * ....
             * next[2]!=null 说明该节点有走向'c'的路
             * */
            this.next = new TrieNode[26];
        }
    }

    public TrieTree() {
        this.root = new TrieNode();
    }

    /*
     * 添加字符串
     * */
    public void insert(String word) {
        if (word == null) {
            return;
        }
        char[] chars = word.toCharArray();
        TrieNode cur = root;
        root.pass++;
        int index = 0;
        for (int i = 0; i < chars.length; i++) {
            index = chars[i] - 'a';
            if (cur.next[index] == null) {
                cur.next[index] = new TrieNode();
            }
            cur = cur.next[index];
            cur.pass++;
        }
        cur.end++;
    }

    /*
     * 删除字符串，删一次就行。。
     * */
    public boolean delete(String word) {
        if (word == null || search(word)==0) {
            return false;
        }
        char[] chars = word.toCharArray();
        TrieNode cur = root;
        root.pass--;
        int index=0;//其实可以不赋值的
        for (char c : chars) {
            index = c - 'a';
            if (cur.next[index] != null && --cur.next[index].pass == 0) {
                cur.next[index] = null;
                return true;
            }
            //上面的判断就算不成立pass值也已经减了
            cur = cur.next[index];
        }
        cur.end--;
        return true;
    }

    /*
     * 查看字符串之前加入过几次
     * */
    public int search(String word) {
        if (word == null) {
            return 0;
        }
        char[] chars = word.toCharArray();
        TrieNode cur = root;
        int index = 0;
        for (char aChar : chars) {
            index = aChar - 'a';
            if (cur.next[index] == null) {
                return 0;
            }
            cur = cur.next[index];
        }
        return cur.end;
    }

    /*
     * 所有加入的字符串中，有几个字符串是以pre为前缀的
     * */
    public int prefixNumber(String pre) {
        if (pre == null) {
            return 0;
        }
        char[] chars = pre.toCharArray();
        TrieNode cur = root;
        int index = 0;
        for (char aChar : chars) {
            index = aChar - 'a';
            if (cur.next[index] == null) {
                return 0;
            }
            cur = cur.next[index];
        }
        return cur.pass;
    }
}

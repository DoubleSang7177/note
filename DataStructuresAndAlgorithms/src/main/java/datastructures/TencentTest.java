package datastructures;
import java.util.*;
/*
* 腾讯笔试
* */
public class TencentTest {
    public static void main2(String[] args) {
        Scanner in=new Scanner(System.in);
        int n=in.nextInt();//节点数
        int m=in.nextInt();//边数
        HashMap<Integer, List<Integer>> map=new HashMap<>();
        char[][] color=new char[n+1][n+1];
        int count=0;
        for (int i = 0; i < m; i++) {
            int x=in.nextInt();
            int y=in.nextInt();
            char flag=in.next().charAt(0);
            color[x][y]=flag;
            color[y][x]=flag;
            List<Integer> listX = map.getOrDefault(x, new ArrayList<>());
            listX.add(y);
            map.put(x, listX);
            List<Integer> listY= map.getOrDefault(y, new ArrayList<>());
            listX.add(x);
            map.put(y, listY);
        }
        for (int i = 1; i <= n; i++) {//遍历每个点
            List<Integer> list = map.get(i);
            boolean flag=true;
            for (Integer next:list){
                if(color[i][next]!='R'){
                    flag=false;
                }
            }
            if(flag){
                count++;
            }
        }
        System.out.println(count);
    }

    public class ListNode {
    int val;
    ListNode next = null;
    public ListNode(int val) {
      this.val = val;
    }
  }

    public boolean[] canSorted (ListNode[] lists) {
        boolean[] ans=new boolean[lists.length];
        int index=0;
        // write code here
        for (ListNode list : lists) {
            int count=0;
            ListNode p=list;
            ListNode last=null;
            while (p!=null && p.next!=null){
                if(p.val>p.next.val){
                    count++;
                }
                p=p.next;
                last=p;
            }
            if(count>1){
                ans[index++]=false;
            }else if (last!=null&&last.val<list.val) {
                    ans[index++]=true;
            }
        }

        return ans;
    }

    public static void main3(String[] args) {
        Scanner in =new Scanner(System.in);
        int n=in.nextInt();
        int m=in.nextInt();
        int[] father=new int[n+1];
        for (int i = 1; i <= n; i++) {
            father[i]=i;//一开始父亲都指向自己
            map.put(i,1);
        }

        int[][] edges=new int[n+1][n+1];
        for (int i = 0; i < m; i++) {
            int n1=in.nextInt();
            int n2=in.nextInt();
            edges[n1][n2]=1;//1表示有边，0表示无边
        }
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                if(edges[i][j]==1){//合并
                    union(i,j,father);
                }
            }
        }
        Collection<Integer> values = map.values();
        Object[] array = values.toArray();
        System.out.println((int)array[0]*(int)array[1]);
    }
    public static HashMap<Integer,Integer> map=new HashMap<>();
    private static void union(int n1,int n2,int[] father){
        if (!isSameSet(n1,n2,father)){
            int top1 = findTop(n1, father);
            int top2=findTop(n2,father);
            father[top1]=top2;
            map.put(top2,map.get(top1)+map.get(top2));
            map.remove(top1);
        }
    }
    private static boolean isSameSet(int n1,int n2,int[] father){
        return findTop(n1,father)==findTop(n2,father);
    }
    private static int findTop(int n,int[] father){
        while (father[n]!=n){
            n=father[n];
        }
        return n;
    }

    public static void main(String[] args) {
        Scanner in=new Scanner(System.in);
        int n=in.nextInt();
        int m=in.nextInt();
        int[][] mix=new int[n][m];
        for (int i = 0; i < n; i++) {
            String str=in.next();
            char[] chars = str.toCharArray();
            for (int j = 0; j < m; j++) {
                mix[i][j]=chars[j];
            }
        }
        int ans=0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                ans+=process(mix,new StringBuilder(),i,j,6);
            }
        }
        System.out.println(ans);
    }

    private static int  process(int[][] mix,StringBuilder sb,int row,int col,int step){
        if (row<0||col<0||row==mix.length||col==mix[0].length){
            return 0;
        }
        sb.append(mix[row][col]);
        if(step==0&&sb.toString().equals("tencent")){
            sb.delete(sb.length()-1,sb.length()-1);
            return 1;
        }
        int count=process(mix,sb,row+1,col,step-1)
                +process(mix,sb,row-1,col,step-1)
                +process(mix,sb,row,col+1,step-1)
                +process(mix,sb,row,col-1,step-1);
        sb.delete(sb.length()-1,sb.length()-1);
        return count;
    }
}

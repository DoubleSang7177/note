package datastructures.greedyAlgprithm;
import java.util.Arrays;
import java.util.Comparator;

/*
* 贪心算法
* */
public class BestArrange {
    /*
    * 会议
    * 有开始时间和结束时间
    * */
    public class Program{
        int start;
        int end;
    }
    /*
    * 会议比较器
    * 按结束时间从小到大排序
    * */
    public class ProgramComparator implements Comparator<Program>{
        @Override
        public int compare(Program o1, Program o2) {
            return o1.end-o2.end;
        }
    }

    /*
    * 什么顺序安排会议才能使被安排的会议数量最大
    * */
    public int bestArrange(Program[] programs, int timePoint) {
        int count = 0;
        //数组排序传比较器直接调Arrays.DSA.sort()就行
        Arrays.sort(programs,new ProgramComparator());
         for (Program cur:programs) {
            if (cur.start >= timePoint) {
                count++;
                timePoint = cur.end;
            }
        }
        return count;
    }

}

package datastructures.violentRecursion;

import java.util.List;
/*
* 派对最大快乐值
* */
public class Happy {

    public static class Employee{
        public int happy;
        List<Employee> subordinates;

        public Employee(int happy, List<Employee> subordinates) {
            this.happy = happy;
            this.subordinates = subordinates;
        }
    }

    public static class Info{
        private int joinMax;
        private int  unJoinMax;

        public Info(int joinMax, int unJoinMax) {
            this.joinMax = joinMax;
            this.unJoinMax = unJoinMax;
        }
    }

    public static int getMaxHappy(Employee employee){
        Info info = process(employee);
        return Math.max(info.joinMax,info.unJoinMax);
    }
    public static Info process(Employee employee){
        if (employee.subordinates==null){//基层员工
            return new Info(employee.happy,0);
        }
        int joinMax=employee.happy;
        int unJoinMax=0;
        for (Employee subordinate : employee.subordinates) {
            Info info = process(subordinate);
            joinMax+=info.unJoinMax;
            unJoinMax+=Math.max(info.joinMax,info.unJoinMax);
        }
        return new Info(joinMax,unJoinMax);
    }

    public static int process2(Employee employee, boolean join){
        if (employee==null){
            return 0;
        }
        //当前员工不能参与活动
        if (!join){
            int res=0;
            for (Employee subordinate : employee.subordinates) {
                res+=Math.max(process2(subordinate,true), process2(subordinate,false));
            }
            return res;
        }
        //当前员工可参与也可不参与活动
        //1.选择参与
        int res1=0;
        res1+=employee.happy;
        for (Employee subordinate : employee.subordinates) {
            res1+= process2(subordinate,false);
        }
        //2.选择不参与
        int res2=0;
        for (Employee subordinate : employee.subordinates) {
            res2+=Math.max(process2(subordinate,true), process2(subordinate,false));
        }
        return Math.max(res1,res2);


    }

}

package mainsi;

import mainsi.entity.MyList;
import mainsi.thread.MyInvocationHandler;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Tests {

    /*
    *💕💕💕
    * */
    @Test
    public void test1() throws IOException {
//        new Calculator().add(10,21);
        Calculator calculator=new Calculator();
        MyInvocationHandler handler = new MyInvocationHandler(calculator);
        Calculate calculate = (Calculate)Proxy.newProxyInstance(calculator.getClass().getClassLoader(), calculator.getClass().getInterfaces(),
                handler);
        calculate.add(10,21);
    }

    /*
    * 通过*.getClass()方法获取类得不到泛型信息？
    * */
    @Test
    public void test2(){
        MyList<Integer> myList1 =new MyList<>();
        MyList<Double> myList2 =new MyList<>();
        System.out.println(myList1.getClass());//class mainsi.entity.MyList
        System.out.println(myList1.getClass());//class mainsi.entity.MyList
        System.out.println(myList2.getClass()==myList1.getClass());//true

    }
}

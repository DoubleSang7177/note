package datastructures.search;
import org.junit.Test;

import java.util.Arrays;

public class ETest {
    @Test
    public void E03(){
        int[] a={-1,1,8,34,67};
        System.out.println(E03BinarySearch.search(a, 678));
    }
    @Test
    public void E04(){
        int[] a={-4,87,35,23,-5,0,1,3,7,34,56};
        E04BubbleSort.sort(a);
        for (int i : a) {
            System.out.print(i+" ");
        }
    }
    @Test
    public void E05(){
        int[] a={-4,87,35,23,-5,0,1,3,7,34,56};
        E05InsertionSort.sort(a);
        for (int i : a) {
            System.out.print(i+" ");
        }
    }
    @Test
    public void E06(){
        System.out.println(E06Fibonacci.fibonacci(13));
    }
    @Test
    public void E07(){
        System.out.println(E07Sum.sum(150));
    }
    @Test
    public void E08(){
        E08HanoiTower.hanoiTower(8);

    }
    @Test
    public void E09(){
        E09PascalTriangle.pascalTriangle(10);

    }
    @Test
    public void test(){
        int[] citations={1,3,1};
        Arrays.sort(citations);
        for (int citation : citations) {
            System.out.println(citation);
        }
        int h=0;
        for (int i = 3; i <= citations.length; ) {
            if(i==citations[citations.length-i]){
                h=citations[citations.length-i];
                break;
            }else {
                i++;
            }
        }
        if(h==0){
            h=citations[0];
        }
        System.out.println("h"+h);
    }

}

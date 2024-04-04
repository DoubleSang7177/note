package datastructures.basicKnowledge;
import java.util.Scanner;

public class ScannerTest {
    public static void main(String[] args){
        Scanner in=new Scanner(System.in);
        System.out.println("请输入名字");
        String name=in.next();
        System.out.println("请输入年龄");
        int age=in.nextInt();
        System.out.println(name+" "+age);
        while(in.hasNext()){
            int x=in.nextInt();
            int y=in.nextInt();
            System.out.println(x+" "+y);
        }
    }

}

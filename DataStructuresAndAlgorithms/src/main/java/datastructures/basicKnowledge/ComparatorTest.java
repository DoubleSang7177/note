package datastructures.basicKnowledge;
import java.util.Arrays;
import java.util.Comparator;
/*
* 比较器
* 自己定义比较器要实现Comparator函数式接口
* */
public class ComparatorTest {

    public static void main(String[] args) {
        Integer[] arr={2,1,32,45,32,1,2,34,54};
//        Arrays.DSA.sort(arr);
       /* Arrays.DSA.sort(arr, new Comparator<Integer>() {
            *//*
            *返回值是负数时，第一个参数排前面
            * 返回值是正数时，第二个参数排前面
            * 返回0时，谁在前面无所谓
            * *//*
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2-o1;
            }
        });*/
        Arrays.sort(arr,(o1,o2)-> o2-o1);
        for (int i : arr) {
            System.out.print(i+" ");
        }

        Student s1 = new Student();
        Student s2 = new Student();
        Student s3 = new Student();
        s1.setId(2);
        s1.setAge(23);
        s1.setName("张三");
        s2.setId(1);
        s2.setAge(21);
        s2.setName("李思思");
        s3.setId(3);
        s3.setAge(18);
        s3.setName("王五");
        Student[] arr2 = new Student[]{s1,s2,s3};
        Arrays.sort(arr2, new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                return (int) (o1.getId()-o2.getId());
            }
        });
        System.out.println();
        for (Student student : arr2) {
            System.out.println(student.getName()+" "+student.getAge()+" "+student.getId());
        }

    }
}

package datastructures.linearStructure.array;

/*
 * 异或运算的使用场景
 * 位运算比算数运算快很多！
 * */
public class XOR {
    /*
     * 查找出现奇数次的数，前提是只有1个出现奇数次的数
     * */
    public static int find(int[] arr) {
        int eor = 0;
        for (int i = 0; i < arr.length; i++) {
            eor ^= arr[i];
        }
        return eor;
    }

    /*
     * 查找出现奇数次的数，前提是只有2个出现奇数次的数a和b
     * */
    public static void find2(int[] arr) {
        int eor = 0;
        for (int i = 0; i < arr.length; i++) {
            eor ^= arr[i];
        }
        //得到eor=a^b
        int onlyOne = 0;
        int rightOne = eor & (~eor + 1);//提取出最右侧的1
        for (int cur : arr) {
            //以（cur & rightOne）等于0或者不等于0进行分组，将a和b分到不同的两个组
            if ((cur & rightOne) != 0) {//onlyOne和其中一组进行异或运算
                onlyOne ^= cur;//onlyOne为ab其中一个数
            }
        }
        eor ^= onlyOne;//eor为ab中不是onyOne的另外一个数
        System.out.println(eor + " " + onlyOne);
    }

    public static void main(String[] args) {
        System.out.println(find(new int[]{1, 2, 3, 4, 1, 4, 3, 2, 3}));
        find2(new int[]{6,1,3,2,2,2,3,6,3,4,4,5,5,1});
    }
}

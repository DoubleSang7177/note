package datastructures.linearStructure.heap;
/*
 * 堆排序
 * 堆结构数组的底层就是完全二叉树
 * 小根堆
 * */
public class Heap {

    /*
     * 堆排序
     * */
    public static void heapSort(int[] arr){
        if (arr==null || arr.length<2){
            return;
        }
        for (int i = 0; i < arr.length; i++) {//O(N)
            heapInsert(arr,i);//O(logN)
        }
        //此时，最大值在0位置
        int heapSize=arr.length;
        swap(arr,0,--heapSize);
        //heapSize-1位置已排好序，大小减一
        while (heapSize>0){//O(N)
            heapify(arr,0,heapSize);//O(logN)
            swap(arr,0,--heapSize);//O(1)
        }
    }
    /*
     * 某个数现在处在index位置，向上继续移动,也就是形成大根堆
     * index节点和父节点进行比较，比父亲大则向上窜
     * 不比父亲大则不做处理，退出
     * */
    public static void heapInsert(int[] arr, int index){
        //(index-1)/2就是index位置节点的父节点位置
        while (arr[index]>arr[(index-1)/2]){//当index为0时，(index-1)/2==0!!!条件不成立，会跳出循环
            //先交换值
            swap(arr,index,(index-1)/2);
            //更新该数的位置
            index=(index-1)/2;
        }
    }
    /*
     * 某个数在index位置上，能否往下移动
     * 向下移动是指，看index位置节点的值是否大于左右孩子中值大的节点
     * 是则交换，并index更新到参与交换的孩子节点位置，继续向下移动
     * 如果不是，则说明当前节点就是该子树上的最大值，不用交换也不用向下移动，直接return
     * */
    public static void heapify(int[] arr,int index,int heapSize){
        int left=index*2+1; //左孩子的下标
        while (left<heapSize){
            int largest=left+1<heapSize&&arr[left+1]>arr[left]?left+1:left;
            largest=arr[largest]>arr[index]?largest:index;
            if (largest==index){
                return;//一定要return不然当largest==index时进入死循环！
            }
            swap(arr,index,largest);
            index=largest;
            left=index*2+1;
        }
    }
    /*
     * 元素交换位置
     * */
    private static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    public static void main(String[] args) {
        int[] arr={2,31,2,35,65,4,3,78};
        for (int i = 0; i < arr.length; i++) {
            heapInsert(arr,i);
        }
        for (int i : arr) {
            System.out.println(i);
        }
        swap(arr,0,arr.length-1);
        for (int i : arr) {
            System.out.print(i+" ");
        }
        System.out.println();
        int size=arr.length-1;
        while (size>0){
            heapify(arr,0,size);
            swap(arr,0,--size);
        }
        for (int i : arr) {
            System.out.print(i+" "); }


    }
}

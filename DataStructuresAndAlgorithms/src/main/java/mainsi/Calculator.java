package mainsi;

public class Calculator implements Calculate{
    @Override
    public int add(int a, int b) {
        System.out.println("a+b="+(a+b));
        return a+b;
    }
}

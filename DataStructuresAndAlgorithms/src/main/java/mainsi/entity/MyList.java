package mainsi.entity;

public class MyList <T extends Number>{
    private T value;

    public void add(T value){
        this.value=value;
    }
}

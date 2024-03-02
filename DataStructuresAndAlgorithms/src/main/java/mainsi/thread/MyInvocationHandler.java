package mainsi.thread;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class MyInvocationHandler implements InvocationHandler {
    private Object target;

    public MyInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("代理对象中进行功能增强1");
        Object res = method.invoke(target, args);
        System.out.println("代理对象中进行功能增强2");
        return res;
    }
}

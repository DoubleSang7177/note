package spring;

import spring.inter.MySpringApplication;
import spring.inter.MySpringBootApplication;

@MySpringBootApplication
public class MyApplication {//MyApplication作为spring容器的配置类，会被解析

    /*
     * 解析内容：类上面加的注解以及类中的注解
     * */
  /*  @Bean
    public TomcatServer tomcatServer() {
        return new TomcatServer();
    }*/

//    @Bean
//    public JettyServer jettyServer(){
//        return new JettyServer();
//    }

    public static void main(String[] args) {
//        SpringApplication.run(MyApplication.class,args);
        MySpringApplication.run(MyApplication.class, args);
    }
}

package spring.server;

public class TomcatServer implements WebServer {
    @Override
    public void run() {
        System.out.println("启动tomcat");
    }
}

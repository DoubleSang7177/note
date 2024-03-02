package spring.server;

public class JettyServer implements WebServer{
    @Override
    public void run() {
        System.out.println("启动jetty");
    }
}

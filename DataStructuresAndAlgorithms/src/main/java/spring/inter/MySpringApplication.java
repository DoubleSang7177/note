package spring.inter;
import org.apache.catalina.*;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.core.StandardEngine;
import org.apache.catalina.core.StandardHost;
import org.apache.catalina.startup.Tomcat;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import spring.MyApplication;
import spring.server.WebServer;

import java.util.Map;


public class MySpringApplication {
    public static void run(Class<MyApplication> myApplicationClass, String[] args) {
        //创建一个spring容器，传给startTomcat()
        AnnotationConfigWebApplicationContext annotationConfigWebApplicationContext
                =new AnnotationConfigWebApplicationContext();
        annotationConfigWebApplicationContext.register(myApplicationClass);//myApplicationClass作为该容器的配置类，注册到spring容器中
        annotationConfigWebApplicationContext.refresh();//解析传给容器的配置类
//        startTomcat(annotationConfigWebApplicationContext);
        WebServer webServer = getWebServer(annotationConfigWebApplicationContext);
        webServer.run();
    }

    public static WebServer getWebServer(WebApplicationContext webApplicationContext){
        Map<String, WebServer> webServers = webApplicationContext.getBeansOfType(WebServer.class);
        if (webServers.isEmpty()){
            throw new NullPointerException();
        }
        if (webServers.size()>1){
            throw new IllegalArgumentException();
        }
        return webServers.values().stream().findFirst().get();

    }
    private static void startTomcat(WebApplicationContext webApplicationContext) {

        Tomcat tomcat=new Tomcat();
        Server server = tomcat.getServer();
        Service service = server.findService("Tomcat");
        Connector connector = new Connector();
        connector.setPort(8081);
        Engine engine = new StandardEngine();
        engine.setDefaultHost("localhost");

        Host host = new StandardHost();
        host.setName("localhost");

        String contextPath="";
        Context context=new StandardContext();
        context.setPath(contextPath);
        context.addLifecycleListener(new Tomcat.FixContextListener());

        host.addChild(context);
        engine.addChild(host);

        service.setContainer(engine);
        service.addConnector(connector);
        //需要传入一个spring容器
        tomcat.addServlet(contextPath,"dispatcher",new DispatcherServlet(webApplicationContext));
        context.addServletMappingDecoded("/*","dispatcher");

        try {
            tomcat.start();
        } catch (LifecycleException e) {
            e.printStackTrace();
        }

    }
}

package spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import spring.server.JettyServer;
import spring.server.TomcatServer;

@Configuration
public class WebServerAutoConfiguration {

    @Bean
    @Conditional(TomcatCondition.class)
    public TomcatServer tomcatServer(){
        return new TomcatServer();
    }

    @Bean
    @Conditional(JettyCondition.class)
    public JettyServer jettyServer(){
        return new JettyServer();
    }
}

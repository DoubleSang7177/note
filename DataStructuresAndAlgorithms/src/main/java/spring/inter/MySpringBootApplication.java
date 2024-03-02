package spring.inter;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import spring.config.WebServerAutoConfiguration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//@SpringBootApplication
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@ComponentScan//默认扫描spring容器的配置类所在的包
@Import(WebServerAutoConfiguration.class)
public @interface MySpringBootApplication {

}

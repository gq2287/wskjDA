package com.wsda.wsda;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@MapperScan(basePackages="com.wsda.project.dao")
@ComponentScan("com.wsda.project.*")
@EnableSwagger2
@SpringBootApplication
@EnableTransactionManagement(proxyTargetClass = true)//添加事务
public class WsdaApplication {

    private static String[] args;
    private static ConfigurableApplicationContext context;


    public static void main(String[] args) {
        WsdaApplication.args = args;
        WsdaApplication.context = SpringApplication.run(WsdaApplication.class, args);
    }
    public static void restart() {
        context.close();
        WsdaApplication.context =SpringApplication.run(WsdaApplication.class, args);

    }
}

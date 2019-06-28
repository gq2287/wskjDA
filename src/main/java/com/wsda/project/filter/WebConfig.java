package com.wsda.project.filter;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 跨域
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        /**
         * 请求常用的三种配置，
         * *代表允许所有，
         * 当时你也可以自定义属性（比如header只能带什么，只能是post方式等等）
         */
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("PUT", "DELETE", "GET", "POST")
                .allowedHeaders("*")
                .exposedHeaders("access-control-allow-headers", "access-control-allow-methods", "access-control-allow" +
                        "-origin", "access-control-max-age", "X-Frame-Options","Authorization","token")
                .allowCredentials(false).maxAge(3600);


    }
}
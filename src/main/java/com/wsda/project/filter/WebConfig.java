package com.wsda.project.filter;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.MultipartConfigElement;

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
                .allowedMethods("*")
                .allowedHeaders("*");
    }

    /**
     * 文件上传配置
     * @return
     */
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //文件最大
        factory.setMaxFileSize("512000KB"); //KB,MB  单个文件500MB
        // 设置总上传数据总大小 1000MB
        factory.setMaxRequestSize("1024000KB");
        return factory.createMultipartConfig();
    }
}
package com.wsda.project.dataSourceConfig;

import com.alibaba.druid.pool.DruidDataSource;
import com.wsda.project.util.StringUtil;
import com.wsda.wsda.WsdaApplication;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @Description:
 * @date 2019
 */
@Configuration
public class DataSourceConfig {
    private static Logger logger = LoggerFactory.getLogger(DataSourceConfig.class);
    @Resource
    private DataSourceConfig dbConfig;
    @Bean(name = "dataSource")
    public DruidDataSource dataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        try {
            PropertiesConfiguration properties = new PropertiesConfiguration( StringUtil.getRealPathByPack());
            String driverClassName = properties.getString("spring.datasource.driverClassName");
            dataSource.setDriverClassName(driverClassName);
            dataSource.setUrl(properties.getString("spring.datasource.url"));
            dataSource.setUsername(properties.getString("spring.datasource.username"));
            dataSource.setPassword(properties.getString("spring.datasource.password"));
        } catch (Exception e) {
            logger.error("获取数据库配置文件失败" + e);
        }
        return dataSource;
    }

    /**
     * 动态修改数据库链接
     */
    public void changeDataSource () {
        Thread restartThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    WsdaApplication.restart();
                } catch (Exception ignored) {
                    System.out.println("启动重启项目"+ignored.getMessage());
                }
            }
        });
        restartThread.setDaemon(false);
        restartThread.start();
    }
}
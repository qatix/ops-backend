package com.quasar.backend.datasources;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 配置多数据源
 *
 * @Author: Logan
 * @Date: 2018/8/30 4:48 PM
 */
@Configuration
public class DynamicDataSourceConfig {

    @Bean(name = "first_db")
    @ConfigurationProperties("spring.datasource.druid.first")
    public DataSource firstDataSource() {
        return DruidDataSourceBuilder.create().build();
    }


    @Bean
    @Primary
    public DynamicDataSource dataSource(@Qualifier("first_db") DataSource firstDataSource) {
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DsEnum.FIRST.getValue(), firstDataSource);
        return new DynamicDataSource(firstDataSource, targetDataSources);
    }
}

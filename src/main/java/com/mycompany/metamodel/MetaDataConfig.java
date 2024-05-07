package com.mycompany.metamodel;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MetaDataConfig {


    @Bean
    public HikariDataSource dataSource() {
        return DataSource.ds;
    }
}

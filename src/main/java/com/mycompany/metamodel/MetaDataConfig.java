package com.mycompany.metamodel;

import com.zaxxer.hikari.HikariDataSource;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.SQLException;

@Configuration
public class MetaDataConfig {


    @Bean
    public HikariDataSource dataSource() {
        return DataSource.ds;
    }

    @Bean
    public Connection getConnection() throws SQLException {
        return DataSource.ds.getConnection();
    }
    @Bean
    public DSLContext getJOOQ() throws SQLException {
        return DSL.using(getConnection(), SQLDialect.POSTGRES);
    }
}

package com.mycompany.metamodel;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DataSource {

    private static HikariConfig config = new HikariConfig();
    public static HikariDataSource ds;

    static {
        config.setJdbcUrl( "jdbc:postgresql://localhost:5432/MetaPos" );
        config.setUsername( "postgres" );
        config.setPassword( "123" );

        ds = new HikariDataSource( config );
    }

    private DataSource() {}


}
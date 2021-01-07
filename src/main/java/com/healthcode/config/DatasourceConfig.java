package com.healthcode.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

import static com.healthcode.common.Constant.HIKARI_CONFIG_LOCATION;

/**
 * @author qianlei
 */
public class DatasourceConfig {
    private static final DataSource dataSource = new HikariDataSource(new HikariConfig(HIKARI_CONFIG_LOCATION));

    public static DataSource getDataSource() {
        return dataSource;
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}

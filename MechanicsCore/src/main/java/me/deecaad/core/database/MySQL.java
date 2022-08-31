package me.deecaad.core.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class MySQL extends HikariBased {

    public MySQL(String hostname, int port, String database, String username, String password) {
        super(DatabaseType.MYSQL);
        HikariConfig config = new HikariConfig();

        config.setPoolName("WMMySQL");
        config.setDriverClassName("com.mysql.jdbc.Driver");
        config.setJdbcUrl("jdbc:mysql://" + hostname + ":" + port + "/" + database);
        config.setUsername(username);
        config.setPassword(password);

        dataSource = new HikariDataSource(config);
    }
}
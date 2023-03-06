package com.github.zigcat;

import com.github.zigcat.ormlite.models.*;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class DatabaseConfiguration {
    private static final String DB_URL = "jdbc:sqlite:C:\\Users\\MI\\Desktop\\study\\OOP\\azsdb.sqlite";
    public static ConnectionSource connectionSource;

    static {
        try{
            connectionSource = new JdbcConnectionSource(DB_URL);
            TableUtils.createTableIfNotExists(connectionSource, User.class);
            TableUtils.createTableIfNotExists(connectionSource, GasStation.class);
            TableUtils.createTableIfNotExists(connectionSource, Column.class);
            TableUtils.createTableIfNotExists(connectionSource, Fuel.class);
            TableUtils.createTableIfNotExists(connectionSource, Check.class);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

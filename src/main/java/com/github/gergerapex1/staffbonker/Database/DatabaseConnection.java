package com.github.gergerapex1.staffbonker.Database;

import com.github.gergerapex1.staffbonker.Config.Config;
import com.github.gergerapex1.staffbonker.Config.DatabaseAuthConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.UUID;

public class DatabaseConnection {
    private Connection connection;
    private Statement statement;
    private final Logger logger = LoggerFactory.getLogger("staffbonker Database");

    public DatabaseConnection() {
        try {
            Class.forName("com.github.gergerapex1.staffbonker.Database.Driver");
            DatabaseAuthConfig config = new Config().getAuth();
            connection = DriverManager.getConnection(
                    config.getURI(), config.getUser(), config.getPassword()
            );
            statement = connection.createStatement();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
    public String getUsername(UUID uuid) {
        try {
            String executeCode = "SELECT name FROM libertybans_latest_names WHERE uuid = UNHEX('" + noslashUUID(uuid) + "')";
            ResultSet resultSet = statement.executeQuery(executeCode);
            while(resultSet.next())
            {
                return resultSet.getString(1);
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String noslashUUID(UUID uuid) {
        return uuid.toString().replace("-", "");
    }
}

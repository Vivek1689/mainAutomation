package utils.db;


import utils.constants.Constants;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDB {
    static Connection conn = null;

    public static Connection createConnection() {
        if (conn == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                conn = DriverManager.getConnection(Constants.dbUrl, Constants.username, Constants.password);
                System.out.println("Create connection local");
            } catch (SQLException e) {
                System.out.println("Connection is not created");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return conn;
    }

    public static void main(String arg[]){
        createConnection();
    }

}

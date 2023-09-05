package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyJDBC {
    public static Connection cnn() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library_management","root","");
        }catch (SQLException e){
            e.printStackTrace();
        }
        return connection;
    }
}

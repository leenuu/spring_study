package study1_1_2_UserDao;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;

public class NUserDao {
    
    public Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection c = DriverManager.getConnection("jdbc:mysql://15.164.225.156/test", "root", "Kangjoon12!");

        return c;
    }
}
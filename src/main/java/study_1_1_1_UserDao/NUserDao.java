package study_1_1_1_UserDao;

import java.sql.DriverManager;
import java.sql.SQLException;

import java.sql.Connection;

public class NUserDao extends UserDao {
    
    public Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection c = DriverManager.getConnection("jdbc:mysql://15.164.225.156/test", "root", "Kangjoon12!");
        System.out.println("connection complete!");
        return c;
    }
}
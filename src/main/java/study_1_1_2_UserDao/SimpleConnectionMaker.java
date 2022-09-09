package study_1_1_2_UserDao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;

public class SimpleConnectionMaker {
   public Connection makeNewConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection c = DriverManager.getConnection("jdbc:mysql://15.164.225.156/test", "root", "Kangjoon12!");
        return c;
   } 
}

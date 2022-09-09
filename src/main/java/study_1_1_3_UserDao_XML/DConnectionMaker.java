package study_1_1_3_UserDao_XML;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;

public class DConnectionMaker implements ConnectionMaker {
    public Connection makeConnection() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
        Connection c = DriverManager.getConnection("jdbc:mysql://15.164.225.156/test", "root", "Kangjoon12!");
        System.out.println("connection complete!");

        return c;
    }
}

package study_1_2_1_test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;

public class DConnectionMaker implements ConnectionMaker {
    public Connection makeConnection() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
        Connection c = DriverManager.getConnection("jdbc:mysql://15.164.225.156/test", "root", "Kangjoon12!");
//        System.out.println("connection complete!");

        return c;
    }
}

    git config --global user.name leenuu
        git config --global user.email kangjoon10@gmail.com
git branch -M main
        git remote add origin https://github.com/leenuu/spring_study.git


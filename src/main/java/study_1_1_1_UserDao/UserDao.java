package study_1_1_1_UserDao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Connection;

public abstract class UserDao {
    public void add(User user) throws ClassNotFoundException, SQLException {
        Connection c = this.getConnection();
        System.out.println("connect");
    	PreparedStatement ps = c.prepareStatement(
            "insert into users(id, name, password) values(?,?,?)");
        
        ps.setString(1, user.getid());
        ps.setString(2, user.getname());
        ps.setString(3, user.getpassword());

        ps.executeUpdate();

        ps.close();
        c.close();
    }

    public User get(String id) throws ClassNotFoundException, SQLException {
        Connection c = this.getConnection();
        PreparedStatement ps = c.prepareStatement(
            "select * from users where id = ?");
        
    	ps.setString(1, id);

        ResultSet rs = ps.executeQuery();
        rs.next();
        User user = new User();
		user.setid(rs.getString("id"));
		user.setname(rs.getString("name"));
		user.setpassword(rs.getString("password"));

		rs.close();
        ps.close();
        c.close();

        System.out.println("get user inform complete!");
        return user;
    }
	public abstract Connection getConnection() throws ClassNotFoundException, SQLException;

}

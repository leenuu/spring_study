package study_1_2_3_test_use_spring;

import org.springframework.dao.EmptyResultDataAccessException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {
    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    public void add(User user) throws SQLException {
        Connection c = dataSource.getConnection();

//        System.out.println("connect");
    	PreparedStatement ps = c.prepareStatement(
            "insert into users(id, name, password) values(?,?,?)");
        
        ps.setString(1, user.getid());
        ps.setString(2, user.getname());
        ps.setString(3, user.getpassword());

        ps.executeUpdate();

        ps.close();
        c.close();
    }

    public User get(String id) throws SQLException {
        Connection c = dataSource.getConnection();
        PreparedStatement ps = c.prepareStatement(
            "select * from users where id = ?");
        
    	ps.setString(1, id);

        ResultSet rs = ps.executeQuery();

        User user = null;
        if (rs.next()) {
            user = new User();
            user.setid((rs.getString("id")));
            user.setname((rs.getString("name")));
            user.setpassword((rs.getString("password")));
        }

		rs.close();
        ps.close();
        c.close();
//		System.out.println("get user inform complete!");
        if (user == null) throw new EmptyResultDataAccessException(1);
        return user;
    }

    public void reset() throws SQLException{
        Connection c = dataSource.getConnection();
        PreparedStatement ps = c.prepareStatement(
                "TRUNCATE users");

        ps.execute();
        ps.close();
        c.close();
    }
    public int getCount() throws SQLException{
        Connection c = dataSource.getConnection();
        PreparedStatement ps = c.prepareStatement(
                "select count(*) from users");
        ResultSet rs = ps.executeQuery();
        rs.next();
        int count = rs.getInt(1);

        rs.close();
        ps.close();
        c.close();

        return count;
    }

}

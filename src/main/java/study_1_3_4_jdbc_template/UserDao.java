package study_1_3_4_jdbc_template;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {
    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;
//    private JdbcContext jdbcContext;

    //    public void setJdbcContext(JdbcContext jdbcContext) {
//        this.jdbcContext = jdbcContext;
//    }
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);

//        this.jdbcContext = new JdbcContext();
//        this.jdbcContext.setDataSource(dataSource);
        this.dataSource = dataSource;
    }

    public void add(final User user) throws SQLException {
//       this.jdbcContext.exequteSql("insert into users(id, name, password) values(?,?,?)", user.getid(), user.getname(), user.getpassword());
//        this.jdbcTemplate.update("insert into users(id, name, password) values(?,?,?)", user.getid(), user.getname(), user.getpassword());
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
    public void reset() throws SQLException {
//        this.jdbcContext.exequteSql("TRUNCATE users");
        this.jdbcTemplate.update("TRUNCATE users");
    }

    private PreparedStatement makeStatement(Connection c) throws SQLException {
        PreparedStatement ps;
        ps = c.prepareStatement("TRUNCATE users");
        return ps;
    }
}
package study_1_3_4_jdbc_template;

<<<<<<< HEAD
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
=======
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
>>>>>>> 12f2624eb0d2b4791e1d16c85a44d28abc0d74b0

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
<<<<<<< HEAD
=======
//       this.jdbcContext.exequteSql("insert into users(id, name, password) values(?,?,?)", user.getid(), user.getname(), user.getpassword());
>>>>>>> 12f2624eb0d2b4791e1d16c85a44d28abc0d74b0
        this.jdbcTemplate.update("insert into users(id, name, password) values(?,?,?)", user.getid(), user.getname(), user.getpassword());
    }

    public User get(String id) throws SQLException {
<<<<<<< HEAD
        return this.jdbcTemplate.queryForObject("select * from users where id = ?",
                new Object[]{id},
                new RowMapper<User>() {
                    public User mapRow(ResultSet resultSet, int i) throws SQLException {
                        User user =new User();
                        user.setid(resultSet.getString("id"));
                        user.setname(resultSet.getString("name"));
                        user.setpassword(resultSet.getString("password"));

                        return user;
                    }
                });
    }
    public int getCount() throws SQLException{
        return this.jdbcTemplate.queryForObject("select count(*) from users", Integer.class);
//        return 0;
    }

    public void reset() throws SQLException {
=======
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
>>>>>>> 12f2624eb0d2b4791e1d16c85a44d28abc0d74b0
        this.jdbcTemplate.update("TRUNCATE users");
    }

    private PreparedStatement makeStatement(Connection c) throws SQLException {
        PreparedStatement ps;
        ps = c.prepareStatement("TRUNCATE users");
        return ps;
    }
}

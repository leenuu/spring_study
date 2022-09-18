package study_1_3_4_jdbc_template;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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
        this.jdbcTemplate.update("insert into users(id, name, password) values(?,?,?)", user.getid(), user.getname(), user.getpassword());
    }

    public User get(String id) throws SQLException {
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
   public List<User> getAll() {
       return this.jdbcTemplate.query("select * from users order by id",
               new RowMapper<User>() {
                   public User mapRow(ResultSet resultSet, int i) throws SQLException {
                       User user = new User();
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
        this.jdbcTemplate.update("TRUNCATE users");
    }

    private PreparedStatement makeStatement(Connection c) throws SQLException {
        PreparedStatement ps;
        ps = c.prepareStatement("TRUNCATE users");
        return ps;
    }
}

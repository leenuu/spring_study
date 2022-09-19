package study_1_4_1_error_exception;

import com.mysql.cj.exceptions.MysqlErrorNumbers;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDao {
    private JdbcTemplate jdbcTemplate;
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    private RowMapper<User> userMappper = new RowMapper<User>() {
        public User mapRow(ResultSet resultSet, int i) throws SQLException {
            User user = new User();
            user.setid(resultSet.getString("id"));
            user.setname(resultSet.getString("name"));
            user.setpassword(resultSet.getString("password"));

            return user;
        }
    };
    public void add(User user) throws DuplicateUserIdException{
        try {
            this.jdbcTemplate.update("insert into users(id, name, password) values(?,?,?)", user.getid(), user.getname(), user.getpassword());
        }
        catch (DuplicateKeyException e) {
            throw new DuplicateUserIdException(e);
        }
    }
//    public void add(final User user) throws DuplicateUserIdException {
//        try {
//                if code can occur SQLException
//        }
//        catch (SQLException e) {
//            if(e.getErrorCode() == MysqlErrorNumbers.ER_DUP_ENTRY)
//                throw new DuplicateUserIdException(e);
//            else
//                throw new RuntimeException(e);
//        }
//    }

    public User get(String id) throws SQLException {
        return this.jdbcTemplate.queryForObject("select * from users where id = ?", new Object[] {id}, this.userMappper);
    }
   public List<User> getAll() {
       return this.jdbcTemplate.query("select * from users order by id", this.userMappper);

   }

    public int getCount() throws SQLException{
        return this.jdbcTemplate.queryForObject("select count(*) from users", Integer.class);
//        return 0;
    }

    public void reset() throws SQLException {
        this.jdbcTemplate.update("TRUNCATE users");
    }

}

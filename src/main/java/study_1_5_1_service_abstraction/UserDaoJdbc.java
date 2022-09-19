package study_1_5_1_service_abstraction;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDaoJdbc implements UserDao {
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
            user.setLevel(Level.valueOf(resultSet.getInt("Level")));
            user.setLogin(resultSet.getInt("Login"));
            user.setRecommend(resultSet.getInt("Recommend"));

            return user;
        }
    };
    public void add(User user) {
            this.jdbcTemplate.update("insert into users(id, name, password, Level, Login, Recommend) values(?,?,?,?,?,?)", user.getid(), user.getname(), user.getpassword(), user.getLevel(), user.getLogin(), user.getRecommand());
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

    public User get(String id) {
        return this.jdbcTemplate.queryForObject("select * from users where id = ?", new Object[] {id}, this.userMappper);
    }
   public List<User> getAll() {
       return this.jdbcTemplate.query("select * from users order by id", this.userMappper);

   }

    public int getCount() {
        return this.jdbcTemplate.queryForObject("select count(*) from users", Integer.class);
//        return 0;
    }

    public void reset() {
        this.jdbcTemplate.update("TRUNCATE users");
    }

}

package study_1_5_5_java_mail;

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
            user.setId(resultSet.getString("id"));
            user.setName(resultSet.getString("name"));
            user.setPassword(resultSet.getString("password"));
            user.setLevel(Level.valueOf(resultSet.getInt("Level")));
            user.setLogin(resultSet.getInt("Login"));
            user.setRecommend(resultSet.getInt("Recommend"));
            user.setEmail(resultSet.getString("email"));
            return user;
        }
    };
    public void add(User user) {
        this.jdbcTemplate.update("insert into users_mail(id, name, password, Level, Login, Recommend, email) values(?,?,?,?,?,?,?)", user.getId(), user.getName(), user.getPassword(), user.getLevel().intValue(), user.getLogin(), user.getRecommand(), user.getEmail());
    }
    public void update(User user) {
        this.jdbcTemplate.update("update users_mail set name = ?, password = ?, Level = ?, Login = ?, Recommend = ?, email = ? where id = ?", user.getName(), user.getPassword(), user.getLevel().intValue(), user.getLogin(), user.getRecommand(), user.getEmail(), user.getId());
    }
    public User get(String id) {
        return this.jdbcTemplate.queryForObject("select * from users_mail where id = ?", new Object[] {id}, this.userMappper);
    }
   public List<User> getAll() {
       return this.jdbcTemplate.query("select * from users_mail order by id", this.userMappper);

   }

    public int getCount() {
        return this.jdbcTemplate.queryForObject("select count(*) from users_mail", Integer.class);
//        return 0;
    }

    public void reset() {
        this.jdbcTemplate.update("TRUNCATE users_mail");
    }

}

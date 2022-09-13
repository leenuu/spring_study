package study_1_3_1_exception_strategy_pattern;

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
    public void add(final User user) throws SQLException {
        jdbcContextWithStatementStrategy(new StatementStrategy() {
            public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
                PreparedStatement ps = c.prepareStatement(
                        "insert into users(id, name, password) values(?,?,?)");

                ps.setString(1, user.getid());
                ps.setString(2, user.getname());
                ps.setString(3, user.getpassword());

                return ps;
            }
        });
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
        jdbcContextWithStatementStrategy(new StatementStrategy() {
            @Override
            public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
               return c.prepareStatement("TRUNCATE users");
            }
        });
    }

    public void jdbcContextWithStatementStrategy(StatementStrategy stmt) throws SQLException {
        Connection c = null;
        PreparedStatement ps = null;

        try {
            c = dataSource.getConnection();
            ps = stmt.makePreparedStatement(c);
            ps.execute();
        } catch (SQLException e) {
            throw e;
        } finally {
            if (ps != null) { try { ps.close();} catch (SQLException e) {}}
            if (c != null) { try { c.close();} catch (SQLException e) {}}
        }
    }
    private PreparedStatement makeStatement(Connection c) throws SQLException {
        PreparedStatement ps;
        ps = c.prepareStatement("TRUNCATE users");
        return ps;
    }
}

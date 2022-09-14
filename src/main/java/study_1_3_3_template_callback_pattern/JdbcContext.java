package study_1_3_3_template_callback_pattern;

import javax.print.attribute.standard.Finishings;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JdbcContext {
    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void workWithStatementStrategy(StatementStrategy stmt) throws SQLException
    {
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
    public void exequteSql(final String query, final String... property) throws SQLException {
        this.workWithStatementStrategy(new StatementStrategy() {
            public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
                PreparedStatement ps = c.prepareStatement(query);
                for(int i = 0; i <= property.length; i++) {
                    ps.setString(i+1, property[i]);
                }
                return ps;
            }
        });
    }
}

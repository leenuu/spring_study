package study_1_3_2_strategy_pattern;

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
}

package study_1_5_3_transaction;

import org.junit.Test;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class UserService {
    UserDao userDao;
    public static final int MIN_LOGCOUNT_FOR_SILVER = 50;
    public static final int MIN_RECOMMEND_FOR_GOLD = 30;
    private DataSource dataSource;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    public void upgradeLevels() throws Exception{
        TransactionSynchronizationManager.initSynchronization();
        Connection c = DataSourceUtils.getConnection(dataSource);
        c.setAutoCommit(false);

        try {
            List<User> users = userDao.getAll();
            for(User user : users) {
                if (canUpgradeLevel(user)) {
                    upgradeLevel(user);
                }
            }
        }
        catch (Exception e) {
            c.rollback();
            throw e;
        }
        finally {
            DataSourceUtils.releaseConnection(c, dataSource);
            TransactionSynchronizationManager.unbindResource(this.dataSource);
            TransactionSynchronizationManager.clearSynchronization();
        }

    }
    public void add(User user) {
        if (user.getLevel() == null) user.setLevel(Level.BASIC);
        userDao.add(user);
    }

    protected boolean canUpgradeLevel(User user) {
        Level currentLevel = user.getLevel();
        switch (currentLevel) {
            case BASIC: return (user.getLogin() >= MIN_LOGCOUNT_FOR_SILVER);
            case SILVER: return (user.getRecommand() >= MIN_RECOMMEND_FOR_GOLD);
            case GOLD: return false;
            default: throw new IllegalArgumentException("Unknown Level: " + currentLevel);
        }
    }
    protected void upgradeLevel(User user) {
        user.upgradeLevel();
        userDao.update(user);
    }

}


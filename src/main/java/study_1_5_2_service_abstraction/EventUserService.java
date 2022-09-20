package study_1_5_2_service_abstraction;

import java.util.List;

public class EventUserService implements UserLevelUpgradePolicy{
    UserDao userDao;
    public static final int EVENT_MIN_LOGCOUNT_FOR_SILVER = 25;
    public static final int EVENT_MIN_RECOMMEND_FOR_GOLD = 30;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
    public void upgradeLevels() {
        List<User> users = userDao.getAll();
        for(User user : users) {
            if (canUpgradeLevel(user)) {
                upgradeLevel(user);
            }
        }
    }
    public void add(User user) {
        if (user.getLevel() == null) user.setLevel(Level.BASIC);
        userDao.add(user);
    }
    public boolean canUpgradeLevel(User user) {
        Level currentLevel = user.getLevel();
        switch (currentLevel) {
            case BASIC: return (user.getLogin() >= EVENT_MIN_LOGCOUNT_FOR_SILVER);
            case SILVER: return (user.getRecommand() >= EVENT_MIN_RECOMMEND_FOR_GOLD);
            case GOLD: return false;
            default: throw new IllegalArgumentException("Unknown Level: " + currentLevel);
        }
    }
    public void upgradeLevel(User user) {
        user.upgradeLevel();
        userDao.update(user);
    }
}

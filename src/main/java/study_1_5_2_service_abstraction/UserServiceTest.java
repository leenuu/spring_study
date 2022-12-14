package study_1_5_2_service_abstraction;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static study_1_5_2_service_abstraction.UserService.MIN_LOGCOUNT_FOR_SILVER;
import static study_1_5_2_service_abstraction.UserService.MIN_RECOMMEND_FOR_GOLD;
import static study_1_5_2_service_abstraction.EventUserService.EVENT_MIN_LOGCOUNT_FOR_SILVER;
import static study_1_5_2_service_abstraction.EventUserService.EVENT_MIN_RECOMMEND_FOR_GOLD;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/1_5_applicationContextPlus.xml")
public class UserServiceTest {
    @Autowired
    UserService userService;
    @Autowired
    EventUserService eventUserServicel;
    @Autowired
    UserDao userDao;
    List<User> users;
    List<User> eventUsers;

    @Before
    public void setUp() {
        users = Arrays.asList (
            new User("leenuu", "kang", "not", Level.BASIC, MIN_LOGCOUNT_FOR_SILVER-1, 0),
            new User("donas", "kim", "404", Level.BASIC, MIN_LOGCOUNT_FOR_SILVER, 10),
            new User("ksj", "kim", "eunjin", Level.SILVER, 60, MIN_RECOMMEND_FOR_GOLD-1),
            new User("moon", "ans", "tlsgur", Level.SILVER, 60, MIN_RECOMMEND_FOR_GOLD),
            new User("pgn", "hong", "found", Level.GOLD, 100, Integer.MAX_VALUE)
        );

        eventUsers = Arrays.asList (
                new User("leenuu", "kang", "not", Level.BASIC, EVENT_MIN_LOGCOUNT_FOR_SILVER-1, 0),
                new User("donas", "kim", "404", Level.BASIC, EVENT_MIN_LOGCOUNT_FOR_SILVER, 10),
                new User("ksj", "kim", "eunjin", Level.SILVER, 60, EVENT_MIN_RECOMMEND_FOR_GOLD-1),
                new User("moon", "ans", "tlsgur", Level.SILVER, 60, EVENT_MIN_RECOMMEND_FOR_GOLD),
                new User("pgn", "hong", "found", Level.GOLD, 100, Integer.MAX_VALUE)
        );
    }

//    @Test
    public void bean() {
        assertThat(this.userService, is(notNullValue()));
    }
    @Test
    public void eventUpgradeLevels() {
        userDao.reset();
        for(User user : eventUsers) userDao.add(user);

        eventUserServicel.upgradeLevels();

//        System.out.println(eventUsers.get(0));

        cheackLevel(eventUsers.get(0), false);
        cheackLevel(eventUsers.get(1), true);
        cheackLevel(eventUsers.get(2), false);
        cheackLevel(eventUsers.get(3), true);
        cheackLevel(eventUsers.get(4), false);
    }
//    @Test
    public void upgradeLevels() {
        userDao.reset();
        for(User user : users) userDao.add(user);

        userService.upgradeLevels();

        cheackLevel(users.get(0), false);
        cheackLevel(users.get(1), true);
        cheackLevel(users.get(2), false);
        cheackLevel(users.get(3), true);
        cheackLevel(users.get(4), false);


    }
//    @Test
    public void add() {
        userDao.reset();

        User userWithLevel = users.get(4);
        User userWithoutlevel = users.get(0);
        userWithoutlevel.setLevel(null);

        userService.add(userWithLevel);
        userService.add(userWithoutlevel);

        User userWithLevelRead = userDao.get(userWithLevel.getId());
        User userWithoutLevelRead = userDao.get(userWithoutlevel.getId());

        assertThat(userWithLevelRead.getLevel(), is(userWithLevel.getLevel()));
        assertThat(userWithoutLevelRead.getLevel(), is(userWithoutlevel.getLevel()));
    }
    private void cheackLevel(User user, boolean upgraded) {
        User userUpdate = userDao.get(user.getId());
        if (upgraded) {
            assertThat(userUpdate.getLevel(), is(user.getLevel().nextLevel()));
        }
        else {
            assertThat(userUpdate.getLevel(), is(user.getLevel()));
        }
    }

}

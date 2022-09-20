package study_1_5_1_service_abstraction;

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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/1_5_applicationContext.xml")
public class UserServiceTest {
    @Autowired
    UserService userService;
    @Autowired
    UserDao userDao;
    List<User> users;

    @Before
    public void setUp() {
        users = Arrays.asList (
            new User("leenuu", "kang", "not", Level.BASIC, 49, 0),
            new User("donas", "kim", "404", Level.BASIC, 50, 10),
            new User("ksj", "kim", "eunjin", Level.SILVER, 60, 29),
            new User("moon", "ans", "tlsgur", Level.SILVER, 60, 30),
            new User("pgn", "hong", "found", Level.GOLD, 100, 100)
        );
    }

//    @Test
    public void bean() {
        assertThat(this.userService, is(notNullValue()));
    }
//    @Test
    public void upgradeLevels() {
        userDao.reset();
        for(User user : users) userDao.add(user);

        userService.upgradeLevels();

        cheackLevel(users.get(0), Level.BASIC);
        cheackLevel(users.get(1), Level.SILVER);
        cheackLevel(users.get(2), Level.SILVER);
        cheackLevel(users.get(3), Level.GOLD);
        cheackLevel(users.get(4), Level.GOLD);


    }
    @Test
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
    private void cheackLevel(User user, Level expectedLevel) {
        User userUpdate = userDao.get(user.getId());
        assertThat(userUpdate.getLevel(), is(expectedLevel));
    }

}

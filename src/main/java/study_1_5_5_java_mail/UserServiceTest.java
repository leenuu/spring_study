package study_1_5_5_java_mail;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static study_1_5_2_service_abstraction.UserService.MIN_LOGCOUNT_FOR_SILVER;
import static study_1_5_2_service_abstraction.UserService.MIN_RECOMMEND_FOR_GOLD;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/1_6_applicationContext.xml")
public class UserServiceTest {
    @Autowired
    UserService userService;
    @Autowired
    UserDao userDao;
    @Autowired
    PlatformTransactionManager transactionManager;
    @Autowired
    MailSender mailSender;
    List<User> users;

    @Before
    public void setUp() {
        users = Arrays.asList (
            new User("leenuu", "kang", "not", Level.BASIC, MIN_LOGCOUNT_FOR_SILVER-1, 0, "kangjoon10@gmail.com"),
            new User("donas", "kim", "404", Level.BASIC, MIN_LOGCOUNT_FOR_SILVER, 10, "kangjoon10@gmail.com"),
            new User("ksj", "kim", "eunjin", Level.SILVER, 60, MIN_RECOMMEND_FOR_GOLD-1, "kangjoon10@gmail.com"),
            new User("moon", "ans", "tlsgur", Level.SILVER, 60, MIN_RECOMMEND_FOR_GOLD, "kangjoon10@gmail.com"),
            new User("pgn", "hong", "found", Level.GOLD, 100, Integer.MAX_VALUE, "kangjoon10@gmail.com")
        );
    }

//    @Test
    public void upgradeAllOrNothing() {
        UserService testUserService = new TestUserService(users.get(3).getId());
        testUserService.setUserDao(this.userDao);
        testUserService.setTransactionManager(this.transactionManager);
        testUserService.setMailSender(mailSender);

        userDao.reset();
        for(User user : users) userDao.add(user);

        try {
            testUserService.upgradeLevels();
//            fail("TestUserServiceException expected");
        }
        catch (TestUserServiceException e) {
        }

        cheackLevel(users.get(1), false);
    }
//    @Test
    public void bean() {
        assertThat(this.userService, is(notNullValue()));
    }
    @Test
    @DirtiesContext
    public void upgradeLevels() throws Exception {
        userDao.reset();
        for(User user : users) userDao.add(user);

        MockMailSender mockMailSender = new MockMailSender();
        userService.setMailSender(mockMailSender);

        userService.upgradeLevels();

        cheackLevel(users.get(0), false);
        cheackLevel(users.get(1), true);
        cheackLevel(users.get(2), false);
        cheackLevel(users.get(3), true);
        cheackLevel(users.get(4), false);

        List<String> requests = mockMailSender.getRequests();
        assertThat(requests.size(), is(2));
        assertThat(requests.get(0), is(users.get(1).getEmail()));
        assertThat(requests.get(1), is(users.get(3).getEmail()));
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
    static class TestUserService extends UserService {
        private String id;

        private TestUserService(String id) {
            this.id = id;
        }

        protected void upgradeLevel(User user) {
            if (user.getId().equals(this.id)) throw new TestUserServiceException();
            super.upgradeLevel(user);
        }
    }
    static class TestUserServiceException extends RuntimeException {

    }

}

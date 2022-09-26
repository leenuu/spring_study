package study_1_6_1_AOP;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.ArrayList;
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
    UserServiceImpl userService;
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
        UserServiceImpl testUserService = new TestUserService(users.get(3).getId());
        testUserService.setUserDao(this.userDao);
        testUserService.setMailSender(mailSender);

        UserServiceTx userServiceTx = new UserServiceTx();
        userServiceTx.setTransactionManager(this.transactionManager);
        userServiceTx.setUserService(testUserService);

        userDao.reset();
        for(User user : users) userDao.add(user);

        try {
            userServiceTx.upgradeLevels();
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
//    @DirtiesContext
    public void upgradeLevels() throws Exception {
        UserServiceImpl userServiceImpl = new UserServiceImpl();

        MockUserDao mockUserDao = new MockUserDao(this.users);
        userServiceImpl.setUserDao(mockUserDao);

        MockMailSender mockMailSender = new MockMailSender();
        userServiceImpl.setMailSender(mockMailSender);

        userServiceImpl.upgradeLevels();

        List<User> updated = mockUserDao.getUpdated();
        assertThat(updated.size(), is(2));
        cheackUserAndLevel(updated.get(0), "donas", Level.SILVER);
        cheackUserAndLevel(updated.get(1), "moon", Level.GOLD);

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
    private void cheackUserAndLevel(User updated, String expectedId, Level expectedLevel) {
        assertThat(updated.getId(), is(expectedId));
        assertThat(updated.getLevel(), is(expectedLevel));
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
    static class TestUserService extends UserServiceImpl {
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
    static class MockUserDao implements UserDao {
        private List<User> users;
        private List<User> updated = new ArrayList();

        private MockUserDao(List<User> users) {
            this.users = users;
        }
        public List<User> getUpdated() {
            return this.updated;
        }
        public List<User> getAll() {
            return this.users;
        }
        public void update(User user) {
            this.updated.add(user);
        }
        public void add(User user) {
            throw new UnsupportedOperationException();
        }
        public void reset() {
            throw new UnsupportedOperationException();
        }
        public User get(String id) {
            throw new UnsupportedOperationException();
        }
        public int getCount() {
            throw new UnsupportedOperationException();
        }
    }

}

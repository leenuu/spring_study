package study_1_5_2_service_abstraction;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/1_5_applicationContext.xml")
public class UserDaoTest {

    @Autowired
    private ApplicationContext context;
    @Autowired
    private UserDao dao;
    @Autowired
    private DataSource dataSource;
    private User user1;
    private User user2;
    private User user3;

    @Before
    public void setUp() {
        this.user1 = new User("leenuu", "kang", "404", Level.BASIC, 1, 0);
        this.user2 = new User("donas", "kim", "not", Level.SILVER, 55, 10);
        this.user3 = new User("pgn", "hong", "found", Level.GOLD, 40, 40);
    }
    @Test
    public void update() {
        dao.reset();

        dao.add(user1);
        dao.add(user2);

        user1.setName("eunjin");
        user1.setPassword("ksj");
        user1.setLevel(Level.GOLD);
        user1.setLogin(1000);
        user1.setRecommend(999);

        dao.update(user1);

        User user1updtate = dao.get(user1.getId());
        checkSameUser(user1, user1updtate);
        User user2same = dao.get(user2.getId());
        checkSameUser(user2, user2same);
    }
//    @Test
    public void resetData() {
        this.dao.reset();
    }
//    @Test
    public void setData() {
        this.dao.add(user1);
        this.dao.add(user2);
        this.dao.add(user3);
    }
//    @Test
    public void addAndget() {

        dao.reset();
        dao.add(user1);
		dao.add(user2);
        dao.add(user3);

        User userget1 = dao.get(user1.getId());
        User userget2 = dao.get(user2.getId());
        User userget3 = dao.get(user3.getId());

        checkSameUser(user1, userget1);
        checkSameUser(user2, userget2);
        checkSameUser(user3, userget3);

    }
//    @Test(expected = DuplicateKeyException.class)
    public void duplicatekey() {
        dao.reset();

        dao.add(user1);
        dao.add(user1);
    }
//    @Test
    public void sqlExceptionTranslate() {
        dao.reset();

        try {
            dao.add(user1);
            dao.add(user1);
        }
        catch (DuplicateKeyException ex) {
            SQLException sqlEx = (SQLException)ex.getRootCause();
            SQLExceptionTranslator set = new SQLErrorCodeSQLExceptionTranslator(this.dataSource);
            assertThat(set.translate(null, null, sqlEx), instanceOf(DuplicateKeyException.class));
        }
    }
//    @Test(expected = EmptyResultDataAccessException.class)
    public void getUserFailure(){

        dao.reset();
        assertThat(dao.getCount(), is(0));

        dao.get("unkown_id");
    }
//    @Test
    public void getAll(){
        dao.reset();

        List<User> users0 = dao.getAll();
        assertThat(users0.size(), is(0));
        
        dao.add(user1);
        List<User> users1 = dao.getAll();
        assertThat(users1.size(), is(1));
        checkSameUser(user1, users1.get(0));

        dao.add(user2);
        List<User> users2 = dao.getAll();
        assertThat(users2.size(), is(2));
        checkSameUser(user2, users2.get(0));
        checkSameUser(user1, users2.get(1));

        dao.add(user3);
        List<User> users3 = dao.getAll();
        assertThat(users3.size(), is(3));
        checkSameUser(user2, users3.get(0));
        checkSameUser(user1, users3.get(1));
        checkSameUser(user3, users3.get(2));
    }
    private void checkSameUser(User user1, User user2) {
        assertThat(user1.getId(), is(user2.getId()));
        assertThat(user1.getName(), is(user2.getName()));
        assertThat(user1.getPassword(), is(user2.getPassword()));
        assertThat(user1.getLevel(), is(user2.getLevel()));
        assertThat(user1.getLogin(), is(user2.getLogin()));
        assertThat(user1.getRecommand(), is(user2.getRecommand()));

    }
}

package study_1_4_2_error_exception;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
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
@ContextConfiguration(locations = "/1_4_applicationContext.xml")
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
//        System.out.println(this.context);
//        System.out.println(this);

        // this.dao = this.context.getBean("userDao", UserDao.class);

        this.user1 = new User("leenuu", "kang", "404");
        this.user2 = new User("donas", "kim", "not");
        this.user3 = new User("pgn", "hong", "found");
    }
//    @Test
    public void addAndget() throws SQLException {

        dao.reset();
        assertThat(dao.getCount(), is(0));
//        System.out.println("test reset table complete");
        dao.add(user1);
//        System.out.println("test add complete");
		dao.add(user2);
        dao.add(user3);
        assertThat(dao.getCount(), is(3));

        User userget1 = dao.get(user1.getid());
        User userget2 = dao.get(user2.getid());
        User userget3 = dao.get(user3.getid());

        assertThat(userget1.getname(), is(user1.getname()));
        assertThat(userget1.getpassword(), is(user1.getpassword()));

        assertThat(userget2.getname(), is(user2.getname()));
        assertThat(userget2.getpassword(), is(user2.getpassword()));


        assertThat(userget3.getname(), is(user3.getname()));
        assertThat(userget3.getpassword(), is(user3.getpassword()));

//        assertThat(user2.getname(), is(user2.getname()));
//        assertThat(user2.getpassword(), is(user2.getpassword()));
//        if (!user1.getname().equals(user2.getname())) {
//            System.out.println("test fail name");
//        }
//        else if (!user1.getpassword().equals(user2.getpassword())) {
//            System.out.println("test fail password");
//        }
//        else {
//            System.out.println("test search complete");
//        }


//        System.out.println("name : " + user2.getname());
//        System.out.println("id : " + user2.getid());
//        System.out.println("passwd : " + user2.getpassword());
    }
//    @Test(expected = DuplicateKeyException.class)
    public void duplicatekey() {
        dao.reset();

        dao.add(user1);
        dao.add(user1);
    }
    @Test
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
    public void getUserFailure() throws SQLException {

        dao.reset();
        assertThat(dao.getCount(), is(0));

        dao.get("unkown_id");
    }
//    @Test
    public void getAll() throws SQLException{
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
        assertThat(user1.getid(), is(user2.getid()));
        assertThat(user1.getname(), is(user2.getname()));
        assertThat(user1.getpassword(), is(user2.getpassword()));
    }
}

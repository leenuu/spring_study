package study_1_2_1_test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericGroovyApplicationContext;

import java.sql.SQLException;

public class UserDaoTest {

    @Test
    public void addAndget() throws ClassNotFoundException, SQLException {
        ApplicationContext context = new GenericGroovyApplicationContext("applicationContext.xml");

        User user1 = new User();
        User user2 = new User();

        UserDao dao = context.getBean("userDao",UserDao.class);

        dao.reset();

        System.out.println("test reset table complete");

        user1.setid("leenuu");
        user1.setname("kang");
        user1.setpassword("not_found");

        dao.add(user1);

        System.out.println("test add complete");

		user2 = dao.get(user1.getid());

        assertThat(user2.getname(), is(user2.getname()));
        assertThat(user2.getpassword(), is(user2.getpassword()));
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
}

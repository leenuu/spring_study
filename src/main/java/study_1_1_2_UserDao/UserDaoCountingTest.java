package study_1_1_2_UserDao;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;

public class UserDaoCountingTest {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        ApplicationContext context = new AnnotationConfigApplicationContext(CountingDaoFactory.class);
        User user = new User();
        UserDao dao = context.getBean("userDao",UserDao.class);

        CountingConnectionMaker ccm = context.getBean("connectionMaker", CountingConnectionMaker.class);
        System.out.println("counter : " + ccm.getCounter());
    }
}

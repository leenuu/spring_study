package study1_1_2_UserDao;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;

public class UserDaoTest {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
        User user = new User();
        UserDao dao = context.getBean("userDao",UserDao.class);

//		UserDao dao = new UserDao();
		user = dao.get("2");

        System.out.println("name : " + user.getname());
        System.out.println("id : " + user.getid());
        System.out.println("passwd : " + user.getpassword());
    }
}

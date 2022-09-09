package test_3_UserDao_XML;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericGroovyApplicationContext;

import java.sql.SQLException;

public class UserDaoTest {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        ApplicationContext context = new GenericGroovyApplicationContext("applicationContext.xml");
        User user = new User();
        UserDao dao = context.getBean("userDao",UserDao.class);

//		UserDao dao = new UserDao();
		user = dao.get("2");

        System.out.println("name : " + user.getname());
        System.out.println("id : " + user.getid());
        System.out.println("passwd : " + user.getpassword());
    }
}

package test_2_UserDao;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class testObj {
    public static void main(String[] args) {
        DaoFactory factory = new DaoFactory();
        UserDao dao1 = factory.userDao();
        UserDao dao2 = factory.userDao();

        ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
        UserDao dao3 = context.getBean("userDao", UserDao.class);
        UserDao dao4 = context.getBean("userDao", UserDao.class);

        System.out.println("dao1 : " + dao1);
        System.out.println("dao2 : " + dao2);
        System.out.println("dao3 : " + dao3);
        System.out.println("dao4 : " + dao4);

    }
}

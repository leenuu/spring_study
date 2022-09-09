package test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class test {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(testBean.class);

        int num = context.getBean("test", int.class);

        System.out.println(num);

    }
}

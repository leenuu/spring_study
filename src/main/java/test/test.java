package test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


public class test {
    @Test
    public void test() {
        ApplicationContext context = new AnnotationConfigApplicationContext(testBean.class);

        int num = context.getBean("test", int.class);

        assertThat(num, is(1));
    }
}

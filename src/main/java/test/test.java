package test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


public class test {
    public static void main(String[] args) {
        new test().test_1("gjgjgjgj");
        new test().test_1("54545454");
    }
    public void test_1(String q,String... s) {
//        System.out.println(s[0]);
        System.out.println(q);
        System.out.println(s.length);
//        for(String a:s)
//            System.out.println(a);
    }
}

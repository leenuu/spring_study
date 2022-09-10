package study_1_2_4_edu_test_code;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.matchers.JUnitMatchers.either;
import static org.junit.matchers.JUnitMatchers.hasItem;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = JUnitTest.class)
public class JUnitTest {
    @Autowired
    ApplicationContext context;
    static Set<JUnitTest> testObject = new HashSet<JUnitTest>();
    static ApplicationContext contextObject = null;
//    static JUnitTest testObject;

    @Test
    public void test1() {
        assertThat(testObject, not(hasItem(this)));
        testObject.add(this);

        assertThat(contextObject == null || contextObject == this.context, is(true));
        contextObject = this.context;

//        assertThat(this, is(not(sameInstance(testObject))));
//        testObject = this;
    }

    @Test
    public void test2() {
        assertThat(testObject, not(hasItem(this)));
        testObject.add(this);


        assertTrue(contextObject == null || contextObject == this.context);
        contextObject = this.context;
//        assertThat(this, is(not(sameInstance(testObject))));
//        testObject = this;
    }

    @Test
    public void test3() {
        assertThat(testObject, not(hasItem(this)));
        testObject.add(this);

        assertThat(contextObject, either(is(nullValue())).or(is(this.context)));
        contextObject = this.context;
//        assertThat(this, is(not(sameInstance(testObject))));
//        testObject = this;
    }

}

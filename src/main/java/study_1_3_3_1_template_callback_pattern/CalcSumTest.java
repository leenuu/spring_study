package study_1_3_3_1_template_callback_pattern;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CalcSumTest {
    Calculator calculator;
    String numFilePath;

    @Before
    public void setUp() {
        this.calculator = new Calculator();
        this.numFilePath = new File("./numbers.txt").getPath();
    }
    @Test
    public void sumOfNumbers() throws IOException {
//        File dir = new File("./").getPath();
//        File files[] = dir.listFiles();
//
//        for (File file : files) {
//            System.out.println(file);
//        }
//        System.out.println(new File("./numbers.txt").getPath());
//        int sum = calculator.clacSum();
        assertThat(calculator.clacSum(this.numFilePath), is(10));
    }
}

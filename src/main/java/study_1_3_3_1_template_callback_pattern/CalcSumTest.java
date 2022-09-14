package study_1_3_3_1_template_callback_pattern;

import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CalcSumTest {
    @Test
    public void sumOfNumbers() throws IOException {
        Calculator calculator = new Calculator();
//        File dir = new File("./").getPath();
//        File files[] = dir.listFiles();
//
//        for (File file : files) {
//            System.out.println(file);
//        }
//        System.out.println(new File("./numbers.txt").getPath());
//        int sum = calculator.clacSum();
        int sum = calculator.clacSum(new File("./numbers.txt").getPath());
        assertThat(sum, is(10));
    }
}

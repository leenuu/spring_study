package study_1_3_3_1_template_callback_pattern;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.Buffer;

public class Calculator {
    public Integer clacSum(String filepath) throws IOException {
        BufferedReader br =null;
        try {
            br = new BufferedReader(new FileReader(filepath));
            Integer sum = 0;
            String line = null;
            while ((line = br.readLine()) != null) {
                sum += Integer.valueOf(line);
            }
            br.close();
            return sum;
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
            throw e;
        }
        finally {
            if (br != null) {
                try {
                    br.close();
                }
                catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }

        }
    }
}

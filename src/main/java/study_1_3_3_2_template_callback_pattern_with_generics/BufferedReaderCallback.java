package study_1_3_3_2_template_callback_pattern_with_generics;

import java.io.BufferedReader;
import java.io.IOException;

public interface BufferedReaderCallback {
    Integer doSomeThingWithReader(BufferedReader br) throws IOException;
}

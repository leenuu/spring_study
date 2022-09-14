package study_1_3_3_1_template_callback_pattern;

import java.io.BufferedReader;
import java.io.IOException;

public interface BufferedReaderCallback {
    Integer doSomeThingWithReader(BufferedReader br) throws IOException;
}

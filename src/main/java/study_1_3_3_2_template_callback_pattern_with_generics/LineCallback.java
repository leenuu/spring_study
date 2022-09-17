package study_1_3_3_2_template_callback_pattern_with_generics;

public interface LineCallback<T> {
    T doSomethingWithLine(String line, T value);
}

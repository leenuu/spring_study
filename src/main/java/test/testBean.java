package test;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class testBean {
    @Bean
    public int test() {
        return  1;
    }
}

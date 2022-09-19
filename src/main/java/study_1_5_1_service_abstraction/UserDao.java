package study_1_5_1_service_abstraction;

import java.util.List;

public interface UserDao {
    void add(User user);
    User get(String id);
    List<User> getAll();
    void reset();
    int getCount();
}

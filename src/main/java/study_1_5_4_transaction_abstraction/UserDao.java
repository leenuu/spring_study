package study_1_5_4_transaction_abstraction;

import java.util.List;

public interface UserDao {
    void add(User user);
    void update(User user1);
    User get(String id);
    List<User> getAll();
    void reset();
    int getCount();
}

package study_1_5_1_service_abstraction;

public class User {
    String id;
    String name;
    String password;
    Level level;
    int login;
    int recommend;

    public User() {

    }
    public User(String id, String name, String password, Level level, int login, int recommend) {
       this.id = id;
       this.name = name;
       this.password = password;
       this.level = level;
       this.login = login;
       this.recommend = recommend;
    }
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public void setLevel(Level level) {
        this.level = level;
    }
    public Level getLevel() {
        return level;
    }
    public void setLogin(int login) {
        this.login = login;
    }
    public int getLogin() {
        return this.login;
    }
    public void setRecommend(int recommend) {
        this.recommend = recommend;
    }
    public int getRecommand() {
        return this.recommend;
    }
}

package study_1_4_2_error_exception;

public class User {
    String id;
    String name;
    String password;

    public User() {

    }
    public User(String id, String name, String password) {
       this.id = id;
       this.name = name;
       this.password = password;
    }
    public String getid() {
        return this.id;
    }

    public void setid(String id) {
        this.id = id;
    }

    public String getname() {
        return this.name;
    }

    public void setname(String name) {
        this.name = name;
    }

    public String getpassword() {
        return this.password;
    }

    public void setpassword(String password) {
        this.password = password;
    }
}

package entities;

public class Teacher {

    private int idTeacher;
    private String email;
    private String login;
    private String password;

    public Teacher(String email, String login, String password) {
        this.email = email;
        this.login = login;
        this.password = password;
    }

    public Teacher(int idTeacher, String email, String login, String password) {
        this.idTeacher = idTeacher;
        this.email=email;
        this.login = login;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getIdTeacher() {
        return idTeacher;
    }

    public void setIdTeacher(int idTeacher) {
        this.idTeacher = idTeacher;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

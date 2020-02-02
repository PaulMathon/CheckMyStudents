package com.example.checkmystudents.entities;

public class Teacher {

    private long teacherId;
    private String login;
    private String password;

    public Teacher(long teacherId, String login, String password){
        this.teacherId=teacherId;
        this.login=login;
        this.password=password;
    }

    public long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(long userId) {
        this.teacherId = teacherId;
    }

    public String getLogin() {
        return login;
    }

    public void setEmail(String email) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

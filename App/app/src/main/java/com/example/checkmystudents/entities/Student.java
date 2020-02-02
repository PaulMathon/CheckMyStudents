package com.example.checkmystudents.entities;

public class Student {
    int id;
    String name;
    String firstName;
    int id_class;

    public Student(int id, String name, String firstName, int id_class) {
        this.id = id;
        this.name = name;
        this.firstName = firstName;
        this.id_class = id_class;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public int getId_class() {
        return id_class;
    }

    public void setId_class(int id_class) {
        this.id_class = id_class;
    }
}

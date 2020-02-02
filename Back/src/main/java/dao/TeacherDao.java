package dao;

import entities.Teacher;
import exceptions.MatchAlreadyExistsException;

import java.io.IOException;

import java.util.List;

public interface TeacherDao {

    Teacher getTeacherFromLogin(String login);

    List<Teacher> listTeachers() throws IOException;

    Teacher registerTeacher(Teacher teacher);




}

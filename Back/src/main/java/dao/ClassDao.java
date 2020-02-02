package dao;

import entities.Class;
import entities.Teacher;

import java.io.IOException;
import java.util.List;

public interface ClassDao {

    List<Class> getClassFromTeacher(Teacher teacher) throws IOException;
}

package dao;

import entities.Student;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface StudentDao {

    List<Student> listStudentsFromClass(int idClass) throws IOException;
}

package dao.impl;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import dao.StudentDao;

import entities.Student;

import static dao.impl.DataSourceProvider.getDataSource;

public class StudentDaoImpl implements StudentDao {

    //récupérer la liste des utilisateurs avec en premier les admins
    public List<Student> listStudentsFromClass(int idClass) throws IOException {
        List<Student> listStudents = new ArrayList<>();
        try (Connection connection = getDataSource().getConnection()) {
            String sqlQuery = "SELECT * FROM `eleve` WHERE id_classe=?";
            try (PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
                statement.setInt(1, idClass);
                try (ResultSet results = statement.executeQuery()) {
                    while (results.next()) {
                        Student student = new Student(results.getInt("id_eleve"),results.getString("nom"),results.getString("prenom"),results.getInt("id_classe"));
                        listStudents.add(student);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); }
        return listStudents;
    }

}



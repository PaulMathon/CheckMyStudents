package dao.impl;

import dao.TeacherDao;
import entities.Class;
import entities.Teacher;
import exceptions.MatchAlreadyExistsException;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static dao.impl.DataSourceProvider.getDataSource;

import java.time.LocalDate;

public class TeacherDaoImpl implements TeacherDao {

    //Récupération des matchs dont la date est passée est dont le score n'est pas encore rentré
    @Override
    public List<Teacher> listTeachers() throws IOException {

        List<Teacher> listTeacher = new ArrayList<>();

        try (Connection connection = getDataSource().getConnection()) {
            String sqlQuery = "SELECT * FROM `professeur`;";
            try (Statement statement = connection.createStatement()) {
                try (ResultSet results = statement.executeQuery(sqlQuery)) {
                    while (results.next()) {
                        Teacher teacher = new Teacher(results.getInt("id_prof"),
                                results.getString("email"),
                                results.getString("login"),
                                results.getString("mdp"));
                        listTeacher.add(teacher);

                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listTeacher;

    }

    @Override
    public Teacher getTeacherFromLogin(String login){

        System.out.println("Login au début de getTeacherFromLogin : "+login);

        Teacher teacher = null;

        try (Connection connection = getDataSource().getConnection()) {
            String sqlQuery = "SELECT * FROM `professeur` WHERE login=?;";
            try (PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
                statement.setString(1, login);
                try (ResultSet results = statement.executeQuery()) {
                    if (results.next()) {
                        teacher = new Teacher(results.getInt("id_prof"),
                                results.getString("email"),
                                results.getString("login"),
                                results.getString("password"));
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Vous n\'êtes pas connecté à la base de données, vérifiez qu\'elle est bien allumée et rééssayez");
        }
        return teacher;
    }

    @Override
    public Teacher registerTeacher(Teacher teacher){

        System.out.println("Enregistrement du prof : "+teacher.getLogin());

        try (Connection connection = getDataSource().getConnection()) {
            String sqlQuery = "INSERT INTO `professeur`(email,login,password) VALUES (?,?,?);";
            try (PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
                statement.setString(1, teacher.getEmail());
                statement.setString(2, teacher.getLogin());
                statement.setString(3, teacher.getPassword());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            System.err.println("Vous n\'êtes pas connecté à la base de données, vérifiez qu\'elle est bien allumée et rééssayez");
        }
        return teacher;
    }



}



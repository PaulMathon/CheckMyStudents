package dao.impl;


import dao.ClassDao;
import entities.Class;
import entities.Teacher;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static dao.impl.DataSourceProvider.getDataSource;

public class ClassDaoImpl implements ClassDao {

    @Override
    public List<Class> getClassFromTeacher(Teacher teacher) throws IOException {

        List<Class> listClass = new ArrayList<>();

        try (Connection connection = getDataSource().getConnection()) {
            String sqlQuery = "SELECT e.id_prof, e.id_classe, c.domaine, c.url_photo FROM enseigne e INNER JOIN classe c ON c.id_classe = e.id_classe WHERE e.id_prof=?;";
            try (PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
                statement.setInt(1, teacher.getIdTeacher() );
                try (ResultSet results = statement.executeQuery()) {
                    while (results.next()) {
                        Class tmpClass = new Class(results.getInt("id_classe"),results.getString("domaine"),results.getString("url_photo"));
                        listClass.add(tmpClass);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Vous n\'êtes pas connecté à la base de données, vérifiez qu\'elle est bien allumée et rééssayez");
        }
        return listClass;
    }
}


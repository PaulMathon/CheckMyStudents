package managers;


import dao.StudentDao;
import dao.impl.StudentDaoImpl;
import entities.Student;
import utils.MotDePasseUtils;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class StudentLibrary {

    //Création d'un singleton:tjrs la meme instance
    private static class UtilisateurLibraryHolder {

        private final static StudentLibrary instance = new StudentLibrary();
    }

    private StudentDao studentDao = new StudentDaoImpl();

    public static StudentLibrary getInstance() {

        return UtilisateurLibraryHolder.instance;
    }

    public List<Student> listStudentsFromClass(int idClass) throws IOException { return studentDao.listStudentsFromClass(idClass);}


    }

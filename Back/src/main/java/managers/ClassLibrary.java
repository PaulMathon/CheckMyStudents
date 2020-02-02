package managers;

import dao.ClassDao;
import dao.impl.ClassDaoImpl;
import entities.Class;
import entities.Teacher;

import java.io.IOException;
import java.util.List;

public class ClassLibrary {

    //Création d'un singleton: une seule Library
    private static class EquipeLibraryHolder {
        private final static ClassLibrary instance = new ClassLibrary();
    }

    private ClassDao classDao = new ClassDaoImpl();

    public static ClassLibrary getInstance() { return ClassLibrary.EquipeLibraryHolder.instance;
    }

    private ClassLibrary() {
    }

    public List<Class> getClassFromTeacher(Teacher teacher) throws IOException {return classDao.getClassFromTeacher(teacher);}

}

package managers;


import dao.TeacherDao;
import dao.impl.TeacherDaoImpl;
import entities.Teacher;
import exceptions.MatchAlreadyExistsException;

import java.io.IOException;
import java.util.List;

public class TeacherLibrary {

	//Création d'un singleton:tjrs la meme instance
	private static class MatchLibraryHolder {
		private final static TeacherLibrary instance = new TeacherLibrary();
	}

	private TeacherDao teacherDao = new TeacherDaoImpl();

	public static TeacherLibrary getInstance() {
		return MatchLibraryHolder.instance;
	}


	private TeacherLibrary() {
	}

	public List<Teacher> listTeachers() throws IOException {return teacherDao.listTeachers();}

	public Teacher getTeacherFromLogin(String login){ return teacherDao.getTeacherFromLogin(login);}

	public Teacher registerTeacher(Teacher teacher){return teacherDao.registerTeacher(teacher);}


}

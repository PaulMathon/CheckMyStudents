package controllers;

import entities.Teacher;
import managers.TeacherLibrary;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.List;

@Path("teacher")
public class TeacherController {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Teacher> listTeachers() throws IOException {

        return TeacherLibrary.getInstance().listTeachers();
    }

    @GET
    @Path("/{teacher}")
    @Produces(MediaType.APPLICATION_JSON)
    public Teacher getTeacher(@PathParam("teacher") String teacherLogin) throws IOException {

        return TeacherLibrary.getInstance().getTeacherFromLogin(teacherLogin);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Teacher validTeacher(@FormParam("login") String login, @FormParam("password") String password){
        Teacher teacher = TeacherLibrary.getInstance().getTeacherFromLogin(login);
        System.out.println("Login reçut : "+login);
        if (teacher!=null && teacher.getPassword().equals(password)) {
            return teacher;
        } else {
            return null;
        }
    }


    @POST
    @Path("/register")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Teacher registerTeacher(@FormParam("email") String email, @FormParam("login") String login,
                                   @FormParam("password") String password){
        Teacher teacher = new Teacher( email, login, password);
        System.out.println("Login à indcrire : "+login);
        if (teacher!=null) {
            TeacherLibrary.getInstance().registerTeacher(teacher);
            return teacher;
        } else {
            return null;
        }
    }



}

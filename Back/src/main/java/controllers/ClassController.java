package controllers;

import entities.Class;
import entities.Teacher;
import managers.ClassLibrary;
import managers.TeacherLibrary;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.List;

@Path("/class")
public class ClassController {

    @GET
    @Path("/{teacher}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Class> getClassFromTeacher(@PathParam("teacher") String teacherLogin) throws IOException {

        Teacher teacher = TeacherLibrary.getInstance().getTeacherFromLogin(teacherLogin);
        System.out.println("#########"+teacher.getIdTeacher());

        return ClassLibrary.getInstance().getClassFromTeacher(teacher);
    }
}

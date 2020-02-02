package controllers;

import entities.Student;
import managers.StudentLibrary;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.List;

@Path("/students")
public class StudentController {

    @GET
    @Path("/{idClass}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Student> listStudents(@PathParam("idClass") String idClassAsString) throws IOException {
        int idClass = Integer.parseInt(idClassAsString);
        return StudentLibrary.getInstance().listStudentsFromClass(idClass);
    }

}

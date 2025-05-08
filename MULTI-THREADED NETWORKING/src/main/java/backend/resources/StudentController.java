package backend.resources;

import backend.StudentApp;
import backend.student.model.Student;
import backend.student.service.StudentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class StudentController {
    private StudentService studentService;
    private final ObjectMapper mapper = new ObjectMapper();

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }
    public String processRequest(String request){

        String[] protocol = request.split(";");
        //  protocol[0] = switch case
        switch (protocol[0]){
            case "1" : { // creating a new student
//               protocol[1] = Data Transfer Object or the Student the user wants to create
                try {
                    Student fromRequest = mapper.readValue(protocol[1], Student.class);
                    Student created =  this.studentService.createStudent(fromRequest);

//                  Successful response protocol containing the created user and a successful status
//                  denoted by the first element '1'
                    return "1" + ";" + this.mapper.writeValueAsString(created);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                    return "0" + ";" + "The student you submitted could not be created";
                } catch (Exception e) {
                    e.printStackTrace();
                    return "0" + ";" + e.getMessage();
                }
            }
            default:{
                return "0" + ";" + "Unable to process request";
            }
        }

    }

}

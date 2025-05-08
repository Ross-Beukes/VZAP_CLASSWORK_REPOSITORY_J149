package backend.student.service;

import backend.student.model.Student;

public interface StudentService {
    Student createStudent(Student student)throws Exception;
    Student findStudentById(Long id)throws IllegalArgumentException;
    Student updateStudent(Student student) throws Exception;
    Student deleteStudent(Student student) throws Exception;

}

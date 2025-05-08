package backend.student.repo;

import backend.student.model.Student;
import java.util.List;
import java.util.Optional;

public interface StudentRepo {
    //Create
    Optional<Student> createStudent(Student student);

    //Retrieve
    Optional<Student> retrieveStudent(Student student);
    Optional<Student> retrieveStudent(Long studentId);
    List<Student> getAllStudents();

    //Update
    Optional<Student> updateStudent(Student student);

    //Delete
    Optional<Student> deleteStudent(Student student);

}

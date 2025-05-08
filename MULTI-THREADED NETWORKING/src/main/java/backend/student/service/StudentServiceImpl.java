package backend.student.service;

import backend.student.model.Student;
import backend.student.repo.StudentRepo;

import java.util.Optional;

public class StudentServiceImpl implements StudentService{
    private StudentRepo studentRepo;

    public StudentServiceImpl(StudentRepo studentRepo) {
        this.studentRepo = studentRepo;
    }

    @Override
    public Student createStudent(Student student) throws Exception {
        if (student != null){
            String idNumber = student.getIdNumber();
            if (idNumber != null && idNumber.length() == 13) {
                Optional<Student> studentOptional = this.studentRepo.createStudent(student);
                if (studentOptional.isPresent()) {
                    return studentOptional.get();
                }
            }else {
                throw new IllegalArgumentException("Invalid ID Number");
            }
        }
        throw new IllegalArgumentException("Invalid Student!");
    }

    @Override
    public Student findStudentById(Long id) throws IllegalArgumentException {
        return null;
    }

    @Override
    public Student updateStudent(Student student) throws Exception {
        return null;
    }

    @Override
    public Student deleteStudent(Student student) throws Exception {
        return null;
    }
}

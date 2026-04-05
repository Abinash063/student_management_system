package com.example.demo.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // GET ALL STUDENTS
    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    // ADD NEW STUDENT
    public void addNewStudent(Student student) {
        Optional<Student> studentOptional =
                studentRepository.findStudentByEmail(student.getEmail());

        if (studentOptional.isPresent()) {
            throw new IllegalStateException("Email already exists");
        }

        studentRepository.save(student);
    }

    // DELETE STUDENT
    public void deleteStudent(String studentId) {   // ✅ String instead of Long
        boolean exists = studentRepository.existsById(studentId);

        if (!exists) {
            throw new IllegalStateException(
                    "Student with id " + studentId + " does not exist");
        }

        studentRepository.deleteById(studentId);
    }

    // UPDATE STUDENT
    public void updateStudent(String studentId, String name, String email) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalStateException(
                        "Student with id " + studentId + " does not exist"));

        // UPDATE NAME
        if (name != null && name.length() > 0 &&
                !Objects.equals(student.getName(), name)) {
            student.setName(name);
        }

        // UPDATE EMAIL
        if (email != null && email.length() > 0 &&
                !Objects.equals(student.getEmail(), email)) {

            Optional<Student> studentOptional =
                    studentRepository.findStudentByEmail(email);

            if (studentOptional.isPresent()) {
                throw new IllegalStateException("Email already taken");
            }

            student.setEmail(email);
        }

        studentRepository.save(student);
    }
}
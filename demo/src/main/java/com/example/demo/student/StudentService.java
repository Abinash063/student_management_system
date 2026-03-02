package com.example.demo.student;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

//@Component  //to make this bean
@Service // same as @component but more readable (nothing else) (context specific)
public class StudentService {
    private final StudentRepository studentRepository ;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }



    public List<Student> getStudents(){
        return  studentRepository.findAll(); //this returns a list to us, we have not implemented this method ourself,it is predefined provided by -> spring jpa (go and see inside JpaRepository interface (and all these methods are inside that) and this interface is extended by StudentRepository interface
    }

    public void addNewStudent(Student student) {
        Optional<Student> studentOptional  = studentRepository.findStudentByEmail(student.getEmail()); //this method can retur null or list of students ,so better to use Optional , it is a safe container , it provides us with .isPresent() method which helps us check is there something inside this box ??
        if(studentOptional.isPresent()){ //.getEmail is a public getter method defined in the Student class
            throw new IllegalStateException("email already exist");
        }
        studentRepository.save(student) ;  //this save method is predefined, it comes from public interface StudentRepository extends JpaRepository...(because of jpaRepository interface)
    }

    public void deleteStudent(Long studentId) {
        boolean exists = studentRepository.existsById(studentId) ;
        if(!exists){
            throw new IllegalStateException("student with id ->" + studentId +"doesn't exists");
        }
        studentRepository.deleteById(studentId) ;

    }

    @Transactional // <--- THE MAGIC ANNOTATION(automatically does the changes in the database in case anything changes , we don't need to explicitely,use .save method to write the changes in the database ,so, without this annotation, if the data changes,we will need to explicitely mention .save method to save the changes in the database ...
    public void updateStudent(Long studentId, String name, String email) {

        // Step 1: Find the student or throw an error
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalStateException(
                        "student with id " + studentId + " does not exist"));

        // Step 2: Update NAME (Logic Check)
        // logic: If name is provided AND it's not empty AND it's not the same as current name
        if (name != null && name.length() > 0 && !Objects.equals(student.getName(), name)) {
            student.setName(name);
        }

        // Step 3: Update EMAIL (Logic Check)
        if (email != null && email.length() > 0 && !Objects.equals(student.getEmail(), email)) {

            // 3a. Check if this new email is already taken by SOMEONE ELSE
            Optional<Student> studentOptional = studentRepository.findStudentByEmail(email);
            if (studentOptional.isPresent()) {
                throw new IllegalStateException("email taken");
            }

            // 3b. If safe, update the email
            student.setEmail(email);
        }
    }
}

package com.example.demo.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;



@RestController
@RequestMapping(path="/api/v1/student")
public class StudentController {
    private final StudentService studentService;
    @Autowired  //will map to @component
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public List<Student> getStudent() {
        return studentService.getStudents() ;
    }

    @PostMapping
    public void registerNewStudent(@RequestBody Student student){
        studentService.addNewStudent(student) ;
    }

    @DeleteMapping(path= "{studentId}" )
    public void deleteStudent(@PathVariable("studentId") Long studentId){
        studentService.deleteStudent(studentId) ;
    }



    @PutMapping(path = "{studentId}") // Maps PUT requests to /api/v1/student/{id}
    public void updateStudent(
            @PathVariable("studentId") Long studentId, // Maps the dynamic ID from the URL path (e.g., .../student/1)
            @RequestParam(required = false) String name, // Maps the key from the query string (e.g., ...?name=Maria) required=false means the user can skip this param and the request won't fail
            @RequestParam(required = false) String email) {

        studentService.updateStudent(studentId, name, email);
    }




}

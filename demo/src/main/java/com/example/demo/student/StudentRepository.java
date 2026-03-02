package com.example.demo.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

//this file is for data link layer, so ,we need to link the service layer with this layer
//so, this interface is the component while StudentService will be autowired to it
//this interface is responsible for data access

/**
 * DATA ACCESS LAYER
 * -----------------
 * This interface is responsible for talking to the database.
 * * 1. Why is this an Interface?
 * We don't need to write the class implementation. Spring Data JPA auto-generates
 * the implementation at runtime (creating a proxy).
 * * 2. Why "extends JpaRepository"?
 * It inherits standard methods for CRUD operations (Create, Read, Update, Delete)
 * like .save(), .findAll(), .deleteById(), etc., so we don't have to write SQL.
 * * 3. Generics <Student, Long>:
 * - Student: The Entity type this repository manages.
 * - Long: The data type of the Primary Key (@Id) in the Student class.
 */

@Repository //same as @Component ,but more readable and context specific
public interface StudentRepository extends JpaRepository<Student, Long> {//this interface is responsible for data access

    /**
     * CUSTOM QUERY METHOD
     * -------------------
     * We just declare the method signature, and Spring generates the SQL.
     * * Naming Convention: "find" + "Student" + "By" + "Email"
     * Generated SQL: SELECT * FROM student WHERE email = ?
     * * @param email The email to search for.
     * @return Optional container (contains Student if found, or empty if not).
     */
    Optional<Student> findStudentByEmail(String email);//if ,found,then returns list of students

    void deleteById(Long studentId);
}

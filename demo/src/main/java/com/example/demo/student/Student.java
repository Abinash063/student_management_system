package com.example.demo.student;
import jakarta.persistence.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.Period;

@Entity  //mapping student to db(h2) like -> hibernate
@Table //it says create the table in the database h2 with the attributes of class Students
public class Student {
    @Id  //so, Long id will work as primary key
    @SequenceGenerator(
        name = "student_sequence",
        sequenceName = "student_sequence" ,
        allocationSize = 1  //fixing the sequnce to increment by 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "student_sequence"
    )
    //all the above 4 annotations are provided by spring data jpa

    private Long id ;
    private String name ;
    private String email;
    private LocalDate dob ;
    @Transient //this means ,we don't want age to be a coloumn of the db (it is a derived attribute)
    private Integer age ;

    public Student() {
    }

    public Student(Long id, String name, String email, LocalDate dob) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.dob = dob;
    }

    public Student(String name, String email, LocalDate dob) {
        this.name = name;
        this.email = email;
        this.dob = dob;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public Integer getAge() {
        return Period.between(this.dob, LocalDate.now() ).getYears() ;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", dob=" + dob +
                ", age=" + age +
                '}';
    }
}


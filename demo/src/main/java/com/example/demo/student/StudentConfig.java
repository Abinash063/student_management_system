package com.example.demo.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;


@Configuration
public class StudentConfig {


    /**
     * This is a "Startup Script".
     * Because we are using H2 (In-Memory Database), our data is lost every time we restart.
     * This code runs automatically on startup to re-insert our dummy data (Aman and Alex)
     * so we don't have an empty database while testing.
     */

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository repository) {
        return args -> {  //lambda function
            Student aman = new Student(
                    //1L, //no need of id ,since we have auto sequence
                    "aman",
                    "aman.sharma@gmail.com",
                    LocalDate.of(2000, Month.JANUARY, 5)
                    //no need of age ,as it is derived attribute
            );
            Student alex = new Student(
                    //1L,
                    "alex",
                    "alex.sharma@gmail.com",
                    LocalDate.of(2000, Month.JANUARY, 5)
            );

            repository.saveAll(
                    List.of(aman, alex)
            );
        };
    }
}

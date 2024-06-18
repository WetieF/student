package com.wetie.student;

import com.wetie.student.access.dataaccess.entities.Student;
import com.wetie.student.access.dataaccess.repositories.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class StudentApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudentApplication.class, args);
	}

	/*@Bean
	public CommandLineRunner commandLineRunner(StudentRepository studentRepository) {
		return args -> {
			Student student = new Student();
			student.setFirstName("Non");
			student.setLastName("Tapta");
			student.setPhoneNumber("1122345678");

			studentRepository.save(student);
		};
	}*/

	// @SpringBootTest creates an application context and loads full application context.
	// starts the embedded server, creates a web environment and then enables @Test methods to do integration testing

}

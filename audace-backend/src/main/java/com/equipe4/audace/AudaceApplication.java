package com.equipe4.audace;

import com.equipe4.audace.model.Student;
import com.equipe4.audace.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AudaceApplication {
	public static void main(String[] args) {
		SpringApplication.run(AudaceApplication.class, args);
	}

}

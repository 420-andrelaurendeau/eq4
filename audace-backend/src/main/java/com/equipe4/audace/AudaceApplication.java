package com.equipe4.audace;

import com.equipe4.audace.model.Employer;
import com.equipe4.audace.model.Manager;
import com.equipe4.audace.model.Student;
import com.equipe4.audace.model.department.Department;
import com.equipe4.audace.model.offer.Offer;
import com.equipe4.audace.repository.EmployerRepository;
import com.equipe4.audace.repository.ManagerRepository;
import com.equipe4.audace.repository.StudentRepository;
import com.equipe4.audace.repository.department.DepartmentRepository;
import com.equipe4.audace.repository.offer.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;

@SpringBootApplication
public class AudaceApplication implements CommandLineRunner {
	@Autowired
	private DepartmentRepository departmentRepository;
	@Autowired
	private EmployerRepository employerRepository;
	@Autowired
	private StudentRepository studentRepository;
	@Autowired
	private ManagerRepository managerRepository;
	public static void main(String[] args) {
		SpringApplication.run(AudaceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Department department = departmentRepository.save(new Department("GLO", "Génie logiciel"));
		Employer employer = new Employer(
				1L,
				"employer",
				"employerman",
				"temp@gmail.com",
				"password",
				"Temp Baklungel",
				"Big Baklunger",
				"123 Street Street",
				"1234567890",
				"-123"
		);

		Offer offer1 = new Offer("Stage en génie logiciel", "Stage en génie logiciel", new Date(), new Date(), new Date(), employer, department);
		Offer offer4 = new Offer("Stage en génie logiciel", "Stage en génie logiciel", new Date(), new Date(), new Date(), employer, department);
		Offer offer2 = new Offer("Stage en génie logiciel", "Stage en génie logiciel", new Date(), new Date(), new Date(), employer, department);
		Offer offer3 = new Offer("Stage en génie logiciel", "Stage en génie logiciel", new Date(), new Date(), new Date(), employer, department);
		employer.getOffers().add(offer1);
		employer.getOffers().add(offer2);
		employer.getOffers().add(offer3);
		employer.getOffers().add(offer4);
		employerRepository.save(employer);

		Student student = new Student(2L, "Kylian", "Mbappe", "kylian@live.fr", "123123", "34 de Montpellier", "4387654545", "2080350", department);
		studentRepository.save(student);

		Manager manager = new Manager(3L, "manager", "managerman", "manager@email.com", "password", "yeete", "1234567890", department);
		managerRepository.save(manager);
	}
}

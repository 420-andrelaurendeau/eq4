package com.equipe4.audace;

import com.equipe4.audace.dto.EmployerDTO;
import com.equipe4.audace.model.Employer;
import com.equipe4.audace.model.Manager;
import com.equipe4.audace.model.Student;
import com.equipe4.audace.model.department.Department;
import com.equipe4.audace.model.offer.Offer;
import com.equipe4.audace.model.security.Salt;
import com.equipe4.audace.repository.EmployerRepository;
import com.equipe4.audace.repository.ManagerRepository;
import com.equipe4.audace.repository.department.DepartmentRepository;
import com.equipe4.audace.repository.security.SaltRepository;
import com.equipe4.audace.service.EmployerService;
import com.equipe4.audace.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

@SpringBootApplication
@AllArgsConstructor
public class AudaceApplication implements CommandLineRunner {
	private DepartmentRepository departmentRepository;
	private EmployerRepository employerRepository;
	private ManagerRepository managerRepository;
	private EmployerService employerService;
	private StudentService studentService;
	private SaltRepository saltRepository;

	public static void main(String[] args) {
		SpringApplication.run(AudaceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Department department = departmentRepository.save(new Department("GLO", "Génie logiciel"));
		Employer employer = new Employer(
				null,
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

		Optional<EmployerDTO> optionalEmpDto = employerService.createEmployer(employer.toDTO());

		if (optionalEmpDto.isEmpty()) {
			return;
		}

		EmployerDTO empDto = optionalEmpDto.orElseThrow();
		employer = employerRepository.findById(empDto.getId()).orElseThrow();

		Offer offer1 = new Offer("Stage en génie spaget car c'est bon du spaget pour le dîner miam", "Stage en génie logiciel", new Date(), new Date(), new Date(), employer, department);
		offer1.setStatus(Offer.Status.ACCEPTED);
		Offer offer4 = new Offer("Stage en génie logiciel", "Stage en génie logiciel", new Date(), new Date(), new Date(), employer, department);
		offer4.setStatus(Offer.Status.ACCEPTED);
		Offer offer2 = new Offer("Stage en génie logiciel", "Stage en génie logiciel", new Date(), new Date(), new Date(), employer, department);
		Offer offer3 = new Offer("Stage en génie logiciel", "Stage en génie logiciel", new Date(), new Date(), new Date(), employer, department);
		employer.setOffers(new ArrayList<>());
		employer.getOffers().add(offer1);
		employer.getOffers().add(offer2);
		employer.getOffers().add(offer3);
		employer.getOffers().add(offer4);

		employerRepository.save(employer);

		Student student = new Student(2L, "Kylian", "Mbappe", "kylian@live.fr", "123123", "34 de Montpellier", "4387654545", "2080350", department);
		studentService.createStudent(student.toDTO(), department.getCode());

		Manager manager = new Manager(3L, "manager", "managerman", "manager@email.com", "password", "yeete", "1234567890", department);
		String managerPassword = manager.getPassword();
		String managerSalt = BCrypt.gensalt();
		manager.setPassword(BCrypt.hashpw(managerPassword, managerSalt));
		saltRepository.save(new Salt(null, manager, managerSalt));
		managerRepository.save(manager);
	}
}

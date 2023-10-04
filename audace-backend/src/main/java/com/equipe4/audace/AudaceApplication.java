package com.equipe4.audace;

import com.equipe4.audace.dto.EmployerDTO;
import com.equipe4.audace.dto.offer.OfferDTO;
import com.equipe4.audace.model.Manager;
import com.equipe4.audace.model.Student;
import com.equipe4.audace.model.department.Department;
import com.equipe4.audace.repository.EmployerRepository;
import com.equipe4.audace.repository.ManagerRepository;
import com.equipe4.audace.repository.StudentRepository;
import com.equipe4.audace.repository.department.DepartmentRepository;
import com.equipe4.audace.service.EmployerService;
import com.equipe4.audace.service.ManagerService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;

@SpringBootApplication
@AllArgsConstructor
public class AudaceApplication implements CommandLineRunner {
	@Autowired
	private DepartmentRepository departmentRepository;

	private EmployerService employerService;
	private ManagerRepository managerRepository;
	private StudentRepository studentRepository;

	public static void main(String[] args) {
		SpringApplication.run(AudaceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Department department = departmentRepository.save(new Department("GLO", "Génie logiciel"));

		employerService.createEmployer(EmployerDTO.employerDTOBuilder()
				.firstName("Employer1").lastName("Employer1").email("employer1@gmail.com").password("123456eE")
				.organisation("Organisation1").position("Position1").phone("123-456-7890").extension("12345")
				.address("Class Service, Javatown, Qc H8N1C1").build());

		employerService.createOffer(OfferDTO.offerDTOBuilder()
				.title("Stage en génie logiciel").description("Stage en génie logiciel")
				.internshipStartDate(LocalDate.now()).internshipEndDate(LocalDate.now()).offerEndDate(LocalDate.now())
				.employerId(1L).departmentCode("GLO")
				.build());

		managerRepository.save(new Manager(2L, "Manager1", "Manager1", "manager@email.com", "123456eE", "adress", "phone",department));
		studentRepository.save(new Student(3L, "Student1", "Student1", "student@email.com", "asdasd",
				"adress", "phone", "yaint", department));
	}
}

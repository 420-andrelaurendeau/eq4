package com.equipe4.audace;

import com.equipe4.audace.dto.EmployerDTO;
import com.equipe4.audace.dto.offer.OfferDTO;
import com.equipe4.audace.model.department.Department;
import com.equipe4.audace.repository.EmployerRepository;
import com.equipe4.audace.repository.ManagerRepository;
import com.equipe4.audace.repository.StudentRepository;
import com.equipe4.audace.repository.department.DepartmentRepository;
import com.equipe4.audace.service.EmployerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;

@SpringBootApplication
public class AudaceApplication implements CommandLineRunner {
	@Autowired
	private DepartmentRepository departmentRepository;

	private EmployerService employerService;

	public AudaceApplication(DepartmentRepository departmentRepository, EmployerService employerService) {
		this.departmentRepository = departmentRepository;
		this.employerService = employerService;
	}

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


	}
}

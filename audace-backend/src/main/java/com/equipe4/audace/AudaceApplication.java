package com.equipe4.audace;

import com.equipe4.audace.dto.EmployerDTO;
import com.equipe4.audace.dto.offer.OfferDTO;
import com.equipe4.audace.model.Employer;
import com.equipe4.audace.model.Student;
import com.equipe4.audace.model.cv.Cv;
import com.equipe4.audace.model.department.Department;
import com.equipe4.audace.model.offer.Offer;
import com.equipe4.audace.repository.EmployerRepository;
import com.equipe4.audace.repository.StudentRepository;
import com.equipe4.audace.repository.cv.CvRepository;
import com.equipe4.audace.repository.department.DepartmentRepository;
import com.equipe4.audace.service.EmployerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.util.Optional;

@SpringBootApplication
public class AudaceApplication implements CommandLineRunner {
	@Autowired
	private DepartmentRepository departmentRepository;

	@Autowired
	private StudentRepository studentRepository;
	@Autowired
	private EmployerRepository employerRepository;
	@Autowired
	private CvRepository cvRepository;
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
		Department department = departmentRepository.save(new Department(null, "GLO", "Génie logiciel"));

		Optional<EmployerDTO> optionalEmployerDTO = employerService.createEmployer(
				new EmployerDTO(
						null,
						"employer",
						"employerMan",
						"employer@email.com",
						"password",
						"organisation",
						"position",
						"123 Street Street",
						"1234567890",
						"-123"
				)
		);

		if (optionalEmployerDTO.isEmpty()) {
			return;
		}

		EmployerDTO employerDTO = optionalEmployerDTO.get();

		employerService.createOffer(
				new OfferDTO(
						null,
						"Stage en génie logiciel PROTOTYPE",
						"Stage en génie logiciel",
						LocalDate.now(),
						LocalDate.now(),
						LocalDate.now(),
						3,
						Offer.Status.ACCEPTED,
						department.toDTO(),
						employerDTO
				)
		);

		Student student = new Student(
				null,
				"student",
				"studentman",
				"student@email.com",
				"password",
				"123 Street Street",
				"1234567890",
				"123456789",
				department
		);

		studentRepository.save(student);

		Employer employer = employerDTO.fromDTO();

		Offer offer1 = new Offer(
				null,
				"Stage en génie logiciel PROTOTYPE",
				"Stage en génie logiciel",
				LocalDate.now(),
				LocalDate.now(),
				LocalDate.now(),
				3,
				department,
				employer
		);

		Offer offer2 = new Offer(
				null,
				"Stage en génie logiciel chez Roc-a-Fella Records",
				"Stage en génie logiciel",
				LocalDate.now(),
				LocalDate.now(),
				LocalDate.now(),
				3,
				department,
				employer
		);
		offer2.setStatus(Offer.Status.ACCEPTED);

		Offer offer3 = new Offer(
				null,
				"Stage en génie logiciel chez Google",
				"Stage en génie logiciel",
				LocalDate.now(),
				LocalDate.now(),
				LocalDate.now(),
				3,
				department,
				employer
		);

		Offer offer4 = new Offer(
				null,
				"Stage en génie logiciel chez Microsoft",
				"Stage en génie logiciel",
				LocalDate.now(),
				LocalDate.now(),
				LocalDate.now(),
				3,
				department,
				employer
		);

		employer.getOffers().add(offer1);
		employer.getOffers().add(offer2);
		employer.getOffers().add(offer3);
		employer.getOffers().add(offer4);
		employerRepository.save(employer);

		byte[] content = new byte[10];
		Cv cv = new Cv(
				null,
				student,
				content,
				"cv.pdf"
		);
		cvRepository.save(cv);
	}
}

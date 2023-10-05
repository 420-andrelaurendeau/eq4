package com.equipe4.audace;

import com.equipe4.audace.dto.EmployerDTO;
import com.equipe4.audace.dto.cv.CvDTO;
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
import com.equipe4.audace.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.util.Date;

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

		Student student = new Student(2L, "Kylian", "Mbappe", "kylian@live.fr", "123123", "34 de Montpellier", "4387654545", "2080350", department);
		studentRepository.save(student);

		Employer employer = new Employer(
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
		Offer offer1 = new Offer("Stage en génie logiciel PROTOTYPE", "Stage en génie logiciel", LocalDate.now(), LocalDate.now(), LocalDate.now(), 3, department, employer);
		offer1.setStatus(Offer.Status.ACCEPTED);

		Offer offer2 = new Offer("Stage en génie logiciel chez Roc-a-Fella Records", "Stage en génie logiciel", LocalDate.now(), LocalDate.now(), LocalDate.now(), 3, department, employer);
		offer2.setStatus(Offer.Status.ACCEPTED);

		Offer offer3 = new Offer("Stage en génie logiciel chez Google", "Stage en génie logiciel", LocalDate.now(), LocalDate.now(), LocalDate.now(), 3, department, employer);
		Offer offer4 = new Offer("Stage en génie logiciel chez Microsoft", "Stage en génie logiciel", LocalDate.now(), LocalDate.now(), LocalDate.now(), 3, department, employer);
		employer.getOffers().add(offer1);
		employer.getOffers().add(offer2);
		employer.getOffers().add(offer3);
		employer.getOffers().add(offer4);
		employerRepository.save(employer);

		byte[] content = new byte[10];
		Cv cv = new Cv(100L, student, "cv.pdf", content);
		cvRepository.save(cv);
	}
}

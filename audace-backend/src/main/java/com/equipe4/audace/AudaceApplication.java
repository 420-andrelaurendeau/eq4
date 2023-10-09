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

		Student student = Student.studentBuilder()
				.firstname("Kylian")
				.lastname("Mbappe")
				.email("kylian@live.fr")
				.password("123123")
				.address("34 de Montpellier")
				.phone("4387654545")
				.studentNumber("2080350")
				.department(department)
				.build();
		student.setId(2L);

		studentRepository.save(student);

		Employer employer = Employer.employerBuilder()
				.firstName("employer")
				.lastName("employerman")
				.email("temp@gmail.com")
				.password("password")
				.organisation("Temp Baklungel")
				.position("Big Baklunger")
				.address("123 Street Street")
				.phone("1234567890")
				.extension("-123")
				.build();

		Offer offer1 = Offer.offerBuilder()
				.title("Stage en génie logiciel PROTOTYPE")
				.description("Stage en génie logiciel")
				.internshipStartDate(LocalDate.now())
				.internshipEndDate(LocalDate.now())
				.offerEndDate(LocalDate.now())
				.availablePlaces(3)
				.department(department)
				.employer(employer)
				.build();
		offer1.setStatus(Offer.Status.ACCEPTED);

		Offer offer2 = Offer.offerBuilder()
				.title("Stage en génie logiciel chez Roc-a-Fella Records")
				.description("Stage en génie logiciel")
				.internshipStartDate(LocalDate.now())
				.internshipEndDate(LocalDate.now())
				.offerEndDate(LocalDate.now())
				.availablePlaces(3)
				.department(department)
				.employer(employer)
				.build();
		offer2.setStatus(Offer.Status.ACCEPTED);

		Offer offer3 = Offer.offerBuilder()
				.title("Stage en génie logiciel chez Google")
				.description("Stage en génie logiciel")
				.internshipStartDate(LocalDate.now())
				.internshipEndDate(LocalDate.now())
				.offerEndDate(LocalDate.now())
				.availablePlaces(3)
				.department(department)
				.employer(employer)
				.build();
		Offer offer4 = Offer.offerBuilder()
				.title("Stage en génie logiciel chez Microsoft")
				.description("Stage en génie logiciel")
				.internshipStartDate(LocalDate.now())
				.internshipEndDate(LocalDate.now())
				.offerEndDate(LocalDate.now())
				.availablePlaces(3)
				.department(department)
				.employer(employer)
				.build();

		employer.getOffers().add(offer1);
		employer.getOffers().add(offer2);
		employer.getOffers().add(offer3);
		employer.getOffers().add(offer4);
		employerRepository.save(employer);

		byte[] content = new byte[10];
		Cv cv = Cv.cvBuilder()
				.student(student)
				.fileName("cv.pdf")
				.content(content)
				.build();
		cv.setId(100L);
		cvRepository.save(cv);
	}
}

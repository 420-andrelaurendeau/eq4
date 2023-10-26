package com.equipe4.audace;

import com.equipe4.audace.dto.EmployerDTO;
import com.equipe4.audace.dto.StudentDTO;
import com.equipe4.audace.dto.offer.OfferDTO;
import com.equipe4.audace.model.Employer;
import com.equipe4.audace.model.Manager;
import com.equipe4.audace.model.Student;
import com.equipe4.audace.model.application.Application;
import com.equipe4.audace.model.cv.Cv;
import com.equipe4.audace.model.department.Department;
import com.equipe4.audace.model.offer.Offer;
import com.equipe4.audace.model.security.Salt;
import com.equipe4.audace.repository.EmployerRepository;
import com.equipe4.audace.repository.ManagerRepository;
import com.equipe4.audace.repository.application.ApplicationRepository;
import com.equipe4.audace.repository.cv.CvRepository;
import com.equipe4.audace.repository.department.DepartmentRepository;
import com.equipe4.audace.repository.offer.OfferRepository;
import com.equipe4.audace.repository.security.SaltRepository;
import com.equipe4.audace.service.EmployerService;
import com.equipe4.audace.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.time.LocalDate;
import java.util.Optional;

@SpringBootApplication
@AllArgsConstructor
public class AudaceApplication implements CommandLineRunner {
	private DepartmentRepository departmentRepository;
	private EmployerRepository employerRepository;
	private EmployerService employerService;
	private CvRepository cvRepository;
	private SaltRepository saltRepository;
	private ManagerRepository managerRepository;
	private StudentService studentService;
	private ApplicationRepository applicationRepository;
	private final OfferRepository offerRepository;

	public static void main(String[] args) {
		SpringApplication.run(AudaceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Department department = departmentRepository.save(new Department(null, "GLO", "Génie logiciel"));

		Optional<EmployerDTO> optionalEmployerDTO = employerService.createEmployer(new EmployerDTO(1L, "employer", "employerman", "employer@email.com", "password", "Temp Baklungel", "Big Baklunger", "123 Street Street", "1234567890", "-123"));
		if (optionalEmployerDTO.isEmpty()) return;
		EmployerDTO employerDTO = optionalEmployerDTO.get();
		Employer employer = employerDTO.fromDTO();

		employerService.createOffer(new OfferDTO(null, "Stage en génie logiciel PROTOTYPE", "Stage en génie logiciel", LocalDate.now(), LocalDate.now(), LocalDate.now(), 3, Offer.OfferStatus.ACCEPTED, department.toDTO(), employerDTO));

		Student student = new Student(null, "Kylian", "Mbappe", "kylian@live.fr", "123123", "34 de Montpellier", "4387654545", "2080350", department);
		Optional<StudentDTO> optionalStudent = studentService.createStudent(student.toDTO(), department.getCode());
		if (optionalStudent.isEmpty()) return;
		student = optionalStudent.get().fromDTO();

		Student student2 = new Student(null, "student", "studentman", "student@email.com", "password", "123 Street Street", "1234567890", "123456789", department);
		Optional<StudentDTO> optionalStudent2 = studentService.createStudent(student2.toDTO(), department.getCode());
		if (optionalStudent2.isEmpty()) return;
		student2 = optionalStudent2.get().fromDTO();

		Offer offer1 = new Offer(null,"Stage en génie spaget car c'est bon du spaget pour le dîner miam", "Stage en génie logiciel", LocalDate.now(), LocalDate.now(), LocalDate.now(), 3, department, employer);
		offer1.setOfferStatus(Offer.OfferStatus.ACCEPTED);

		Offer offer2 = new Offer(null,"Stage en génie logiciel chez Roc-a-Fella Records", "Stage en génie logiciel", LocalDate.now(), LocalDate.now(), LocalDate.now(), 3, department, employer);
		offer2.setOfferStatus(Offer.OfferStatus.ACCEPTED);

		Offer offer3 = new Offer(null,"Stage en génie logiciel chez Google", "Stage en génie logiciel", LocalDate.now(), LocalDate.now(), LocalDate.now(), 3, department, employer);

		Offer offer4 = new Offer(null,"Stage en génie logiciel chez Microsoft", "Stage en génie logiciel", LocalDate.now(), LocalDate.now(), LocalDate.now(), 3, department, employer);
		offer4.setOfferStatus(Offer.OfferStatus.ACCEPTED);

		//employer.getOffers().add(offer1);
		employer.getOffers().add(offer2);
		employer.getOffers().add(offer3);
		employer.getOffers().add(offer4);
		employerRepository.save(employer);

		String cvContent = "cv content for fun";
		byte[] content = cvContent.getBytes();

		Cv cv1 = new Cv(1L, "cv.pdf", content, student);
		cv1.setCvStatus(Cv.CvStatus.ACCEPTED);
		cvRepository.save(cv1);

		Cv cv2 = new Cv(2L, "cv.pdf", content, student2);
		cv2.setCvStatus(Cv.CvStatus.ACCEPTED);
		cvRepository.save(cv2);

		offerRepository.save(offer1);
		Application application1 = new Application(1L, cv1, offer1);
		Application application2 = new Application(2L, cv2, offer1);

		applicationRepository.save(application1);
		applicationRepository.save(application2);


		Manager manager = new Manager(null, "manager", "managerman", "manager@email.com", "password", "yeete", "1234567890", department);
		managerRepository.save(manager);

		String managerPassword = manager.getPassword();
		String managerSalt = BCrypt.gensalt();
		manager.setPassword(BCrypt.hashpw(managerPassword, managerSalt));
		manager = managerRepository.save(manager);
		saltRepository.save(new Salt(null, manager, managerSalt));
	}
}

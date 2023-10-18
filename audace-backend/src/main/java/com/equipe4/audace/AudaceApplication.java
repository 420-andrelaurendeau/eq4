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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.time.LocalDate;
import java.util.*;

@SpringBootApplication
public class AudaceApplication implements CommandLineRunner {
	private DepartmentRepository departmentRepository;
	private EmployerRepository employerRepository;
	private CvRepository cvRepository;
	private SaltRepository saltRepository;
	private ManagerRepository managerRepository;
	private EmployerService employerService;
	private StudentService studentService;
	private ApplicationRepository applicationRepository;
	private OfferRepository offerRepository;

	public AudaceApplication(DepartmentRepository departmentRepository, EmployerRepository employerRepository, CvRepository cvRepository, SaltRepository saltRepository, ManagerRepository managerRepository, EmployerService employerService, StudentService studentService, ApplicationRepository applicationRepository, OfferRepository offerRepository) {
		this.departmentRepository = departmentRepository;
		this.employerRepository = employerRepository;
		this.cvRepository = cvRepository;
		this.saltRepository = saltRepository;
		this.managerRepository = managerRepository;
		this.employerService = employerService;
		this.studentService = studentService;
		this.applicationRepository = applicationRepository;
		this.offerRepository = offerRepository;
	}


	public static void main(String[] args) {
		SpringApplication.run(AudaceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Department department = departmentRepository.save(new Department(null, "GLO", "Génie logiciel"));

		Optional<EmployerDTO> optionalEmployerDTO = employerService.createEmployer(new EmployerDTO(1L, "employer", "employerman", "temp@gmail.com", "password", "Temp Baklungel", "Big Baklunger", "123 Street Street", "1234567890", "-123"));
		if (optionalEmployerDTO.isEmpty()) return;
		EmployerDTO employerDTO = optionalEmployerDTO.get();
		Employer employer = employerDTO.fromDTO();

		employerService.createOffer(
				new OfferDTO(
						null,
						"Stage en génie logiciel PROTOTYPE",
						"Stage en génie logiciel",
						LocalDate.now(),
						LocalDate.now(),
						LocalDate.now(),
						3,
						Offer.OfferStatus.ACCEPTED,
						department.toDTO(),
						employerDTO
				)
		);
		employerService.createOffer(new OfferDTO(null, "Stage en génie logiciel PROTOTYPE", "Stage en génie logiciel", LocalDate.now(), LocalDate.now(), LocalDate.now(), 3, Offer.OfferStatus.ACCEPTED, department.toDTO(), employerDTO));

		Student student = new Student(2L, "Kylian", "Mbappe", "kylian@live.fr", "123123", "34 de Montpellier", "4387654545", "2080350", department);
		Optional<StudentDTO> optionalStudent = studentService.createStudent(student.toDTO(), department.getCode());
		if (optionalStudent.isEmpty()) return;
		student = optionalStudent.get().fromDTO();


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
		offer2.setOfferStatus(Offer.OfferStatus.ACCEPTED);
		Offer offer1 = new Offer(null,"Stage en génie spaget car c'est bon du spaget pour le dîner miam", "Stage en génie logiciel", LocalDate.now(), LocalDate.now(), LocalDate.now(), 3, department, employer);
		offer1.setOfferStatus(Offer.OfferStatus.ACCEPTED);

		Offer offer3 = new Offer(null,"Stage en génie logiciel chez Google", "Stage en génie logiciel", LocalDate.now(), LocalDate.now(), LocalDate.now(), 3, department, employer);

		Offer offer4 = new Offer(null,"Stage en génie logiciel chez Microsoft", "Stage en génie logiciel", LocalDate.now(), LocalDate.now(), LocalDate.now(), 3, department, employer);
		offer4.setOfferStatus(Offer.OfferStatus.ACCEPTED);

		employer.getOffers().add(offer1);
		employer.getOffers().add(offer2);
		employer.getOffers().add(offer3);
		employer.getOffers().add(offer4);
		employerRepository.save(employer);

		String cvContent = "cv content for fun";
		byte[] content = cvContent.getBytes();

		Cv cv = new Cv(1L, student, content, "cv.pdf");
		cvRepository.save(cv);

		Manager manager = new Manager(3L, "manager", "managerman", "manager@email.com", "password", "yeete", "1234567890", department);
		managerRepository.save(manager);

		String managerPassword = manager.getPassword();
		String managerSalt = BCrypt.gensalt();
		manager.setPassword(BCrypt.hashpw(managerPassword, managerSalt));
		manager = managerRepository.save(manager);
		saltRepository.save(new Salt(null, manager, managerSalt));
		managerRepository.save(manager);

		Application application = new Application(null, cv, offerRepository.findById(1L).orElseThrow());
		applicationRepository.save(application);
	}
}

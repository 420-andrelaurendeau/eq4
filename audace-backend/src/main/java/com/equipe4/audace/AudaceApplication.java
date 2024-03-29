package com.equipe4.audace;

import com.equipe4.audace.dto.EmployerDTO;
import com.equipe4.audace.dto.StudentDTO;
import com.equipe4.audace.model.Employer;
import com.equipe4.audace.model.Manager;
import com.equipe4.audace.model.Student;
import com.equipe4.audace.model.application.Application;
import com.equipe4.audace.model.cv.Cv;
import com.equipe4.audace.model.department.Department;
import com.equipe4.audace.model.offer.Offer;
import com.equipe4.audace.model.security.Salt;
import com.equipe4.audace.model.session.OfferSession;
import com.equipe4.audace.model.session.Session;
import com.equipe4.audace.repository.ManagerRepository;
import com.equipe4.audace.repository.application.ApplicationRepository;
import com.equipe4.audace.repository.contract.ContractRepository;
import com.equipe4.audace.repository.cv.CvRepository;
import com.equipe4.audace.repository.department.DepartmentRepository;
import com.equipe4.audace.repository.offer.OfferRepository;
import com.equipe4.audace.repository.security.SaltRepository;
import com.equipe4.audace.repository.session.OfferSessionRepository;
import com.equipe4.audace.repository.session.SessionRepository;
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
	private EmployerService employerService;
	private SaltRepository saltRepository;
	private ManagerRepository managerRepository;
	private StudentService studentService;
	private SessionRepository sessionRepository;
	private OfferRepository offerRepository;
	private OfferSessionRepository offerSessionRepository;
	private ApplicationRepository applicationRepository;
	private CvRepository cvRepository;
	private ContractRepository contractRepository;

	public static void main(String[] args) {
		SpringApplication.run(AudaceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Session winter23 = sessionRepository.save(new Session(null, LocalDate.of(2023, 1, 20), LocalDate.of(2023, 5, 19)));
		Session summer23 = sessionRepository.save(new Session(null, LocalDate.of(2023, 5, 20), LocalDate.of(2023, 8, 19)));
		Session fall23 = sessionRepository.save(new Session(null, LocalDate.of(2023, 8, 20), LocalDate.of(2023, 12, 23)));
		Session winter24 = sessionRepository.save(new Session(null, LocalDate.of(2024, 1, 20), LocalDate.of(2024, 5, 19)));

		Department department = departmentRepository.save(new Department(null, "GLO", "Génie logiciel"));

		Optional<EmployerDTO> optionalEmployerDTO = employerService.createEmployer(new EmployerDTO(1L, "Earl", "Sinclaire", "esinclaire@email.com", "password", "Temp Baklungel", "Big Baklunger", "123 Street Street", "5143643320", "45123"));
		if (optionalEmployerDTO.isEmpty()) return;
		EmployerDTO employerDTO = optionalEmployerDTO.get();
		Employer employer = employerDTO.fromDTO();

		Optional<StudentDTO> optionalStudent = studentService.createStudent(new StudentDTO(null, "Kylian", "Mbappe", "kylian@live.fr", "34 de Montpellier", "4387654545", "123123", "2080350", department.toDTO()), department.getCode());
		if (optionalStudent.isEmpty()) return;
		StudentDTO studentDTO = optionalStudent.get();
		Student student = studentDTO.fromDTO();

		Optional<StudentDTO> optionalStudent2 = studentService.createStudent(new StudentDTO(null, "Ethyl", "Hinkleman", "hinkleman@email.com", "2469 Fallon Drive", "5192477059", "password", "20834534", department.toDTO()), department.getCode());
		if (optionalStudent2.isEmpty()) return;
		StudentDTO studentDTO2 = optionalStudent2.get();
		Student student2 = studentDTO2.fromDTO();

		Optional<StudentDTO> optionalStudent3 = studentService.createStudent(new StudentDTO(null, "Edmonton", "Mundare", "mundare@email.com", "4696 Main St", "7807647135", "password", "1974532", department.toDTO()), department.getCode());
		if (optionalStudent3.isEmpty()) return;
		StudentDTO studentDTO3 = optionalStudent3.get();
		Student student3 = studentDTO3.fromDTO();

		Offer offer2 = offerRepository.save(
                new Offer(null, "Stage en génie logiciel chez Roc-a-Fella Records", "Stage en génie logiciel", LocalDate.of(2024, 1, 22), LocalDate.of(2024, 1, 22).plusWeeks(15), LocalDate.of(2023, 10, 22).plusMonths(1), 2, department, employer)
		);
        offer2.setOfferStatus(Offer.OfferStatus.ACCEPTED);
        offer2 = offerRepository.save(offer2);
		offerSessionRepository.save(new OfferSession(null, offer2, winter24));

        Offer offer3 = offerRepository.save(
                new Offer(null, "Stage en génie logiciel chez Google", "Stage en génie logiciel", LocalDate.of(2024, 1, 22), LocalDate.of(2024, 1, 22).plusWeeks(15), LocalDate.of(2023, 10, 22).plusMonths(1), 3, department, employer)
		);
        offerSessionRepository.save(new OfferSession(null, offer3, winter24));

        Offer offer4 = offerRepository.save(
                new Offer(null, "Stage en génie logiciel chez Microsoft", "Stage en génie logiciel", LocalDate.of(2023, 5, 22), LocalDate.of(2023, 5, 22).plusWeeks(12), LocalDate.of(2023, 2, 22).plusMonths(1), 3, department, employer)
		);
        offerSessionRepository.save(new OfferSession(null, offer4, summer23));

        Offer offer5 = offerRepository.save(
                new Offer(null, "Stage en génie logiciel chez Apple", "Stage en génie logiciel", LocalDate.of(2023, 1, 22), LocalDate.of(2023, 1, 22).plusWeeks(12), LocalDate.of(2022, 10, 22).plusMonths(1), 3, department, employer)
        );
        offerSessionRepository.save(new OfferSession(null, offer5, winter23));

		String cvContent = "cv content for fun";
		byte[] content = cvContent.getBytes();

		Cv cv1 = new Cv(1L, "cvKylian.pdf", content, student);
		cv1.setCvStatus(Cv.CvStatus.ACCEPTED);
		cvRepository.save(cv1);

		Cv cv2 = new Cv(2L, "cvEthyl.pdf", content, student2);
		cv2.setCvStatus(Cv.CvStatus.ACCEPTED);
		cvRepository.save(cv2);

		Cv cv3 = new Cv(3L, "cvEdmonton.pdf", content, student3);
		cv3.setCvStatus(Cv.CvStatus.ACCEPTED);
		cvRepository.save(cv3);

		applicationRepository.save(new Application(null, cv2, offer2));
		applicationRepository.save(new Application(null, cv3, offer2));

		Manager manager = new Manager(null, "Carl", "LaMontagne", "cmont@email.com", "password", "yeete", "1234567890", department);
		manager = managerRepository.save(manager);

		String managerPassword = manager.getPassword();
		String managerSalt = BCrypt.gensalt();
		manager.setPassword(BCrypt.hashpw(managerPassword, managerSalt));
		manager = managerRepository.save(manager);
		saltRepository.save(new Salt(null, manager, managerSalt));
	}
}

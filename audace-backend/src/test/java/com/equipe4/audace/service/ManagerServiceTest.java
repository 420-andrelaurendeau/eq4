package com.equipe4.audace.service;

import com.equipe4.audace.dto.ManagerDTO;
import com.equipe4.audace.dto.cv.CvDTO;
import com.equipe4.audace.dto.offer.OfferDTO;
import com.equipe4.audace.model.Employer;
import com.equipe4.audace.model.Manager;
import com.equipe4.audace.model.Student;
import com.equipe4.audace.model.cv.Cv;
import com.equipe4.audace.model.department.Department;
import com.equipe4.audace.model.offer.Offer;
import com.equipe4.audace.repository.ManagerRepository;
import com.equipe4.audace.repository.cv.CvRepository;
import com.equipe4.audace.repository.department.DepartmentRepository;
import com.equipe4.audace.repository.offer.OfferRepository;
import com.equipe4.audace.utils.SessionManipulator;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ManagerServiceTest {
    @Mock
    private OfferRepository offerRepository;
    @Mock
    private DepartmentRepository departmentRepository;
    @Mock
    private ManagerRepository managerRepository;
    @Mock
    private CvRepository cvRepository;
    @Mock
    private SessionManipulator sessionManipulator;
    @InjectMocks
    private ManagerService managerService;

    @Test
    public void acceptOffer() {
        Employer employer = mock(Employer.class);
        Department department = new Department(1L, "code", "name");
        Offer offer1 = new Offer(
                1L,
                "title",
                "description",
                LocalDate.now(),
                LocalDate.now(),
                LocalDate.now(),
                1,
                department,
                employer
        );
        Manager manager = new Manager(
                1L,
                "firstName",
                "lastName",
                "email",
                "password",
                "address",
                "phone",
                department
        );
        when(offerRepository.findById(anyLong())).thenReturn(Optional.of(offer1));
        when(offerRepository.save(any())).thenReturn(offer1);
        when(sessionManipulator.isOfferInCurrentSession(offer1)).thenReturn(true);
        when(managerRepository.findById(anyLong())).thenReturn(Optional.of(manager));

        managerService.acceptOffer(1L, 1L);

        assert(offer1.getOfferStatus() == Offer.OfferStatus.ACCEPTED);
    }

    @Test
    public void acceptOffer_InvalidId() {
        when(offerRepository.findById(1L)).thenThrow(EntityNotFoundException.class);
        assertThrows(EntityNotFoundException.class, () -> managerService.acceptOffer(1L, 1L));
    }
    @Test
    public void acceptOffer_wrongDepartment() {
        Employer employer = mock(Employer.class);
        Department department = new Department(1L, "code", "name");
        Department department2 = new Department(2L, "code2", "name2");
        Offer offer1 = new Offer(
                1L,
                "title",
                "description",
                LocalDate.now(),
                LocalDate.now(),
                LocalDate.now(),
                1,
                department,
                employer
        );
        Manager manager = new Manager(
                1L,
                "firstName",
                "lastName",
                "email",
                "password",
                "address",
                "phone",
                department2
        );
        when(offerRepository.findById(anyLong())).thenReturn(Optional.of(offer1));
        when(managerRepository.findById(anyLong())).thenReturn(Optional.of(manager));

        assertThatThrownBy(() -> managerService.acceptOffer(1L, 1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("The manager isn't in the right department");
    }

    @Test
    public void refuseOffer() {
        Employer employer = mock(Employer.class);
        Department department = new Department(1L, "code", "name");
        Offer offer1 = new Offer(
                1L,
                "title",
                "description",
                LocalDate.now(),
                LocalDate.now(),
                LocalDate.now(),
                1,
                department,
                employer
        );
        Manager manager = new Manager(
                1L,
                "firstName",
                "lastName",
                "email",
                "password",
                "address",
                "phone",
                department
        );
        when(offerRepository.findById(anyLong())).thenReturn(Optional.of(offer1));
        when(offerRepository.save(any())).thenReturn(offer1);
        when(sessionManipulator.isOfferInCurrentSession(offer1)).thenReturn(true);
        when(managerRepository.findById(anyLong())).thenReturn(Optional.of(manager));

        managerService.refuseOffer(1L, 1L);

        assert(offer1.getOfferStatus() == Offer.OfferStatus.REFUSED);
    }

    @Test
    public void refuseOffer_Invalid_Id() {
        when(offerRepository.findById(1L)).thenThrow(EntityNotFoundException.class);
        assertThrows(EntityNotFoundException.class, () -> managerService.refuseOffer(1L, 1L));
    }
    @Test
    public void refuseOffer_wrongDepartment() {
        Employer employer = mock(Employer.class);
        Department department = new Department(1L, "code", "name");
        Department department2 = new Department(2L, "code2", "name2");
        Offer offer1 = new Offer(
                1L,
                "title",
                "description",
                LocalDate.now(),
                LocalDate.now(),
                LocalDate.now(),
                1,
                department,
                employer
        );
        Manager manager = new Manager(
                1L,
                "firstName",
                "lastName",
                "email",
                "password",
                "address",
                "phone",
                department2
        );
        when(offerRepository.findById(anyLong())).thenReturn(Optional.of(offer1));
        when(managerRepository.findById(anyLong())).thenReturn(Optional.of(manager));

        assertThatThrownBy(() -> managerService.refuseOffer(1L, 1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("The manager isn't in the right department");
    }

    @Test
    void getOffersByDepartment_happyPath() {
        Department mockedDepartment = mock(Department.class);
        List<Offer> offers = new ArrayList<>();

        Employer fakeEmployer = new Employer(1L, "Employer1", "Employer1", "asd@email.com", "password", "Organisation1", "Position1", "123-456-7890", "12345", "Class Service, Javatown, Qc H8N1C1");
        Offer fakeOffer = new Offer(1L, "Stage en génie logiciel", "Stage en génie logiciel", LocalDate.now(), LocalDate.now(), LocalDate.now(), 3, mockedDepartment, fakeEmployer);

        fakeEmployer.getOffers().add(fakeOffer);

        for (int i = 0; i < 3; i++)
            offers.add(fakeOffer);

        when(departmentRepository.findById(anyLong())).thenReturn(Optional.of(mockedDepartment));
        when(offerRepository.findAllByDepartment(mockedDepartment)).thenReturn(offers);
        when(sessionManipulator.removeOffersNotInCurrentSession(offers)).thenReturn(offers);

        List<OfferDTO> result = managerService.getOffersByDepartment(1L);

        assertThat(result.size()).isEqualTo(offers.size());
        assertThat(result).containsExactlyInAnyOrderElementsOf(offers.stream().map(Offer::toDTO).toList());
    }

    @Test
    void getOffersByDepartment_departmentNotFound() {
        when(departmentRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> managerService.getOffersByDepartment(1L))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("Department not found");
    }

    @Test
    void getOffersByDepartment_noOffers() {
        Department mockedDepartment = mock(Department.class);

        when(departmentRepository.findById(anyLong())).thenReturn(Optional.of(mockedDepartment));
        when(offerRepository.findAllByDepartment(mockedDepartment)).thenReturn(new ArrayList<>());

        List<OfferDTO> result = managerService.getOffersByDepartment(1L);

        assertThat(result.size()).isEqualTo(0);
    }

    @Test
    public void findManagerById_happyPathTest() {
        // Arrange
        Department department = mock(Department.class);
        Manager manager = new Manager(1L, "manager", "managerman", "manager@email.com", "password", "yeete", "1234567890", department);

        when(managerRepository.findById(1L)).thenReturn(Optional.of(manager));

        // Act
        ManagerDTO result = managerService.getManagerById(1L).orElseThrow();

        // Assert
        assertThat(result.getFirstName()).isEqualTo("manager");
        assertThat(result.getLastName()).isEqualTo("managerman");
        assertThat(result.getEmail()).isEqualTo("manager@email.com");
    }

    @Test
    public void findManagerById_notFoundTest() {
        // Arrange
        when(managerRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        Optional<ManagerDTO> result = managerService.getManagerById(1L);

        // Assert
        assertThat(result.isEmpty()).isTrue();
    }

    @Test
    public void getCvsByDepartment_happyPath() {
        Department mockedDepartment = mock(Department.class);
        Student student = new Student(1L, "firstName", "lastName", "email", "password", "address", "phone", "studentNumber", mockedDepartment);
        List<Cv> listCvs = new ArrayList<>();
        listCvs.add(new Cv(null, student, new byte[10], "cv"));

        when(cvRepository.findAllByStudentDepartmentId(anyLong())).thenReturn(listCvs);

        List<CvDTO> result = managerService.getCvsByDepartment(1L);

        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    public void getCvsByDepartment_invalidDepartmentId() {
        when(cvRepository.findAllByStudentDepartmentId(anyLong())).thenReturn(new ArrayList<>());

        assertThat(managerService.getCvsByDepartment(1L).size()).isEqualTo(0);
    }

    @Test
    public void getCvsByDepartment_noCvs() {
        List<Cv> listCvs = new ArrayList<>();

        when(cvRepository.findAllByStudentDepartmentId(anyLong())).thenReturn(listCvs);

        List<CvDTO> result = managerService.getCvsByDepartment(1L);

        assertThat(result.size()).isEqualTo(0);
    }

    @Test
    public void acceptCv() {
        Student student = mock(Student.class);
        Cv cv = new Cv(null, student, "Monkey Enthusiast needs more sleep".getBytes(), "cv");
        Department department = new Department(2L, "code", "name");
        Manager manager = new Manager(
                1L,
                "firstName",
                "lastName",
                "email",
                "password",
                "address",
                "phone",
                department
        );
        when(cvRepository.findById(1L)).thenReturn(Optional.of(cv));
        when(cvRepository.save(any())).thenReturn(cv);
        when(cv.getStudent().getDepartment()).thenReturn(department);
        when(managerRepository.findById(anyLong())).thenReturn(Optional.of(manager));

        Optional<CvDTO> cvDTO = managerService.acceptCv(1L, 1L);
        if (cvDTO.isPresent()) {
            assert(cvDTO.get().getCvStatus() == Cv.CvStatus.ACCEPTED);
        }
        else {
            assert(false);
        }
    }

    @Test
    public void acceptCv_InvalidId() {
        when(cvRepository.findById(1L)).thenThrow(EntityNotFoundException.class);
        assertThrows(EntityNotFoundException.class, () -> managerService.acceptCv(1L, 1L));
    }

    @Test
    public void acceptCv_wrongDepartment() {
        Student student = mock(Student.class);
        Cv cv = new Cv(null, student, "Monkey Enthusiast needs more sleep".getBytes(), "cv");
        Department department = new Department(2L, "code", "name");
        Department department2 = new Department(3L, "code2", "name2");
        Manager manager = new Manager(
                1L,
                "firstName",
                "lastName",
                "email",
                "password",
                "address",
                "phone",
                department2
        );
        when(cvRepository.findById(1L)).thenReturn(Optional.of(cv));
        when(cv.getStudent().getDepartment()).thenReturn(department);
        when(managerRepository.findById(anyLong())).thenReturn(Optional.of(manager));

        assertThatThrownBy(() -> managerService.acceptCv(1L, 1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("The manager isn't in the right department");
    }

    @Test
    public void refuseCv() {
        Student student = mock(Student.class);
        Cv cv = new Cv(null, student, "Monkey Enthusiast needs more sleep".getBytes(), "cv");
        Department department = new Department(2L, "code", "name");
        Manager manager = new Manager(
                1L,
                "firstName",
                "lastName",
                "email",
                "password",
                "address",
                "phone",
                department
        );
        when(cvRepository.findById(1L)).thenReturn(Optional.of(cv));
        when(cvRepository.save(any())).thenReturn(cv);
        when(cv.getStudent().getDepartment()).thenReturn(department);
        when(managerRepository.findById(anyLong())).thenReturn(Optional.of(manager));

        Optional<CvDTO> cvDTO = managerService.refuseCv(1L, 1L);
        if (cvDTO.isPresent()) {
            assert(cvDTO.get().getCvStatus() == Cv.CvStatus.REFUSED);
        }
        else {
            assert(false);
        }
    }

    @Test
    public void refuseCv_Invalid_Id() {
        when(cvRepository.findById(1L)).thenThrow(EntityNotFoundException.class);
        assertThrows(EntityNotFoundException.class, () -> managerService.refuseCv(1L, 1L));
    }

    @Test
    public void refuseCv_wrongDepartment() {
        Student student = mock(Student.class);
        Cv cv = new Cv(null, student, "Monkey Enthusiast needs more sleep".getBytes(), "cv");
        Department department = new Department(2L, "code", "name");
        Department department2 = new Department(3L, "code2", "name2");
        Manager manager = new Manager(
                1L,
                "firstName",
                "lastName",
                "email",
                "password",
                "address",
                "phone",
                department2
        );
        when(cvRepository.findById(1L)).thenReturn(Optional.of(cv));
        when(cv.getStudent().getDepartment()).thenReturn(department);
        when(managerRepository.findById(anyLong())).thenReturn(Optional.of(manager));

        assertThatThrownBy(() -> managerService.refuseCv(1L, 1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("The manager isn't in the right department");
    }
}
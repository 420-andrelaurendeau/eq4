package com.equipe4.audace.service;

import com.equipe4.audace.dto.offer.OfferDTO;
import com.equipe4.audace.model.Employer;
import com.equipe4.audace.model.department.Department;
import com.equipe4.audace.model.offer.Offer;
import com.equipe4.audace.repository.EmployerRepository;
import com.equipe4.audace.repository.department.DepartmentRepository;
import com.equipe4.audace.repository.offer.OfferRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OfferServiceTest {

    @Mock
    private OfferRepository offerRepository;
    @Mock
    private DepartmentService departmentService;
    @Mock
    private EmployerService employerService;
    @InjectMocks
    private OfferService offerService;

    Department mockedDepartment;
    Employer fakeEmployer;

    @BeforeEach
    public void setUp() {
        mockedDepartment = mock(Department.class);
        fakeEmployer = new Employer(
                "employer",
                "employerman",
                "email@gmail.com",
                "password",
                "organisation",
                "position",
                "address",
                "phone",
                "extension"
        );
        fakeEmployer.setId(1L);
    }

    @Test
    public void createOffer_HappyPath(){
        Offer offer = Offer.offerBuilder()
                .title("Stage en génie logiciel").description("Stage en génie logiciel")
                .internshipStartDate(LocalDate.now()).internshipEndDate(LocalDate.now()).offerEndDate(LocalDate.now())
                .availablePlaces(3).employer(fakeEmployer).department(mockedDepartment)
                .build();
        OfferDTO offerDTO = new OfferDTO(offer);

        when(offerRepository.save(any(Offer.class))).thenReturn(offer);

        OfferDTO dto = offerService.createOffer(offerDTO).get();

        assertThat(dto.equals(offerDTO));
        verify(offerRepository, times(1)).save(offerDTO.fromDTO());
    }
    @Test
    void getOffers_HappyPath() {
        List<Offer> offers = new ArrayList<>();
        Offer offer1 = Offer.offerBuilder()
                .title("Stage en génie logiciel").description("Stage en génie logiciel")
                .internshipStartDate(LocalDate.now()).internshipEndDate(LocalDate.now()).offerEndDate(LocalDate.now())
                .availablePlaces(3).employer(fakeEmployer).department(mockedDepartment)
                .build();
        Offer offer2 = Offer.offerBuilder()
                .title("Stage en génie logiciel").description("Stage en génie logiciel")
                .internshipStartDate(LocalDate.now()).internshipEndDate(LocalDate.now()).offerEndDate(LocalDate.now())
                .availablePlaces(3).employer(fakeEmployer).department(mockedDepartment)
                .build();
        offers.add(offer1);
        offers.add(offer2);

        when(offerRepository.findAll()).thenReturn(offers);

        List<OfferDTO> offerDTOList = offerService.findAllOffers();

        assertThat(offerDTOList.size()).isEqualTo(2);
        verify(offerRepository, times(1)).findAll();
    }

    @Test
    public void deleteOffer_HappyPath(){
        Offer offer1 = Offer.offerBuilder()
                .title("Stage en génie logiciel").description("Stage en génie logiciel")
                .internshipStartDate(LocalDate.now()).internshipEndDate(LocalDate.now()).offerEndDate(LocalDate.now())
                .availablePlaces(3).employer(fakeEmployer).department(mockedDepartment)
                .build();
        offer1.setId(1L);
        when(offerRepository.findById(offer1.getId())).thenReturn(Optional.of(offer1));

        offerService.deleteOffer(offer1.getId());

        verify(offerRepository).deleteById(offer1.getId());
    }

    @Test
    public void deleteOffer_OfferDontExists() {
        when(offerRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> offerService.deleteOffer(1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Offer doesn't exists");
    }

    @Test
    public void updateOffer_HappyPath() {
        Offer offer = Offer.offerBuilder()
                .title("Stage en génie logiciel").description("Stage en génie logiciel")
                .internshipStartDate(LocalDate.now()).internshipEndDate(LocalDate.now()).offerEndDate(LocalDate.now())
                .availablePlaces(3).employer(fakeEmployer).department(mockedDepartment)
                .build();
        offer.setId(1L);

        when(offerRepository.save(any(Offer.class))).thenReturn(offer);
        when(offerRepository.findById(offer.getId())).thenReturn(Optional.of(offer));

        OfferDTO originalOffer = offerService.createOffer(new OfferDTO(offer)).get();

        offer.setAvailablePlaces(2);

        OfferDTO updatedOffer = offerService.updateOffer(new OfferDTO(offer)).get();

        verify(offerRepository).save(offer);
        verify(offerRepository).findById(offer.getId());
        assertThat(originalOffer.getAvailablePlaces()).isEqualTo(3);
        assertThat(updatedOffer.getAvailablePlaces()).isEqualTo(2);
    }
    @Test()
    public void updateOffer_OfferDontExists() {
        Offer offer = Offer.offerBuilder()
                .title("Stage en génie logiciel").description("Stage en génie logiciel")
                .internshipStartDate(LocalDate.now()).internshipEndDate(LocalDate.now()).offerEndDate(LocalDate.now())
                .availablePlaces(3).employer(fakeEmployer).department(mockedDepartment)
                .build();
        offer.setId(1L);

        when(offerRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> offerService.updateOffer(new OfferDTO(offer)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Offer doesn't exists");
    }
}

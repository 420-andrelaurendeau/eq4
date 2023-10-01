package com.equipe4.audace.service;

import com.equipe4.audace.model.Employer;
import com.equipe4.audace.model.department.Department;
import com.equipe4.audace.model.offer.Offer;
import com.equipe4.audace.repository.department.DepartmentRepository;
import com.equipe4.audace.repository.offer.OfferRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ManagerServiceTest {
    @Mock
    private OfferRepository offerRepository;
    @InjectMocks
    private ManagerService managerService;

    @Test
    public void acceptOffer() {
        Employer employer = new Employer();
        Department department = new Department();
        Offer offer1 = Offer.offerBuilder()
                .title("Stage en génie logiciel").description("Stage en génie logiciel")
                .internshipStartDate(LocalDate.now()).internshipEndDate(LocalDate.now()).offerEndDate(LocalDate.now())
                .availablePlaces(2)
                .employer(employer).department(department)
                .build();
        when(offerRepository.findById(1L)).thenReturn(Optional.of(offer1));
        when(offerRepository.save(any())).thenReturn(offer1);

        managerService.acceptOffer(1L);

        assert(offer1.getStatus() == Offer.Status.ACCEPTED);
    }

    @Test
    public void acceptOffer_InvalidId() {
        when(offerRepository.findById(1L)).thenThrow(EntityNotFoundException.class);
        assertThrows(EntityNotFoundException.class, () -> managerService.acceptOffer(1L));
    }

    @Test
    public void refuseOffer() {
        Employer employer = new Employer();
        Department department = new Department();
        Offer offer1 = Offer.offerBuilder()
                .title("Stage en génie logiciel").description("Stage en génie logiciel")
                .internshipStartDate(LocalDate.now()).internshipEndDate(LocalDate.now()).offerEndDate(LocalDate.now())
                .availablePlaces(2)
                .employer(employer).department(department)
                .build();
        when(offerRepository.findById(1L)).thenReturn(Optional.of(offer1));
        when(offerRepository.save(any())).thenReturn(offer1);

        managerService.refuseOffer(1L);

        assert(offer1.getStatus() == Offer.Status.REFUSED);
    }

    @Test
    public void refuseOffer_Invalid_Id() {
        when(offerRepository.findById(1L)).thenThrow(EntityNotFoundException.class);
        assertThrows(EntityNotFoundException.class, () -> managerService.refuseOffer(1L));
    }
}
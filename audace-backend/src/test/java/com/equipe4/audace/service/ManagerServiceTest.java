package com.equipe4.audace.service;

import com.equipe4.audace.model.Employer;
import com.equipe4.audace.model.department.Department;
import com.equipe4.audace.model.offer.Offer;
import com.equipe4.audace.repository.offer.OfferRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ManagerServiceTest {
    @Mock
    public OfferRepository offerRepository;
    @InjectMocks
    public ManagerService managerService;

    @Test
    public void acceptOffer() {
        Employer employer = new Employer();
        Department department = new Department();
        Offer offer1 = new Offer("Stage en génie logiciel", "Stage en génie logiciel", new Date(), new Date(), null, employer, department);
        when(offerRepository.getReferenceById(1L)).thenReturn(offer1);

        managerService.acceptOffer(1L);

        assert(offer1.getStatus() == Offer.Status.ACCEPTED);
    }

    @Test
    public void acceptOffer_InvalidId() { //TODO : Double check that it's fine
        when(offerRepository.getReferenceById(1L)).thenThrow(EntityNotFoundException.class);
        try {
            managerService.acceptOffer(1L);
        }
        catch (EntityNotFoundException e) { //TODO : I think there's a better way to check that but I ain't sure
            assert(true);
        }
        catch (Exception e) {
            assert(false);
        }
    }

    @Test
    public void refuseOffer() {
        Employer employer = new Employer();
        Department department = new Department();
        Offer offer1 = new Offer("Stage en génie logiciel", "Stage en génie logiciel", new Date(), new Date(), null, employer, department);
        when(offerRepository.getReferenceById(1L)).thenReturn(offer1);

        managerService.refuseOffer(1L);

        assert(offer1.getStatus() == Offer.Status.REFUSED);
    }

    @Test
    public void refuseOffer_Invalid_Id() { //TODO : Double check that it's fine
        when(offerRepository.getReferenceById(1L)).thenThrow(EntityNotFoundException.class);
        try {
            managerService.refuseOffer(1L);
        }
        catch (EntityNotFoundException e) { //TODO : I think there's a better way to check that but I ain't sure
            assert(true);
        }
        catch (Exception e) {
            assert(false);
        }
    }
}
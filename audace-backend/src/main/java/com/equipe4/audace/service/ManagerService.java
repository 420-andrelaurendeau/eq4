package com.equipe4.audace.service;

import com.equipe4.audace.dto.offer.OfferDTO;
import com.equipe4.audace.model.department.Department;
import com.equipe4.audace.model.offer.Offer;
import com.equipe4.audace.model.offer.Offer.Status;
import com.equipe4.audace.repository.department.DepartmentRepository;
import com.equipe4.audace.repository.offer.OfferRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ManagerService extends UserService {
    public ManagerService(OfferRepository offerRepository, DepartmentRepository departmentRepository) {
        super(offerRepository, departmentRepository);
    }
    @Transactional
    public Optional<OfferDTO> acceptOffer(Long offerId) {
        return setOfferStatus(offerId, Status.ACCEPTED);
    }

    @Transactional
    public Optional<OfferDTO> refuseOffer(Long offerId) {
        return setOfferStatus(offerId, Status.REFUSED);
    }

    private Optional<OfferDTO> setOfferStatus(Long offerId, Status status) {
        Offer offer = offerRepository.findById(offerId).orElseThrow();
        offer.setStatus(status);
        return Optional.of(offerRepository.save(offer).toDto());
    }

    @Transactional
    public List<OfferDTO> getOffersByDepartment(Long departmentId) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new NoSuchElementException("Department not found"));
        List<Offer> offers = offerRepository.findAllByDepartment(department);

        return offers.stream().map(Offer::toDto).toList();
    }
}

package com.equipe4.audace.service;

import com.equipe4.audace.dto.offer.OfferDTO;
import com.equipe4.audace.model.offer.Offer;
import com.equipe4.audace.model.offer.Offer.Status;
import com.equipe4.audace.repository.offer.OfferRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ManagerService {
    private final OfferRepository offerRepository;

    @Autowired
    public ManagerService(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }

    public Optional<OfferDTO> acceptOffer(Long offerId) {
        return setOfferStatus(offerId, Status.ACCEPTED);
    }
    public Optional<OfferDTO> refuseOffer(Long offerId) {
        return setOfferStatus(offerId, Status.REFUSED);
    }

    @Transactional
    private Optional<OfferDTO> setOfferStatus(Long offerId, Status status) {
        Offer offer = offerRepository.findById(offerId).orElseThrow();
        offer.setStatus(status);
        return Optional.of(offerRepository.save(offer).toDto());
    }
}

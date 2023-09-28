package com.equipe4.audace.service;

import com.equipe4.audace.dto.offer.OfferDTO;
import com.equipe4.audace.model.offer.Offer;
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

    @Transactional
    public Optional<OfferDTO> acceptOffer(Long offerId) {
        Optional<Offer> offerOptional = offerRepository.findById(offerId);
        if (offerOptional.isEmpty()) {
            throw new IllegalArgumentException("OfferId is invalid");
        }
        Offer offer = offerOptional.get();
        offer.setStatus(Offer.Status.ACCEPTED);
        return Optional.of(offerRepository.save(offer).toDto());
    }
    @Transactional
    public Optional<OfferDTO> refuseOffer(Long offerId) {
        Optional<Offer> offerOptional = offerRepository.findById(offerId);
        if (offerOptional.isEmpty()) {
            throw new IllegalArgumentException("OfferId is invalid");
        }
        Offer offer = offerOptional.get();
        offer.setStatus(Offer.Status.REFUSED);
        return Optional.of(offerRepository.save(offer).toDto());
    }
}

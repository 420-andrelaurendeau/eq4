package com.equipe4.audace.service;

import com.equipe4.audace.model.offer.Offer;
import com.equipe4.audace.repository.offer.OfferRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManagerService {
    private final OfferRepository offerRepository;

    @Autowired
    public ManagerService(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }

    @Transactional
    public void acceptOffer(Long offerId) {
        Offer offer = offerRepository.getReferenceById(offerId);
        offer.setStatus(Offer.Status.ACCEPTED);
        offerRepository.save(offer);
    }
    @Transactional
    public void refuseOffer(Long offerId) {
        Offer offer = offerRepository.getReferenceById(offerId);
        offer.setStatus(Offer.Status.REFUSED);
        offerRepository.save(offer);
    }
}

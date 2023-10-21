package com.equipe4.audace.service;

import com.equipe4.audace.dto.EmployerDTO;
import com.equipe4.audace.dto.offer.OfferDTO;
import com.equipe4.audace.model.Employer;
import com.equipe4.audace.model.offer.Offer;
import com.equipe4.audace.model.offer.OfferSession;
import com.equipe4.audace.model.session.Session;
import com.equipe4.audace.repository.EmployerRepository;
import com.equipe4.audace.repository.offer.OfferRepository;
import com.equipe4.audace.repository.offer.OfferSessionRepository;
import com.equipe4.audace.repository.security.SaltRepository;
import com.equipe4.audace.utils.SessionManipulator;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployerService extends GenericUserService<Employer> {
    private final EmployerRepository employerRepository;
    private final OfferRepository offerRepository;
    private final OfferSessionRepository offerSessionRepository;
    private final SessionManipulator sessionManipulator;

    public EmployerService(
            SaltRepository saltRepository,
            EmployerRepository employerRepository,
            OfferRepository offerRepository,
            OfferSessionRepository offerSessionRepository,
            SessionManipulator sessionManipulator
    ) {
        super(saltRepository);
        this.employerRepository = employerRepository;
        this.offerRepository = offerRepository;
        this.offerSessionRepository = offerSessionRepository;
        this.sessionManipulator = sessionManipulator;
    }

    @Transactional
    public Optional<EmployerDTO> createEmployer(EmployerDTO employerDTO){
        if (employerDTO == null) throw new IllegalArgumentException("Employer cannot be null");

        Optional<Employer> employerOptional = employerRepository.findByEmail(employerDTO.getEmail());

        if(employerOptional.isPresent()) throw new IllegalArgumentException("Email already in use");

        Employer newEmployer = employerDTO.fromDTO();
        hashAndSaltPassword(newEmployer);
        return Optional.of(employerRepository.save(newEmployer).toDTO());
    }

    public Optional<EmployerDTO> findEmployerById(Long employerId){
        return employerRepository.findById(employerId).map(Employer::toDTO);
    }

    @Transactional
    public Optional<OfferDTO> createOffer(OfferDTO offerDTO){
        if(offerDTO == null) throw new IllegalArgumentException("Offer cannot be null");

        Session session = sessionManipulator.getCurrentSession();
        Offer offer = offerRepository.save(offerDTO.fromDTO());

        offerSessionRepository.save(new OfferSession(null, offer, session));

        return Optional.of(offer.toDTO());
    }

    @Transactional
    public List<OfferDTO> findAllOffersByEmployerId(Long employerId){
        Employer employer = employerRepository.findById(employerId).orElseThrow();
        List<Offer> offers = offerRepository.findAllByEmployer(employer);

        return sessionManipulator
                .removeOffersNotInCurrentSession(offers)
                .stream()
                .map(Offer::toDTO)
                .toList();
    }

    @Transactional
    public Optional<OfferDTO> updateOffer(OfferDTO offerDTO){
        Offer offer = offerRepository.findById(offerDTO.getId()).orElseThrow();

        if (!sessionManipulator.isOfferInCurrentSession(offer)) {
            throw new IllegalStateException("Offer is not in current session");
        }

        return Optional.of(offerRepository.save(offer).toDTO());
    }

    @Transactional
    public void deleteOffer(Long offerId){
        Offer offer = offerRepository.findById(offerId).orElseThrow();

        if (!sessionManipulator.isOfferInCurrentSession(offer)) {
            throw new IllegalStateException("Offer is not in current session");
        }

        offerRepository.delete(offer);
    }
}
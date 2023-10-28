package com.equipe4.audace.service;

import com.equipe4.audace.dto.EmployerDTO;
import com.equipe4.audace.dto.application.ApplicationDTO;
import com.equipe4.audace.dto.offer.OfferDTO;
import com.equipe4.audace.model.Employer;
import com.equipe4.audace.model.application.Application;
import com.equipe4.audace.model.offer.Offer;
import com.equipe4.audace.model.session.OfferSession;
import com.equipe4.audace.model.session.Session;
import com.equipe4.audace.repository.EmployerRepository;
import com.equipe4.audace.repository.application.ApplicationRepository;
import com.equipe4.audace.repository.offer.OfferRepository;
import com.equipe4.audace.repository.session.OfferSessionRepository;
import com.equipe4.audace.repository.security.SaltRepository;
import com.equipe4.audace.utils.SessionManipulator;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EmployerService extends GenericUserService<Employer> {
    private final EmployerRepository employerRepository;
    private final OfferRepository offerRepository;
    private final ApplicationRepository applicationRepository;
    private final OfferSessionRepository offerSessionRepository;
    private final SessionManipulator sessionManipulator;

    public EmployerService(SaltRepository saltRepository, EmployerRepository employerRepository, OfferRepository offerRepository,
                           OfferSessionRepository offerSessionRepository, SessionManipulator sessionManipulator, ApplicationRepository applicationRepository) {
        super(saltRepository);
        this.employerRepository = employerRepository;
        this.offerRepository = offerRepository;
        this.offerSessionRepository = offerSessionRepository;
        this.sessionManipulator = sessionManipulator;
        this.applicationRepository = applicationRepository;
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

    public List<EmployerDTO> findAllEmployers(){
        return employerRepository.findAll().stream().map(Employer::toDTO).toList();
    }

    @Transactional
    public Optional<OfferDTO> createOffer(OfferDTO offerDTO){
        if(offerDTO == null) throw new IllegalArgumentException("Offer cannot be null");

        Session session = sessionManipulator.getCurrentSession();

        offerDTO.setOfferStatus(Offer.OfferStatus.PENDING);

        Offer offer = offerRepository.save(offerDTO.fromDTO());

        offerSessionRepository.save(new OfferSession(null, offer, session));

        return Optional.of(offer.toDTO());
    }

    @Transactional
    public List<OfferDTO> findAllOffersByEmployerIdAndSessionId(Long employerId, Long sessionId){
        Employer employer = employerRepository.findById(employerId).orElseThrow(() -> new NoSuchElementException("Employer not found"));
        List<Offer> offers = offerRepository.findAllByEmployer(employer);

        return sessionManipulator.removeOffersNotInSession(offers, sessionId).stream().map(Offer::toDTO).toList();
    }

    @Transactional
    public Optional<OfferDTO> updateOffer(OfferDTO offerDTO){
        Offer offer = offerRepository.findById(offerDTO.getId()).orElseThrow(() -> new NoSuchElementException("Offer not found"));
        if (!sessionManipulator.isOfferInCurrentSession(offer)) throw new IllegalStateException("Offer is not in current session");
        return Optional.of(offerRepository.save(offer).toDTO());
    }

    @Transactional
    public void deleteOffer(Long offerId){
        Offer offer = offerRepository.findById(offerId).orElseThrow(() -> new NoSuchElementException("Offer not found"));
        if (!sessionManipulator.isOfferInCurrentSession(offer)) throw new IllegalStateException("Offer is not in current session");
        offerRepository.delete(offer);
    }

    //TODO : TESTS
    public List<ApplicationDTO> findAllApplicationsByEmployerIdAndOfferId(Long employerId, Long offerId) {
        Offer offer = offerRepository.findByEmployerIdAndId(employerId, offerId).orElseThrow(() -> new NoSuchElementException("Offer not found"));
        return applicationRepository.findAllByOffer(offer).stream().map(Application::toDTO).toList();
    }

    @Transactional
    public Optional<ApplicationDTO> acceptApplication(Long employerId, Long applicationId) {
        return setApplicationStatus(employerId, applicationId, Application.ApplicationStatus.ACCEPTED);
    }
    @Transactional
    public Optional<ApplicationDTO> refuseApplication(Long employerId, Long applicationId) {
        return setApplicationStatus(employerId, applicationId, Application.ApplicationStatus.REFUSED);
    }

    private Optional<ApplicationDTO> setApplicationStatus(Long employerId, Long applicationId, Application.ApplicationStatus applicationStatus) {
        Application application = applicationRepository.findById(applicationId).orElseThrow(() -> new NoSuchElementException("Application not found"));

        if (!application.getOffer().getEmployer().getId().equals(employerId)) throw new IllegalArgumentException("Employer does not own this application");

        if (applicationStatus == Application.ApplicationStatus.ACCEPTED) {
            Offer offer = application.getOffer();
            Long placesUsed = applicationRepository.countApplicationsByApplicationStatusAndAndOffer(Application.ApplicationStatus.ACCEPTED, offer);

            if (placesUsed >= offer.getAvailablePlaces()) throw new IllegalArgumentException("No more places available");
        }
        application.setApplicationStatus(applicationStatus);
        applicationRepository.save(application);
        return Optional.of(application.toDTO());
    }
}
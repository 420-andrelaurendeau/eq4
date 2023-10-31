package com.equipe4.audace.controller;

import com.equipe4.audace.controller.abstracts.GenericUserController;
import com.equipe4.audace.dto.EmployerDTO;
import com.equipe4.audace.dto.application.ApplicationDTO;
import com.equipe4.audace.dto.offer.OfferDTO;
import com.equipe4.audace.model.Employer;
import com.equipe4.audace.service.EmployerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employers")
@CrossOrigin(origins = "http://localhost:3000")

public class EmployerController extends GenericUserController<Employer, EmployerService> {
    public EmployerController(EmployerService employerService) {
        super(employerService);
    }

    @GetMapping("/{employerId}")
    public ResponseEntity<EmployerDTO> getEmployerById(@PathVariable Long employerId){
        logger.info("getEmployerById");
        return service.findEmployerById(employerId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/offers/{sessionId}")
    public ResponseEntity<List<OfferDTO>> getAllOffersByEmployerIdAndSessionId(@RequestParam("employerId") Long employerId, @PathVariable Long sessionId) {
        logger.info("getAllOffersByEmployerId");
        return ResponseEntity.ok(service.findAllOffersByEmployerIdAndSessionId(employerId, sessionId));
    }

    @PostMapping("/offers")
    public ResponseEntity<HttpStatus> createOffer(@RequestBody OfferDTO offerDTO){
        logger.info("createOffer");
        return service.createOffer(offerDTO)
                .map(offer -> new ResponseEntity<HttpStatus>(HttpStatus.CREATED))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }
    @PutMapping("/offers")
    public ResponseEntity<OfferDTO> updateOffer(@RequestBody OfferDTO offerDTO){
        logger.info("updateOffer");
        OfferDTO updatedOffer = service.updateOffer(offerDTO).orElseThrow();
        return ResponseEntity.ok(updatedOffer);
    }
    @DeleteMapping("/offers")
    public ResponseEntity<HttpStatus> deleteOffer(@RequestParam("offerId") Long offerId){
        logger.info("deleteOffer");
        service.deleteOffer(offerId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/applications/{offerId}")
    public ResponseEntity<List<ApplicationDTO>> getAllApplicationsByEmployerIdAndOfferId(@RequestParam("employerId") Long employerId, @PathVariable Long offerId) {
        logger.info("getAllApplicationsByEmployerId");
        return ResponseEntity.ok(service.findAllApplicationsByEmployerIdAndOfferId(employerId, offerId));
    }
    @PutMapping("/accept_application/{applicationId}")
    public ResponseEntity<HttpStatus> acceptApplication(@RequestParam("employerId") Long employerId, @PathVariable Long applicationId) {
        logger.info("acceptApplication");
        return service.acceptApplication(employerId, applicationId)
                .map(applicationDTO -> new ResponseEntity<HttpStatus>(HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }
    @PutMapping("/refuse_application/{applicationId}")
    public ResponseEntity<HttpStatus> refuseApplication(@RequestParam("employerId") Long employerId, @PathVariable Long applicationId) {
        logger.info("refuseApplication");
        return service.refuseApplication(employerId, applicationId)
                .map(applicationDTO -> new ResponseEntity<HttpStatus>(HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }
}

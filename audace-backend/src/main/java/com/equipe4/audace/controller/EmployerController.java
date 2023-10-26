package com.equipe4.audace.controller;

import com.equipe4.audace.controller.abstracts.GenericUserController;
import com.equipe4.audace.dto.application.ApplicationDTO;
import com.equipe4.audace.dto.EmployerDTO;
import com.equipe4.audace.dto.offer.OfferDTO;
import com.equipe4.audace.model.Employer;
import com.equipe4.audace.service.EmployerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/employers")
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

    @GetMapping("/offers")
    public ResponseEntity<List<OfferDTO>> getAllOffersByEmployerId(@RequestParam("employerId") Long employerId) {
        logger.info("getAllOffersByEmployerId");
        return ResponseEntity.ok(service.findAllOffersByEmployerId(employerId));
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
        service.deleteOffer(offerId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/offers/applications")
    public ResponseEntity<Map<Long, List<ApplicationDTO>>> getAllApplicationsByEmployerId(@RequestParam("employerId") Long employerId) {
        logger.info("getAllApplicationsByEmployerId");
        return ResponseEntity.ok(service.findAllApplicationsByEmployerId(employerId));
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

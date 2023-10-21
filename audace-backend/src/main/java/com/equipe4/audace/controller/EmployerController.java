package com.equipe4.audace.controller;

import com.equipe4.audace.controller.abstracts.GenericUserController;
import com.equipe4.audace.dto.EmployerDTO;
import com.equipe4.audace.dto.offer.OfferDTO;
import com.equipe4.audace.model.Employer;
import com.equipe4.audace.service.EmployerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/employers")
public class EmployerController extends GenericUserController<Employer, EmployerService> {
    public EmployerController(EmployerService employerService) {
        super(employerService);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployerDTO> getEmployerById(@PathVariable Long id){
        logger.info("getEmployerById");
        return service.findEmployerById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/offers")
    public ResponseEntity<List<OfferDTO>> getAllOffersByEmployerId(@PathVariable Long id) {
        logger.info("getAllOffersByEmployerId");
        return ResponseEntity.ok(service.findAllOffersByEmployerId(id));
    }

    @PostMapping("/{id}/offers")
    public ResponseEntity<OfferDTO> createOffer(@RequestBody OfferDTO offerDTO){
        logger.info("createOffer");
        return service.createOffer(offerDTO)
                .map(offer -> ResponseEntity.status(HttpStatus.CREATED).body(offerDTO))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PutMapping("/{id}/offers")
    public ResponseEntity<OfferDTO> updateOffer(@RequestBody OfferDTO offerDTO){
        logger.info("updateOffer");
        OfferDTO updatedOffer = service.updateOffer(offerDTO).orElseThrow();
        return ResponseEntity.ok(updatedOffer);
    }

    @DeleteMapping("/{id}/offers")
    public ResponseEntity<HttpStatus> deleteOffer(@RequestParam("offerId") Long offerId){
        service.deleteOffer(offerId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{employerId}/accept_application/{applicationId}")
    public ResponseEntity<HttpStatus> acceptApplication(@PathVariable Long employerId, @PathVariable Long applicationId) {
        logger.info("acceptApplication");
        return service.acceptApplication(employerId, applicationId)
                .map(applicationDTO -> new ResponseEntity<HttpStatus>(HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }
    @PostMapping("/{employerId}/refuse_application/{applicationId}")
    public ResponseEntity<HttpStatus> refuseApplication(@PathVariable Long employerId, @PathVariable Long applicationId) {
        logger.info("refuseApplication");
        return service.refuseApplication(employerId, applicationId)
                .map(applicationDTO -> new ResponseEntity<HttpStatus>(HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }
}

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

    @GetMapping("offers/{employerId}")
    public ResponseEntity<List<OfferDTO>> getAllOffersByEmployerId(@PathVariable Long employerId) {
        logger.info("getAllOffersByEmployerId");
        return ResponseEntity.ok(service.findAllOffersByEmployerId(employerId));
    }

    @PostMapping("offers/{employerId}")
    public ResponseEntity<OfferDTO> createOffer(@RequestBody OfferDTO offerDTO){
        logger.info("createOffer");
        return service.createOffer(offerDTO)
                .map(offer -> ResponseEntity.status(HttpStatus.CREATED).body(offerDTO))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PutMapping("offers/{id}")
    public ResponseEntity<OfferDTO> updateOffer(@RequestBody OfferDTO offerDTO){
        logger.info("updateOffer");
        OfferDTO updatedOffer = service.updateOffer(offerDTO).orElseThrow();
        return ResponseEntity.ok(updatedOffer);
    }

    @DeleteMapping("offers/{id}")
    public ResponseEntity<HttpStatus> deleteOffer(@RequestParam("offerId") Long offerId){
        service.deleteOffer(offerId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("offers/applications/{employerId}")
    public ResponseEntity<Map<Long, List<ApplicationDTO>>> getAllApplicationsByEmployerId(@PathVariable Long employerId) {
        logger.info("getAllApplicationsByEmployerId");
        return ResponseEntity.ok(service.findAllApplicationsByEmployerId(employerId));
    }

}

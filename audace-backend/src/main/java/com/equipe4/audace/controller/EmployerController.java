package com.equipe4.audace.controller;

import com.equipe4.audace.controller.abstracts.GenericUserController;
import com.equipe4.audace.dto.application.ApplicationDTO;
import com.equipe4.audace.dto.EmployerDTO;
import com.equipe4.audace.dto.department.DepartmentDTO;
import com.equipe4.audace.dto.offer.OfferDTO;
import com.equipe4.audace.model.Employer;
import com.equipe4.audace.model.department.Department;
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

    @GetMapping("/{id}")
    public ResponseEntity<EmployerDTO> getEmployerById(@PathVariable Long id){
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

    @GetMapping("/offers/{offerId}")
    public ResponseEntity<OfferDTO> getOfferById(@PathVariable Long offerId) {
        logger.info("getOfferById");
        return service.findOfferById(offerId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/offers")
    public ResponseEntity<OfferDTO> createOffer(@RequestBody OfferDTO offerDTO){
        logger.info("createOffer");
        logger.info(offerDTO.toString());
        return service.createOffer(offerDTO).map(offer -> ResponseEntity.status(HttpStatus.CREATED).body(offerDTO))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
    @PutMapping("/offers")
    public ResponseEntity<OfferDTO> updateOffer(@RequestBody OfferDTO offerDTO){
        logger.info("updateOffer");
        logger.info("Received offer for update: " + offerDTO.toString());

        OfferDTO updatedOffer = service.updateOffer(offerDTO).get();
        logger.info("Updated offer: " + updatedOffer.toString());
        return ResponseEntity.ok(updatedOffer);
    }

    @DeleteMapping("/offers/{offerId}")
    public ResponseEntity<HttpStatus> deleteOffer(@PathVariable("offerId") Long offerId){
        logger.info("deleteOffer");
        service.deleteOffer(offerId);
        return ResponseEntity.ok().build();
    }
}

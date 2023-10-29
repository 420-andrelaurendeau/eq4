package com.equipe4.audace.controller;

import com.equipe4.audace.controller.abstracts.GenericUserController;
import com.equipe4.audace.dto.EmployerDTO;
import com.equipe4.audace.dto.application.ApplicationDTO;
import com.equipe4.audace.dto.department.DepartmentDTO;
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

    @GetMapping("/{id}/offers/{sessionId}")
    public ResponseEntity<List<OfferDTO>> getAllOffersByEmployerId(@PathVariable Long id, @PathVariable Long sessionId) {
        logger.info("getAllOffersByEmployerId");
        return ResponseEntity.ok(service.findAllOffersByEmployerId(id, sessionId));
    }

    @GetMapping("/offers/{offerId}")
    public ResponseEntity<OfferDTO> getOfferById(@PathVariable Long offerId) {
        logger.info("getOfferById");
        return service.findOfferById(offerId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/offers")
    public ResponseEntity<OfferDTO> createOffer(@RequestBody OfferDTO offerDTO) {
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

    @DeleteMapping("/{offerId}/offers")
    public ResponseEntity<HttpStatus> deleteOffer(@PathVariable("offerId") Long offerId){
        logger.info("deleteOffer");
        service.deleteOffer(offerId);
        return ResponseEntity.ok().build();

    }

    @GetMapping("/departments")
    public ResponseEntity<List<DepartmentDTO>> getAllDepartments(){
        logger.info("getAllDepartments");
        return ResponseEntity.ok(service.findAllDepartments());
    }

    @GetMapping("/{id}/offers/{offerId}/applications")
    public ResponseEntity<List<ApplicationDTO>> getAllApplicationsByEmployerIdAndOfferId(@PathVariable Long id, @PathVariable Long offerId) {
        logger.info("getAllApplicationsByEmployerIdAndOfferId");
        return ResponseEntity.ok(service.findAllApplicationsByEmployerIdAndOfferId(id, offerId));
    }
}

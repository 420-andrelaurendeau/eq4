package com.equipe4.audace.controller;

import com.equipe4.audace.controller.abstracts.GenericUserController;
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

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/employers")
public class EmployerController extends GenericUserController<Employer, EmployerService> {
    public EmployerController(EmployerService employerService) {
        super(employerService);
    }

    @GetMapping
    public ResponseEntity<List<EmployerDTO>> getAllEmployers(){
        logger.info("getAllEmployers");
        return ResponseEntity.ok(service.findAllEmployers());
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

    @GetMapping("/departments")
    public ResponseEntity<List<DepartmentDTO>> getAllDepartments(){
        logger.info("getAllDepartments");
        return ResponseEntity.ok(service.findAllDepartments());
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

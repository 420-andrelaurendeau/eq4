package com.equipe4.audace.controller;

import com.equipe4.audace.controller.abstracts.GenericUserController;
import com.equipe4.audace.dto.EmployerDTO;
import com.equipe4.audace.dto.application.ApplicationDTO;
import com.equipe4.audace.dto.contract.ContractDTO;
import com.equipe4.audace.dto.contract.SignatureDTO;
import com.equipe4.audace.dto.department.DepartmentDTO;
import com.equipe4.audace.dto.offer.OfferDTO;
import com.equipe4.audace.model.Employer;
import com.equipe4.audace.model.department.Department;
import com.equipe4.audace.service.EmployerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/employers")
@CrossOrigin(origins = "http://localhost:3000")

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

    @GetMapping("/offers")
    public ResponseEntity<OfferDTO> getOfferById(@RequestParam("offerId") Long offerId){
        logger.info("getOfferById");
        return service.getOfferById(offerId)
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

    @GetMapping("/departments")
    public ResponseEntity<List<DepartmentDTO>> getAllDepartments() {
        logger.info("getAllDepartments");
        return ResponseEntity.ok(service.getAllDepartments());
    }

    @PostMapping("/sign_contract/{contractId}")
    public ResponseEntity<SignatureDTO> signContract(@PathVariable Long contractId) {
        logger.info("employerSignContract");
        try {
            return ResponseEntity.ok(
                    service.signContract(contractId).orElseThrow()
            );
        } catch (IllegalArgumentException | NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/contracts/{contractId}")
    public ResponseEntity<ContractDTO> getContractById(@PathVariable Long contractId){
        logger.info("getContractById");
        return service.findContractById(contractId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/contracts/{contractId}/signatures")
    public List<SignatureDTO> getSignaturesByContractId(@PathVariable Long contractId) {
        logger.info("getSignatureByContractId");
        return service.getSignaturesByContractId(contractId);
    }
}

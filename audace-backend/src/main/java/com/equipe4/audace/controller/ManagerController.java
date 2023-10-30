package com.equipe4.audace.controller;

import com.equipe4.audace.controller.abstracts.GenericUserController;
import com.equipe4.audace.dto.ManagerDTO;
import com.equipe4.audace.dto.application.ApplicationDTO;
import com.equipe4.audace.dto.contract.ContractDTO;
import com.equipe4.audace.dto.cv.CvDTO;
import com.equipe4.audace.dto.offer.OfferDTO;
import com.equipe4.audace.model.Manager;
import com.equipe4.audace.model.contract.Contract;
import com.equipe4.audace.service.ManagerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/managers")
@CrossOrigin(origins = "http://localhost:3000")

public class ManagerController extends GenericUserController<Manager, ManagerService> {
    public ManagerController(ManagerService managerService) {
        super(managerService);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ManagerDTO> getManagerById(@PathVariable Long id) {
       return service.getManagerById(id)
               .map(ResponseEntity::ok)
               .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/{managerId}/accept_offer/{offerId}")
    public ResponseEntity<HttpStatus> acceptOffer(@PathVariable Long managerId, @PathVariable Long offerId) {
        logger.info("acceptOffer");
        return service.acceptOffer(managerId, offerId)
                .map(offerDTO -> new ResponseEntity<HttpStatus>(HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @PostMapping("/{managerId}/refuse_offer/{offerId}")
    public ResponseEntity<HttpStatus> refuseOffer(@PathVariable Long managerId, @PathVariable Long offerId) {
        logger.info("refuseOffer");
        return service.refuseOffer(managerId, offerId)
                .map(offerDTO -> new ResponseEntity<HttpStatus>(HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @GetMapping("/offers/{departmentId}")
    public ResponseEntity<List<OfferDTO>> getOffersByDepartment(@PathVariable Long departmentId) {
        return ResponseEntity.ok(service.getOffersByDepartment(departmentId));
    }

    @GetMapping("/cvs/{departmentId}")
    public ResponseEntity<List<CvDTO>> getCvsByDepartment(@PathVariable Long departmentId) {
        logger.info("getCvsByDepartment");
        return ResponseEntity.ok(service.getCvsByDepartment(departmentId));
    }

    @PostMapping("/{managerId}/accept_cv/{cvId}")
    public ResponseEntity<HttpStatus> acceptCv(@PathVariable Long managerId, @PathVariable Long cvId) {
        logger.info("acceptCv");
        return service.acceptCv(managerId, cvId)
                .map(offerDTO -> new ResponseEntity<HttpStatus>(HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @PostMapping("/{managerId}/refuse_cv/{cvId}")
    public ResponseEntity<HttpStatus> refuseCv(@PathVariable Long managerId, @PathVariable Long cvId) {
        logger.info("refuseCv");
        return service.refuseCv(managerId, cvId)
                .map(offerDTO -> new ResponseEntity<HttpStatus>(HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @PostMapping("/contracts")
    public ResponseEntity<HttpStatus> createContract(@RequestBody ContractDTO contractDTO){
        logger.info("createContract");
        return service.createContract(contractDTO)
                .map(contract -> new ResponseEntity<HttpStatus>(HttpStatus.CREATED))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @GetMapping("/contracts/{contractId}")
    public ResponseEntity<ContractDTO> getContractById(@PathVariable Long contractId){
        logger.info("getContractById");
        return service.findContractById(contractId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}

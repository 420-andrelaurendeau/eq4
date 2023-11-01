package com.equipe4.audace.controller;

import com.equipe4.audace.controller.abstracts.GenericUserController;
import com.equipe4.audace.dto.application.ApplicationDTO;
import com.equipe4.audace.dto.ManagerDTO;
import com.equipe4.audace.dto.cv.CvDTO;
import com.equipe4.audace.dto.department.DepartmentDTO;
import com.equipe4.audace.dto.offer.OfferDTO;
import com.equipe4.audace.model.Manager;
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

    @GetMapping("/offers/{departmentId}/{sessionId}")
    public ResponseEntity<List<OfferDTO>> getOffersByDepartment(@PathVariable Long departmentId, @PathVariable Long sessionId) {
        return ResponseEntity.ok(service.getOffersByDepartment(departmentId, sessionId));
    }

    @GetMapping("/cvs/{departmentId}/{sessionId}")
    public ResponseEntity<List<CvDTO>> getCvsByDepartment(@PathVariable Long departmentId, @PathVariable Long sessionId) {
        logger.info("getCvsByDepartment");
        return ResponseEntity.ok(service.getCvsByDepartment(departmentId, sessionId));
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
    @GetMapping("/{managerId}/acceptedApplications/{departmentId}")
    public ResponseEntity<List<ApplicationDTO>> getAcceptedApplicationsByDepartment(@PathVariable Long managerId, @PathVariable Long departmentId) {
        logger.info("getAcceptedApplicationsByDepartment");
        return ResponseEntity.ok(
                service.getAcceptedApplicationsByDepartment(managerId, departmentId)
        );
    }

    @GetMapping("/{managerId}/department")
    public ResponseEntity<DepartmentDTO> getDepartmentByManager(@PathVariable Long managerId) {
        logger.info("getDepartment");
        return ResponseEntity.ok(
                service.getDepartmentByManager(managerId)
        );
    }
}

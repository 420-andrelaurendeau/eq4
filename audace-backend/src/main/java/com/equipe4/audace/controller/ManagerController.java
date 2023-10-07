package com.equipe4.audace.controller;

import com.equipe4.audace.controller.abstracts.GenericUserController;
import com.equipe4.audace.dto.ManagerDTO;
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

    @PostMapping("/accept_offer/{offerId}")
    public ResponseEntity<HttpStatus> acceptOffer(@PathVariable Long offerId) {
        logger.info("acceptOffer");
        return service.acceptOffer(offerId)
                .map(offerDTO -> new ResponseEntity<HttpStatus>(HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST));
    }

    @PostMapping("/refuse_offer/{offerId}")
    public ResponseEntity<HttpStatus> refuseOffer(@PathVariable Long offerId) {
        logger.info("refuseOffer");
        return service.refuseOffer(offerId)
                .map(offerDTO -> new ResponseEntity<HttpStatus>(HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST));
    }

    @GetMapping("/offers/{departmentId}")
    public ResponseEntity<List<OfferDTO>> getOffersByDepartment(@PathVariable Long departmentId) {
        return ResponseEntity.ok(service.getOffersByDepartment(departmentId));
    }
}

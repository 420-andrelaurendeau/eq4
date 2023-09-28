package com.equipe4.audace.controller;

import com.equipe4.audace.dto.offer.OfferDTO;
import com.equipe4.audace.service.ManagerService;
import org.apache.coyote.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/managers")
@CrossOrigin(origins = "http://localhost:3000")

public class ManagerController {
    private Logger logger = LoggerFactory.getLogger(ManagerController.class);

    private final ManagerService managerService;

    @Autowired
    public ManagerController(ManagerService managerService) {
        this.managerService = managerService;
    }

    @PostMapping("/accept_offer/{offerId}")
    public ResponseEntity<HttpStatus> acceptOffer(@PathVariable Long offerId) {
        logger.info("acceptOffer");
        return managerService.acceptOffer(offerId).map(offerDTO -> new ResponseEntity<HttpStatus>(HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST));
    }

    @PostMapping("/refuse_offer/{offerId}")
    public ResponseEntity<HttpStatus> refuseOffer(@PathVariable Long offerId) {
        logger.info("refuseOffer");
        return managerService.refuseOffer(offerId)
                .map(offerDTO -> new ResponseEntity<HttpStatus>(HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST));
    }

    @GetMapping("/offers")
    public ResponseEntity<Iterable<OfferDTO>> getOffersByDepartment(@RequestParam Long departmentId) {
        logger.info("getOffersByDepartment");
        return ResponseEntity.ok(managerService.getOffersByDepartment(departmentId));
    }
}

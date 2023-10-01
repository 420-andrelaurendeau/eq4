package com.equipe4.audace.controller;

import com.equipe4.audace.dto.EmployerDTO;
import com.equipe4.audace.dto.offer.OfferDTO;
import com.equipe4.audace.service.EmployerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/employers")
public class EmployerController {
    private final EmployerService employerService;

    private Logger logger = LoggerFactory.getLogger(EmployerController.class);

    @Autowired
    public EmployerController(EmployerService employerService) {
        this.employerService = employerService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployerDTO> getEmployerById(@PathVariable Long id){
        logger.info("getEmployerById");
        return employerService.findEmployerById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<EmployerDTO> getAllEmployers(){
        logger.info("getAllEmployers");
        return employerService.findAllEmployers();
    }

    @GetMapping("/{id}/offers")
    public List<OfferDTO> getAllOffersByEmployerId(@PathVariable Long id) {
        logger.info("getAllOffersByEmployerId");
        return employerService.findAllOffersByEmployerId(id);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> createEmployer(@RequestBody EmployerDTO employerDTO){
        logger.info("createEmployer");
        employerService.createEmployer(employerDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/{id}/offers")
    public ResponseEntity<OfferDTO> createOffer(@RequestBody OfferDTO offerDTO){
        logger.info("createOffer");
        return employerService.createOffer(offerDTO).map(offer -> ResponseEntity.status(HttpStatus.CREATED).body(offerDTO))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
    @PutMapping("/{id}/offers")
    public ResponseEntity updateOffer(@RequestBody OfferDTO offerDTO){
        logger.info("updateOffer");
        OfferDTO updatedOffer = employerService.updateOffer(offerDTO).get();
        return ResponseEntity.ok(updatedOffer);
    }

    @DeleteMapping("/{id}/offers")
    public ResponseEntity deleteOffer(@RequestParam("offerId") Long offerId){
        employerService.deleteOffer(offerId);
        return ResponseEntity.ok().build();
    }


}

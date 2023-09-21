package com.equipe4.audace.controller;

import com.equipe4.audace.dto.EmployerDTO;
import com.equipe4.audace.dto.offer.OfferDTO;
import com.equipe4.audace.service.EmployerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/employers")
public class EmployerController {
    private final EmployerService employerService;
    Logger logger = LoggerFactory.getLogger(EmployerController.class);

    public EmployerController(EmployerService employerService) {
        this.employerService = employerService;
    }

    @GetMapping
    public List<EmployerDTO> getAllEmployers(){
        logger.info("getAllEmployers");
        return employerService.findAllEmployers();
    }

    @PostMapping
    public ResponseEntity<HttpStatus> createEmployer(@RequestBody EmployerDTO employerDTO){
        logger.info("createEmployer");
        employerService.createEmployer(employerDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/offers")
    public ResponseEntity<HttpStatus> createOffer(@RequestBody OfferDTO offerDTO) {
        logger.info("createOffer");
        employerService.createOffer(offerDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}

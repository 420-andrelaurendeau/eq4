package com.equipe4.audace.controller;

import com.equipe4.audace.dto.offer.OfferDTO;
import com.equipe4.audace.service.OfferService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/offers")
public class OfferController {
    private final OfferService offerService;
    private Logger logger = LoggerFactory.getLogger(OfferController.class);


    @Autowired
    public OfferController(OfferService offerService) {
        this.offerService = offerService;
    }

    @GetMapping
    public List<OfferDTO> getAllOffers(){
        logger.info("getAllOffers");
        return offerService.findAllOffers();
    }

}
